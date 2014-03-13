/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.andersonoliveirasilva.jbackup.utils;

import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author anderson
 */
public class JavaMail {

    private Properties props = new Properties();
    private String para;
    private String assunto;
    private String textoMensagem;

    public JavaMail(String para, String assunto, String textoMensagem) {
        this.para = para;
        this.assunto = assunto;
        this.textoMensagem = textoMensagem;
        /**
         * Parâmetros de conexão com servidor Hotmail
         */
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.live.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
    }

    public void enviarEmail(List<File> arquivosAnexos) {
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ande1710@hotmail.com", "xxxxxxxx");
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ande1710@hotmail.com")); //Remetente

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.para)); //Destinatário(s)
            message.setSubject(this.assunto);//Assunto
            message.setText(this.textoMensagem);

            /**
             * Anexando arquivos
             */
            MimeBodyPart mbp2 = new MimeBodyPart();
            Multipart mp = new MimeMultipart();

            for (File file : arquivosAnexos) {
                FileDataSource fds = new FileDataSource(file.getAbsoluteFile()); /*c:\\teste\\User.txt*/

                mbp2.setDataHandler(new DataHandler(fds));
                
                mbp2.setFileName(fds.getName());

                mp.addBodyPart(mbp2);

                message.setContent(mp);
            }

            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
