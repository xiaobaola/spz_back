package com.spz.common;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/spz/common")
public class CommonController {
    @Value("${spzStore.path}")
    private String basePath;

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            //通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //通过输出流，将文件返回到浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                //刷新
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException fe) {
            log.info("FileNotFoundException : com.reggie.common.CommonController.download");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload")
    public Res<String> upload(MultipartFile file) {
        log.info("上传文件名：" + file.toString());
        String originalFilename = file.getOriginalFilename();
        //获取后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名
        String fileName = UUID.randomUUID().toString();
        //拼接
        fileName = fileName + suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //目录不存在 创建目录
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定路径
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Res.success(fileName);
    }
}
