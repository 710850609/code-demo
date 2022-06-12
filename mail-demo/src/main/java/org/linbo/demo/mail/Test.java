package org.linbo.demo.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws Exception {

        Properties props = new Properties(); //可以加载一个配置文件
        // 使用smtp：简单邮件传输协议
        props.put("mail.smtp.host", "smtp.qq.com");//存储发送邮件服务器的信息
        props.put("mail.smtp.auth", "true");//同时通过验证
        Session session = Session.getInstance(props);//根据属性新建一个邮件会话
//        session.setDebug(true); //有他会打印一些调试信息。

        MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象
        message.setFrom(new InternetAddress("**************@qq.com"));//设置发件人的地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("**************@qq.com"));//设置收件人,并设置其接收类型为TO
        message.setSubject("22232232222");//设置标题
        //设置信件内容
//        message.setText(mailContent); //发送 纯文本 邮件 todo
        //message.setContent("232323232323", "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富
        message.setSentDate(new Date());//设置发信时间

        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();

        // 设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setText("邮件的具体内容在此");
        multipart.addBodyPart(contentPart);
        // 添加附件
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource dataSource = new FileDataSource(new File("D:\\intentionDescription\\20180514153827170.png"));
        // 添加附件的内容
        messageBodyPart.setDataHandler(new DataHandler(dataSource));
        // 添加附件的标题
        // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
        messageBodyPart.setFileName(MimeUtility.encodeText(dataSource.getName()));
        multipart.addBodyPart(messageBodyPart);
        // 将multipart对象放到message中
        message.setContent(multipart);
        message.saveChanges();//存储邮javax.activation.DataHandler件信息

        //发送邮件
        Transport transport = session.getTransport();
        transport.connect("**************@qq.com", "jiqaggzvjrzyhjdd");//发送邮件的账号， 授权码
        transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址
        transport.close();
    }

    public static void post() {

    }
}
