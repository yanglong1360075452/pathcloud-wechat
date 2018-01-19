package com.wizinno.pathcloud.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by HP on 2016/5/25.
 */
public class EmailMessage {

    private static Logger logger = LoggerFactory.getLogger(EmailMessage.class);

    private MimeMessage mimeMsg; //MIME邮件对象
    private Session session; //邮件会话对象
    private Properties props; //系统属性
    private String smtpHost;
    private boolean needAuth = false; //smtp是否需要认证
    //smtp认证用户名和密码
    private String username;
    private String password;
    private String subject;
    private String body;
    private List<File> fileAffixs;
    private String from;
    private String to;
    private String copyTo;
    private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
    private Map<String,File> files;


    /**
     * Constructor
     * @param smtpHost 邮件发送服务器
     */
    public EmailMessage(String smtpHost){
        setSmtpHost(smtpHost);
        createMimeMessage();
    }

    public EmailMessage(){

    }

    public void init(){
        if(props == null)
            props = System.getProperties(); //获得系统属性对象
        props.put("mail.smtp.host",smtpHost); //设置SMTP主机
        createMimeMessage();
        if(needAuth){
            props.put("mail.smtp.auth","true");
        }else{
            props.put("mail.smtp.auth","false");
        }

        //设置邮件主题
        try{
            mimeMsg.setSubject(subject, "UTF-8");
        }catch(Exception e){
            logger.error("Can't set email subject:" + subject, e);
        }

        //设置邮件正文
        BodyPart bp = new MimeBodyPart();
        try{
            bp.setContent(""+body,"text/html;charset=UTF-8");
            mp.addBodyPart(bp);
        } catch(Exception e){
            logger.error("Can't set email body:" + body, e);
        }

        //设置收件人
        if(to != null)
            try {
                mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            } catch (MessagingException e) {
                logger.error("Can't set recipients:" + to, e);
            }

        //设置发信人
        if (from != null){
            try {
                mimeMsg.setFrom(new InternetAddress(from));
            } catch (MessagingException e) {
                logger.error("Can't set from:" + from, e);
            }
        }

        //设置附件
        if (fileAffixs != null){
            for (File fileAffix : fileAffixs){
                bp = new MimeBodyPart();
                FileDataSource fileds = new FileDataSource(fileAffix);
                try {
                    bp.setDataHandler(new DataHandler(fileds));
                    bp.setFileName(MimeUtility.encodeText(fileds.getName(),"UTF-8","B"));
                    mp.addBodyPart(bp);
                } catch (MessagingException e) {
                    logger.error("Can't add attachment", e);
                } catch (UnsupportedEncodingException e) {
                    logger.error("Can't add attachment", e);
                }
            }
        }

        //设置附件以及自定义附件名
        if (files != null){
            Iterator iterator = files.entrySet().iterator();
            while(iterator.hasNext()){
                @SuppressWarnings("unchecked")
                Map.Entry<String, File> entry = (Map.Entry)iterator.next();

                BodyPart bodyPart = new MimeBodyPart();
                FileDataSource fileDataSource = new FileDataSource(entry.getValue());
                try {
                    bodyPart.setDataHandler(new DataHandler(fileDataSource));
                    bodyPart.setFileName(MimeUtility.encodeText(entry.getKey()));
                    mp.addBodyPart(bodyPart);
                } catch (MessagingException e) {
                    logger.error("Can't add attachment", e);
                } catch (UnsupportedEncodingException e) {
                    logger.error("Can't add attachment", e);
                }
            }
        }


        //设置抄送对象
        if (copyTo != null){
            try {
                mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyTo));
            } catch (MessagingException e) {
                logger.error("Can't add cc list:", e);
            }
        }
    }

    /**
     * 设置邮件发送服务器
     * @param smtpHost String
     */
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }


    /**
     * 创建MIME邮件对象
     * @return
     */
    public boolean createMimeMessage(){
        try {
            session = Session.getDefaultInstance(props,null); //获得邮件会话对象
        }
        catch(Exception e){
            logger.error("Failed to get default instance！", e);
            return false;
        }

        try {
            mimeMsg = new MimeMessage(session); //创建MIME邮件对象
            mp = new MimeMultipart();

            return true;
        } catch(Exception e){
            logger.error("Failed to create email message!", e);
            return false;
        }
    }

    /**
     * 设置SMTP是否需要验证
     * @param needAuth
     */
    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }

    /**
     * 设置邮件主题
     * @param subject
     * @return
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 设置邮件正文
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 添加附件
     * @param fileAffixs
     */
    public void setFileAffixs(List<File> fileAffixs) {
        this.fileAffixs = fileAffixs;
    }

    /**
     * 添加文件及自定义文件名
     * @param files
     */
    public void setFiles(Map<String, File> files) {
        this.files = files;
    }

    /**
     * 设置发信人
     * @param from String
     */
    public void setFrom(String from) {
        this.from = from;
    }
    /**
     * 设置收信人
     * @param to String
     */
    public void setTo(String to){
        this.to = to;
    }


    /**
     * 设置抄送人
     * @param copyTo
     * @return
     */
    public void setCopyTo(String copyTo) {
        this.copyTo = copyTo;
    }

    /**
     * 发送邮件
     */
    public boolean sendOut(){
        try{
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            Session mailSession = Session.getInstance(props,null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String)props.get("mail.smtp.host"),username,password);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            logger.info("Send email " + mimeMsg.getSubject() + " successfully to " + mimeMsg.getRecipients(Message.RecipientType.TO)[0]);
            transport.close();
            return true;
        } catch(Exception e) {
            logger.error("Failed to send email", e);
            return false;
        }
    }

    public String getCopyTo() {
        return copyTo;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public List<File> getFileAffixs() {
        return fileAffixs;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isNeedAuth() {
        return needAuth;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, File> getFiles() {
        return files;
    }
}
