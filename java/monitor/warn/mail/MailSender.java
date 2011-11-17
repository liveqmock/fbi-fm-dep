package monitor.warn.mail;

import org.apache.commons.lang.StringUtils;

import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * 利用java.mail的邮件发送程序
 */

public class MailSender {

    public static void sendmail(String subject, String from, String[] to, String text, String mimeType) throws Exception {
        Properties props = new Properties();

        String smtp = "smtp.163.com"; //设置发送邮件所用到的smtp

        Session mailSession; //邮件会话对象
        javax.mail.internet.MimeMessage mimeMsg; //MIME邮件对象

        props = System.getProperties(); //获得系统属性对象
        props.put("mail.smtp.host", smtp); //设置SMTP主机
        props.put("mail.smtp.auth", "true"); //是否到服务器用户名和密码验证
        mailSession = Session.getDefaultInstance(props, new Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("boluo_1314@163.com",
                        "boluo1314");
            }
        });
        javax.mail.Transport transport = mailSession.getTransport("smtp");
        mimeMsg = new javax.mail.internet.MimeMessage(mailSession);
        if (!StringUtils.isEmpty(from)) {
            InternetAddress sentFrom = new InternetAddress(from);
            mimeMsg.setFrom(sentFrom); //设置发送人地址
        }

        InternetAddress[] sendTo = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            System.out.println("发送到:" + to[i]);
            sendTo[i] = new InternetAddress(to[i]);
        }

        mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
        mimeMsg.setSubject(subject, "gb2312");

        MimeBodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent(text, mimeType);

        Multipart multipart = new MimeMultipart();//附件传输格式
        multipart.addBodyPart(messageBodyPart1);

        mimeMsg.setContent(multipart);
        //设置信件头的发送日期
        mimeMsg.setSentDate(new Date());
        mimeMsg.saveChanges();
        //发送邮件
        transport.send(mimeMsg);
        transport.close();
    }

}
