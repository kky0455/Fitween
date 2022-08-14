package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArticleImgDto {
    String baseUrl;

    byte[] img;

    public ArticleImgDto(String baseUrl, byte[] img){
        this.baseUrl = baseUrl;
        this.img = img;
    }
}
