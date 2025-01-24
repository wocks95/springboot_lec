package com.min.app04.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class MailSendUtil {
  
  @Autowired
  private JavaMailSender javaMailSender;
  
  // application.properties 파일에 있는 spring.mail.username 속성 값을 가져옵니다.
  @Value("${spring.mail.username}")
  private String username; // 보내는 사람으로 사용합니다.
  
  /**
   * 이메일을 전송하는 메소드입니다.
   * @param to 받는 사랍입니다.
   * @param subject 이메일의 제목입니다.
   * @param contents 이메일의 내용입니다.
   */
  public void sendMail(String to, String subject, String contents) {
    try {
      // 이메일 본문 작성
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      mimeMessage.setContent(contents, "text/html; charset=UTF-8");
      
      // 보내는 사람, 받는 사람, 제목 작성
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
      helper.setFrom(username);
      helper.setTo(to);
      helper.setSubject(subject);
      
      // 이메일 전송하기
      javaMailSender.send(mimeMessage);
      
    } catch (Exception e) {
      e.printStackTrace();  
    }
  }
  
  
}
