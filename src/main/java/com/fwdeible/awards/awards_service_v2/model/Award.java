package com.fwdeible.awards.awards_service_v2.model;


import lombok.Data;


import java.awt.image.BufferedImage;

@Data
public class Award {

    private Long id;
    private String name;
    private String description;
    private int precedence;
    private String imageName;



}

