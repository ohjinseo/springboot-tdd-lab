package com.example.springbootlab.domain.post;

import com.example.springbootlab.exception.UnSupportedImageFormatException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uniqueName; // 이미지 구분을 위해 고유 이름 부여

    @Column(nullable = false)
    private String originName; // 원래 이미지 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // 게시글이 제거되면 이미지도 연쇄적으로 제거
    private Post post;

    // FileService나 PostService에서 지원할 수 있는 타입을 검사할 수 있지만 이는 본연의 임무가 아니므로 도메인 단에서 검사
    private final static String supportedExtension[] = {
            "jpg",
            "jpeg",
            "gif",
            "bmp",
            "png"
    };

    public Image(String originName) {
        this.uniqueName = generateUniqueName(extractExtension(originName));
        this.originName = originName;
    }

    public void initPost(Post post){
        if (this.post == null) {
            this.post = post;
        }
    }

    private String generateUniqueName(String extension) {
        return UUID.randomUUID().toString()+"." + extension;
    }

    private String extractExtension(String originName) {
        try {
            String ext = originName.substring(originName.lastIndexOf(".") + 1);
            if (isSupportedFormat(ext)) // 만약 지원하는 이미지 타입이라면
                return ext;
        } catch (StringIndexOutOfBoundsException e) {}
        throw new UnSupportedImageFormatException();
    }

    private boolean isSupportedFormat(String ext) {
        return Arrays.stream(supportedExtension).anyMatch(e -> e.equalsIgnoreCase(ext));
    }
}
