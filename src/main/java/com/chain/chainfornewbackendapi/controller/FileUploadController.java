package com.chain.chainfornewbackendapi.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RestController
@RequestMapping("/FileUpload")
@Api(tags = "文件上传")
//@CrossOrigin(origins = "*")
public class FileUploadController {

    @Value("${upload.folder}")
    private String uploadFolder;

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("email")String email){
        if (file.isEmpty()|| StringUtils.isEmpty(email)) {
            System.out.println("upload failed,not select the file or not offer the email");
            return "上传失败，请检查文件或邮箱是否为空";
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
            System.out.println("upload succeeded");

            sendEmailWithAttachment(dest,email);

            return "文件上传成功"; // 文件保存成功
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            System.out.println("upload failed, IOException");
            return "文件上传失败";
        }
    }


    private void sendEmailWithAttachment(File file,String email) throws MessagingException {
        Properties prop =new Properties();
        prop.setProperty("mail.host","smtp.qq.com");///设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol","smtp");///邮件发送协议
        prop.setProperty("mail.smtp.auth","true");//需要验证用户密码

        //QQ邮箱需要设置SSL加密
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);

        //使用javaMail发送邮件的5个步骤
        //1.创建定义整个应用程序所需要的环境信息的session对象
        Session session=Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2195016604@qq.com","lvhpunrhfvhydibj");
            }
        });
        //开启session的debug模式，这样可以查看到程序发送Email的运行状态
        session.setDebug(true);//可写可不写

        //2.通过session得到transport对象
        Transport ts=session.getTransport();

        //3.使用邮箱的用户名和授权码连上邮件服务器
        ts.connect("smtp.qq.com","2195016604@qq.com","lvhpunrhfvhydibj");

        //4.创建邮件：写文件
        //注意需要传递session
        MimeMessage message=new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("2195016604@qq.com"));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
        //邮件标题
        message.setSubject("chainAge enroll questions--submit");
        //邮件的文本内容
        MimeBodyPart text=new MimeBodyPart();
        text.setContent("<h1 style='color: red'>请及时接收附件</h1>","text/html;charset=UTF-8");
        //message.setContent("<h1 style='color: red'>请及时接收附件</h1>","text/html;charset=UTF-8");

        //附件
        MimeBodyPart body1= new MimeBodyPart();
        body1.setDataHandler(new DataHandler(new FileDataSource(file)));
        body1.setFileName(file.getName());
        //描述数据关系
        MimeMultipart mm=new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(body1);
        mm.setSubType("mixed");
        //设置到消息中，保存修改
        message.setContent(mm);
        message.saveChanges();

        //5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());

        //关闭连接
        ts.close();
    }
    private static class MailSSLSocketFactory {
        public void setTrustAllHosts(boolean b) {
        }
    }
}
