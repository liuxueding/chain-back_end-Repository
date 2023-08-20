package com.chain.file_upload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/FileUpload")
public class FileUploadController {

    @Value("${upload.folder}")
    private String uploadFolder;

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 拼接文件保存路径
            String filePath = uploadFolder + File.separator + fileName;
            // 创建文件对象
            File dest = new File(filePath);
            // 获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            // 创建目标文件的输出流
            FileOutputStream outputStream = new FileOutputStream(dest);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 关闭输入流和输出流
            inputStream.close();
            outputStream.close();

            return "文件上传成功"; // 文件保存成功
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败";
        }
    }
}
