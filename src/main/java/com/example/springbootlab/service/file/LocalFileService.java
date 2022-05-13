package com.example.springbootlab.service.file;

import com.example.springbootlab.exception.FileUploadFailureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class LocalFileService implements FileService {

    @Value("${upload.image.location}")
    private String location; // 파일 업로드할 위치 주입

    @PostConstruct
    void postConstruct() {
        File dir = new File(location); // 파일을 업로드할 디렉토리 생성
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void upload(MultipartFile file, String filename) {
        try { // MultipartFile을 실제 파일로 지정된 위치에 저장
            file.transferTo(new File(location + filename)); // 특정 파일로 실제 업로드 처리
        } catch (IOException e) {
            throw new FileUploadFailureException(e);
        }
    }

    @Override
    public void delete(String filename) {

    }
}
