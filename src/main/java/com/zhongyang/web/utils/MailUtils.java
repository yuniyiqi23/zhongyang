package com.zhongyang.web.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

    //邮件服务器主机地址
//  private static String HOST="localhost";
    private static String HOST = "smtp.qq.com";
    //帐号
    private static String ACCOUNT = "378532514@qq.com";
    //密码
    private static String PASSWORD = "thvhdvugbbxccagc";


    /**
     * @param addresses   发送邮件给谁
     * @param title    邮件的标题
     * @param emailMsg 邮件信息
     */
    public static void sendMail(List<String> addresses, String title, String emailMsg) throws AddressException, MessagingException {
        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        //设置发送的协议
        props.setProperty("mail.transport.protocol", "SMTP");

        //设置发送邮件的服务器
        props.setProperty("mail.host", HOST);
        props.setProperty("mail.smtp.auth", "true");// 指定验证为true

        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //设置发送人的帐号和密码      帐号          授权码
                return new PasswordAuthentication(ACCOUNT, PASSWORD);
            }
        };
        //会话
        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);

        //设置发送者
        message.setFrom(new InternetAddress(ACCOUNT));

        //设置发送方式与接收者
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toUser));

        //设置收件人，多个
        final int num = addresses.size();
        InternetAddress to_address[] = new InternetAddress[num];
        for(int i=0; i<num; i++){
            to_address[i] = new InternetAddress(addresses.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, to_address);

        //设置邮件主题
        message.setSubject(title);

        //设置邮件内容
        message.setContent(emailMsg, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }


    public static void main(String[] args) throws AddressException, MessagingException {
        String emailStr = PropertiesUtil.getPropertieValue("email.users");
        List<String> adds = Arrays.asList(emailStr.split(","));
        MailUtils.sendMail(adds, "中扬联众", "测试成功123！");
    }
}
