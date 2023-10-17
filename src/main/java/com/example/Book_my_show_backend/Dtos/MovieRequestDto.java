package com.example.Book_my_show_backend.Dtos;

import lombok.Data;

import java.util.Date;

@Data
public class MovieRequestDto {
    private String name ;
    private Date releasedDate;
    private int duration;
}
