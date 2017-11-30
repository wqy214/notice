package com.itheima.bos.notice;


import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class EmailUtil {

    private String email;

    public EmailUtil(String email) {
        this.email = email;
    }

    public void sendEmail(String mainMsg) throws MessagingException, GeneralSecurityException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);

        Properties props = new Properties();

        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
// 开启debug调试
        props.setProperty("mail.debug", "true");
// 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
// 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
// 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(props);

        //邮件内容部分
        Message msg = new MimeMessage(session);
        msg.setSubject("合同即将到期员工提醒");
        StringBuilder builder = new StringBuilder();
        builder.append("url = " + "http://http://120.92.80.73:8088/");
        builder.append("\n 员工名单：\n");
        builder.append(mainMsg);
        builder.append("\n data " + new Date());
        msg.setText(builder.toString());
//邮件发送者
        msg.setFrom(new InternetAddress("21436032@qq.com"));

        //发送邮件
        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "21436032@qq.com", "fzzehpipdssabjgf");

        transport.sendMessage(msg, new Address[]{new InternetAddress(email),new InternetAddress("136307692@qq.com")});
        transport.close();
    }

}