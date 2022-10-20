package ru.ramprox.service.picture.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {

    private String mediaType;

    private byte[] content;

}
