package ru.bigint.webapp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageUploader {
    private static Logger LOGGER = LoggerFactory.getLogger(ImageUploader.class);

    public static void upload(MultipartFile imageFile, String path) {
        if ( imageFile != null && !imageFile.isEmpty() ) {
            try {
                byte[] bytes = imageFile.getBytes();

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(path)
                        )
                );
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                LOGGER.error("Ошибка загрузки файла профиля пользователя");
            }
        }
    }
}
