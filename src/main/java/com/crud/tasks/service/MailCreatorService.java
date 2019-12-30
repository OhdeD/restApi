package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message){
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "https://ohded.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview_message", message.substring(0,50));
        context.setVariable("goodbye", "Best regards, " + adminConfig.getAdminName() + ".");
        context.setVariable("company", adminConfig.getCompanyName() + " || " + adminConfig.getCompanyStreet() + " " + adminConfig.getCompanyNo() + " || e-mail: " + adminConfig.getCompanyMail() + " || phone: " + adminConfig.getCompanyNumber());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
