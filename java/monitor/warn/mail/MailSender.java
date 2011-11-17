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
 * ����java.mail���ʼ����ͳ���
 */

public class MailSender {

    public static void sendmail(String subject, String from, String[] to, String text, String mimeType) throws Exception {
        Properties props = new Properties();

        String smtp = "smtp.163.com"; //���÷����ʼ����õ���smtp

        Session mailSession; //�ʼ��Ự����
        javax.mail.internet.MimeMessage mimeMsg; //MIME�ʼ�����

        props = System.getProperties(); //���ϵͳ���Զ���
        props.put("mail.smtp.host", smtp); //����SMTP����
        props.put("mail.smtp.auth", "true"); //�Ƿ񵽷������û�����������֤
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
            mimeMsg.setFrom(sentFrom); //���÷����˵�ַ
        }

        InternetAddress[] sendTo = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            System.out.println("���͵�:" + to[i]);
            sendTo[i] = new InternetAddress(to[i]);
        }

        mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
        mimeMsg.setSubject(subject, "gb2312");

        MimeBodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent(text, mimeType);

        Multipart multipart = new MimeMultipart();//���������ʽ
        multipart.addBodyPart(messageBodyPart1);

        mimeMsg.setContent(multipart);
        //�����ż�ͷ�ķ�������
        mimeMsg.setSentDate(new Date());
        mimeMsg.saveChanges();
        //�����ʼ�
        transport.send(mimeMsg);
        transport.close();
    }

}
