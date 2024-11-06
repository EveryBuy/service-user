package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.everybuy.errorhandling.exception.impl.EmailSendPermissionException;
import ua.everybuy.routing.model.request.NewsLetterRequest;
import ua.everybuy.routing.model.response.resposedataimpl.NewsLetterResponse;
import ua.everybuy.routing.model.response.resposedataimpl.StatusResponse;


@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final SubscriberService subscriberService;
    private final JavaMailSender mailSender;
    @Value("${internal.mailing.password}")
    private String internalPassword;

    public StatusResponse sendNewsLetters(NewsLetterRequest newsLetterRequest){
        validPassword(newsLetterRequest.getInternalPassword());
        subscriberService.findAll()
                .forEach(mailing -> sendEmail(mailing.getEmail(),
                        newsLetterRequest.getSubject(),
                        newsLetterRequest.getText()));

        return new StatusResponse(HttpStatus.OK.value(),
                new NewsLetterResponse());
    }
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private void validPassword(String password){
        if (password==null || !password.equals(internalPassword)){
            throw new EmailSendPermissionException();
        }
    }

}
