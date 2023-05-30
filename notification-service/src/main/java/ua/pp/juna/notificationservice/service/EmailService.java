package ua.pp.juna.notificationservice.service;


import ua.pp.juna.notificationservice.model.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

    String sendEmailToFollowers(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}
