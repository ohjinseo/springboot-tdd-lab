package com.example.springbootlab.factory.entity;

import com.example.springbootlab.domain.post.Image;

public class ImageFactory {
    public static Image createImage() {
        return new Image("origin_filename.jpg");
    }

    public static Image createImageWithOriginName(String originName) {
        return new Image(originName);
    }
}
