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
 * ����java.mail���ʼ����ͳ���
 */

public class SendMailTest {
    public static void main(String[] args) {
        String title = "Dep warn mail Test";//�������ʼ��ı���
        String from = "boluo_1314@163.com";//�����﷢��
        String sendTo[] = {"boluo_1314@163.com", "yutaoqd@163.com"};//���͵�����
        //�ʼ����ı����ݣ����԰���html�������ʾΪhtmlҳ��
        String content = " DEP���ϵͳ�ʼ�Ԥ�����ԣ�<br><a href='http://10.143.19.179:8080/dep'>����˴���¼ϵͳ</a> ";
        //�������ĸ���������������������
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

        String smtp = "smtp.163.com"; //���÷����ʼ����õ���smtp

        javax.mail.Session mailSession; //�ʼ��Ự����
        javax.mail.internet.MimeMessage mimeMsg; //MIME�ʼ�����

        props = java.lang.System.getProperties(); //���ϵͳ���Զ���
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

        for (int i = 0; i < filenames.length; i++) {
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//ѡ���ÿһ��������
            String filename = filenames[i].split(",")[0];
            System.out.println("��������" + filename);
            String displayname = filenames[i].split(",")[1];
//�õ�����Դ
            FileDataSource fds = new FileDataSource(filename);
//�õ�������������BodyPart
            messageBodyPart2.setDataHandler(new DataHandler(fds));
            messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
            multipart.addBodyPart(messageBodyPart2);
        }
        mimeMsg.setContent(multipart);
//�����ż�ͷ�ķ�������
        mimeMsg.setSentDate(new Date());
        mimeMsg.saveChanges();
//�����ʼ�
        transport.send(mimeMsg);
        transport.close();
    }

}
