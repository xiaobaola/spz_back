package com.spz.public_resource.common;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/spz/common")
public class CommonController {
    // 优化 图片压缩
    // 方案2 只需要basePath 不需转存
    @Value("${spzStore.path}")
    private String basePath;
    // 方案1 拷贝到磁盘和tomcat服务器
//    @Value("${spzStore.save}")
//    private String savePath;

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        log.info("name={}",name);

        try {
            // 从Tomcat服务器中读取文件 相对路径
            //通过输入流读取文件内容
//            FileInputStream fileInputStream = new FileInputStream(savePath + name);
            FileInputStream fileInputStream = new FileInputStream(basePath + name);

            //通过输出流，将文件返回到浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len;
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
            log.info("FileNotFoundException : com.spz.common.CommonController.download");
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
        // 上传到tomcat服务器中
        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //目录不存在 创建目录
            dir.mkdirs();
        }
        //创建一个目录对象2
        // 用于图片备份
//        File dir2 = new File(savePath);
//        //判断当前目录是否存在
//        if (!dir2.exists()) {
//            //目录不存在 创建目录
//            dir2.mkdirs();
//        }

        try {
            //将临时文件转存到指定路径
            file.transferTo(new File(basePath + fileName));
            //文件拷贝
//            fileCopy(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Res.success(fileName);
    }

//    private void fileCopy(String fileName) throws IOException {
//        //文件拷贝
//        File sourceFile = new File(savePath + fileName);
//        File destinationFile = new File(basePath + fileName);
//
//        FileInputStream fis = new FileInputStream(sourceFile);
//        FileOutputStream fos = new FileOutputStream(destinationFile);
//
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = fis.read(buffer)) != -1) {
//            fos.write(buffer, 0, bytesRead);
//        }
//        fos.close();
//        fis.close();
//    }

    @GetMapping("/apk")
    public void downloadApk(HttpServletResponse response) {
        String apkPath = basePath;
        apkPath = apkPath.substring(0,apkPath.length()-6);
        String name = "apk\\spzK2.apk";
        apkPath = apkPath+name;
        log.info("apkPath:{}",apkPath);

        try {
            //通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(apkPath);

            //通过输出流，将文件返回到浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("application/vnd.android.package-archive");

            int len;
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
            log.info("FileNotFoundException : com.spz.common.CommonController.download");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
