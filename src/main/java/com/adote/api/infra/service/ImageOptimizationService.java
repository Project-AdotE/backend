package com.adote.api.infra.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageOptimizationService {

    public byte[] optimizeImage(MultipartFile file) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Thumbnails.of(file.getInputStream())
                    .size(800, 800)
                    .outputQuality(0.75)
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        }
    }

    public MultipartFile convertToMultipartFile(byte[] optimizedImage, String originalFilename) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return originalFilename;
            }

            @Override
            public String getOriginalFilename() {
                return originalFilename;
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return optimizedImage.length == 0;
            }

            @Override
            public long getSize() {
                return optimizedImage.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return optimizedImage;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(optimizedImage);
            }

            @Override
            public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
                throw new UnsupportedOperationException("Not implemented");
            }
        };
    }
}
