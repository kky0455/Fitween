package com.ssafy.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ArticleListDto {

    private Long articleIdx;

    private Map<String, Object> articleImg;

    public ArticleListDto(Long articleIdx, Map<String, Object>articleImg){
        this.articleIdx = articleIdx;
        this.articleImg = articleImg;
    }
}
