package monitor.warn.mail;

import org.apache.commons.lang.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.util.Date;
import java.util.Properties;

/**
 * 利用java.mail的邮件发送程序
 */

public class SendMailTest {
    public static void main(String[] args) {
        String title = "Dep warn mail Test";//所发送邮件的标题
        String from = "boluo_1314@163.com";//从那里发送
        String sendTo[] = {"boluo_1314@163.com", "yutaoqd@163.com"};//发送到那里
        //邮件的文本内容，可以包含html标记则显示为html页面
        String content = " DEP监控系统邮件预警测试！<br><a href='http://10.143.19.179:8080/dep'>点击此处登录系统</a> ";
        //所包含的附件，及附件的重新命名
        String fileNames[] = {"D:\\dep\\log\\error.log,error.log"};
        try {
            // MailSender mailsender = new MailSender();
            sendmail(title, from, sendTo, content, fileNames, "text/html;charset=gb2312");
            //  sendmail(title, from, sendTo, content, null, "text/html;charset=gb2312");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendmail(String subject, String from, String[] to, String text, String[] filenames, String mimeType) throws Exception {
        Properties props = new Properties();

        String smtp = "smtp.163.com"; //设置发送邮件所用到的smtp

        javax.mail.Session mailSession; //邮件会话对象
        javax.mail.internet.MimeMessage mimeMsg; //MIME邮件对象

        props = java.lang.System.getProperties(); //获得系统属性对象
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

        for (int i = 0; i < filenames.length; i++) {
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//选择出每一个附件名
            String filename = filenames[i].split(",")[0];
            System.out.println("附件名：" + filename);
            String displayname = filenames[i].split(",")[1];
//得到数据源
            FileDataSource fds = new FileDataSource(filename);
//得到附件本身并至入BodyPart
            messageBodyPart2.setDataHandler(new DataHandler(fds));
            messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
            multipart.addBodyPart(messageBodyPart2);
        }
        mimeMsg.setContent(multipart);
//设置信件头的发送日期
        mimeMsg.setSentDate(new Date());
        mimeMsg.saveChanges();
//发送邮件
        transport.send(mimeMsg);
        transport.close();
    }

}
