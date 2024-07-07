package com.spz.common;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageCopy {
    @Value("${spzStore.path}")
    private String basePath;
    @Value("${spzStore.save}")
    private String savePath;
    public void imageCopy()  throws IOException {
        Path source = Paths.get(savePath);
        Path target = Paths.get(basePath);
        Files.walk(source)
                .forEach(sourcePath -> {
                    Path targetPath = target.resolve(sourcePath.relativize(source).toString());
                    try {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception ex) {
                        // 如果是文件夹，则创建它
                        if (Files.isDirectory(sourcePath)) {
                            try {
                                Files.createDirectories(targetPath);
                            } catch (Exception e) {
                                // 处理目录创建失败的情况（例如，权限问题）
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
