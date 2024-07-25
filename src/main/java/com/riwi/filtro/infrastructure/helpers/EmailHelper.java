package com.riwi.filtro.infrastructure.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailHelper {

  private final JavaMailSender javaMailSender;

  public void sendMail(String receiver, String client, String surveyTitle) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    String htmlContent = this.readHTMLTemplate(client, surveyTitle);

    try {
      mimeMessage.setFrom(new InternetAddress("andresx1277@gmail.com"));
      mimeMessage.setSubject("Survey creation confirm");
      mimeMessage.setRecipients(RecipientType.TO, receiver);
      mimeMessage.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);
      javaMailSender.send(mimeMessage);
      System.out.println("Email send successfully");
    } catch (Exception e) {
      System.out.println("Something went wrong " + e);
    }
  }

  private String readHTMLTemplate(String name, String surveyTitle) {
    final Path path = Paths.get("src/main/resources/template/EmailTemplate.html");

    try (var lines = Files.lines(path)) {
      var html = lines.collect(Collectors.joining());

      return html.replace("{name}", name).replace("{survey}", surveyTitle);
    } catch (IOException e) {
      throw new IllegalArgumentException("Something went wrong while creating the html template");
    }
  }
}