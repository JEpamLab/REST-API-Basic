package com.epam.model.entity;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
