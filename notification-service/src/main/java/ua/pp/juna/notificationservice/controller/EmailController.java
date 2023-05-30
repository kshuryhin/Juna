package ua.pp.juna.notificationservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.juna.notificationservice.model.EmailDetails;
import ua.pp.juna.notificationservice.service.EmailService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/sendMail")
    public EmailDetails sendMail(@RequestBody EmailDetails details) {
        emailService.sendSimpleMail(details);
        return details;
    }

    @RabbitListener(queues = "rabbitmq.queue", id = "listener")
    public void consumeMessageFromQueue(EmailDetails emailDetails) {
        log.info("Inside rabbit listener");
        emailService.sendSimpleMail(emailDetails);
    }


    @PostMapping("/sendMailToFollowers")
    public EmailDetails sendMailToFollowers(@RequestBody EmailDetails details) {
        emailService.sendEmailToFollowers(details);
        return details;
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        String status = emailService.sendMailWithAttachment(details);

        return status;
    }
}
