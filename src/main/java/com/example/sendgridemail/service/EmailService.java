package com.example.sendgridemail.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.sendgridemail.dto.EmailDTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {
    private final String templatePath = "/templates/";

    public void sendEmail(EmailDTO emailDto) {
        try {
            Email from = new Email(emailDto.from());
            Email to = new Email(emailDto.to());

            String htmlTemplate = readTemplate(templatePath + "setor_modelo_2.html");
            String htmlContent = buildTemplateEmail(htmlTemplate, emailDto);

            Content emailContent = new Content("text/html", htmlContent);
            Mail mail = new Mail(from, "Nova solicitação de visita", to, emailContent);

            SendGrid sendGrid = new SendGrid(System.getenv("SENDGRID_API_KEY"));
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao enviar e-mail HTML", e);
        }
    }

    private String readTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8);

    }

    public String buildTemplateEmail(String htmlTemplate, EmailDTO emailDto) throws IOException {
        return htmlTemplate
                .replace("{{nome}}", emailDto.name())
                .replace("{{email}}", emailDto.email())
                .replace("{{telefone}}", emailDto.phone())
                .replace("{{data}}", emailDto.date())
                .replace("{{hora}}", emailDto.time());
    }
}