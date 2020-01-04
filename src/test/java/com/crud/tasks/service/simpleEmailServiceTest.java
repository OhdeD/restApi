package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(MockitoJUnitRunner.class)
public class simpleEmailServiceTest {
    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

        @Test
        public void shouldSendEmail() {
            //Given
            Mail mail = new Mail("test@test.com", "Test", "Test message.");

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setText(mail.getMessage());
            simpleMailMessage.setSubject(mail.getSubject());
            simpleMailMessage.setTo(mail.getMailTo());

            //When
            simpleEmailService.send(mail);

            //Then
            verify(javaMailSender, times(1)).send(simpleMailMessage);
        };
}