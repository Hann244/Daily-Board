package com.team5.first_project.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestBoardDto {
    private String name;
    private String description;
    private String type;
}
