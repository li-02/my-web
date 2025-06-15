package com.example.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagWithCountResponse {
    private Long id;
    private String name;
    private Integer usageCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
