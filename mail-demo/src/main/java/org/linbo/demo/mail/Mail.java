package org.linbo.demo.mail;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.ArrayList;
import java.util.List;

public class Mail {

    /** 域名 */
    private String host;
    /** 端口 */
    private Integer port;
    /** 授权码 */
    private String authCode;

    /** 发送邮箱 */
    private String from;
    /** 发送方昵称 */
    private String nickName;

    /** 接收邮箱 */
    private String[] receivers;

    /** 邮件主题 */
    private String subject;
    /** 邮件正文 */
    private String message;

    /** 附件 */
    private List<EmailAttachment> attachments = new ArrayList<>(1);

    public static Mail server(String host, Integer port) {
        Mail email = new Mail();
        email.host = host;
        email.port = port;
        return email;
    }

    public Mail from(String from, String authCode, String nickName) {
        this.from = from;
        this.nickName = nickName;
        this.authCode = authCode;
        return this;
    }

    public Mail to(String[] receivers) {
        this.receivers = receivers;
        return this;
    }

    public Mail subject(String subject) {
        this.subject = subject;
        return this;
    }

    public Mail message(String message) {
        this.message = message;
        return this;
    }

    public Mail attach(String name, String description, String path) {
        EmailAttachment attachment=new EmailAttachment();
        attachment.setName(name);
        attachment.setDescription(description);
        attachment.setPath(path);
        this.attachments.add(attachment);
        return this;
    }

    public void send() {
        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(host);
            email.setCharset("UTF-8");
            for (String str : this.receivers) {
                email.addTo(str);
            }
            email.setFrom(from, this.nickName);
            email.setSmtpPort(this.port);
            email.setAuthentication(this.from, this.authCode);
            email.setSubject(this.subject);
            email.setMsg(this.message);
            this.attachments.forEach(attach -> {
                try {
                    email.attach(attach);
                } catch (EmailException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
            email.send();
//            System.out.println(String.format("%s 发送邮件到 %s", from, join(mail.getReceivers(), ",")));
//            return true;
        } catch (EmailException e) {
            e.printStackTrace();
//            System.err.println(from + "发送邮件到" + join(mail.getReceivers(), ",") + "失败");
//            return false;
        }
    }

    public static void main(String[] args) {
        Mail.server("smtp.163.com", 25)
                .from("public_bob@163.com", "BPNVNFTHPDOYDNDF", "程序bug报告者")
                .to(new String[]{"710850609@qq.com"})
                .subject("测试")
                .message("测试发送邮件")
                .attach("测试附件.gif", "附件描述", "C:\\Users\\71085\\Desktop\\temp\\006APoFYly1ga16lzg608g306k06kkci.gif")
                .send();
    }
}
