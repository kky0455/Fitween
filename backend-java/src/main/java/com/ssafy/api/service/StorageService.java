package com.ssafy.api.service;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.ssafy.common.exception.MyFileNotFoundException;
import com.ssafy.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Autowired
    public StorageService(FileStorageProperties fileStorageProperties){
        this.uploadPath = fileStorageProperties.getUploadDir();
    }

    private String getRandomStr(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println("random : " + generatedString);
        return generatedString;
    }

    public List<String> saveFiles(MultipartFile[] files, UUID uid) throws IOException {
        String randomStr = getRandomStr();
        List<String> fileNames = new ArrayList<>();
        for(MultipartFile file : files) {
            fileNames.add(randomStr + StringUtils.cleanPath(file.getOriginalFilename()));
        }
        Path uploadPath = Paths.get(this.uploadPath+"/"+uid);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("make dir : " + uploadPath.toString());
        }
        for(int i =0; i< files.length; i++) {
            try (InputStream inputStream = files[i].getInputStream()) {
                Path filePath = uploadPath.resolve(fileNames.get(i));
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileNames.get(i), ioe);
            }
        }
        return fileNames;
    }

    public Resource loadFileAsResource(String userId, String articleTitle) {
        Path uploadPath = Paths.get(this.uploadPath+"/"+userId);
        try {
            Path filePath = uploadPath.resolve(articleTitle).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + articleTitle);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + articleTitle, ex);
        }
    }
    private static String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }

}
