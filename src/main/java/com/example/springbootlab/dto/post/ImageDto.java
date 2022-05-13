package com.example.springbootlab.dto.post;

import com.example.springbootlab.domain.post.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String originName;
    private String uniqueName;

    public static ImageDto toDto(Image image) {
        return new ImageDto(image.getId(), image.getOriginName(), image.getUniqueName());
    }
}
