package ua.everybuy.buisnesslogic.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.util.RequestSenderService;
import ua.everybuy.database.entity.Subscriber;
import ua.everybuy.database.repository.SubscriberRepository;
import ua.everybuy.errorhandling.exception.impl.EmailAlreadyExistsException;
import ua.everybuy.errorhandling.exception.impl.EmailNoStubscribedException;
import ua.everybuy.routing.model.request.SubscriberRequest;
import ua.everybuy.routing.model.response.resposedataimpl.StatusResponse;
import ua.everybuy.routing.model.response.resposedataimpl.SubscriberResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final RequestSenderService requestSenderService;

    public StatusResponse addEmailToMailing(SubscriberRequest subscriberRequest){
        return prepareStatusResponseForAddingToMailing(subscriberRequest.getEmail());
    }

    public StatusResponse addEmailToMailing(HttpServletRequest httpServletRequest){
        return prepareStatusResponseForAddingToMailing(extractEmailFromRequest(httpServletRequest));
    }

    public void deleteSubscriber(SubscriberRequest subscriberRequest){
        String email = subscriberRequest.getEmail();
        deleteSubscribe(email);
    }

    public void deleteUserFromSubscribe(HttpServletRequest request){
        deleteSubscribe(extractEmailFromRequest(request));
    }

    public List<Subscriber> findAll(){
        return subscriberRepository.findAll();
    }

    private Optional<Subscriber> findSubscriberByEmail(String email){
        return subscriberRepository.findMailingByEmail(email);

    }

    private void saveSubscriber(String email){
        subscriberRepository.save(new Subscriber(email));
    }

    private void emailAlreadyExists(String email){
        Optional<Subscriber> subscriberByEmail = findSubscriberByEmail(email);
        if (subscriberByEmail.isPresent()){
            throw new EmailAlreadyExistsException(email);
        }
    }

    private StatusResponse prepareStatusResponseForAddingToMailing(String email){
        emailAlreadyExists(email);
        saveSubscriber(email);
        return new StatusResponse(HttpStatus.CREATED.value(), new SubscriberResponse(email));
    }

    private String extractEmailFromRequest(HttpServletRequest request){
        return requestSenderService.doRequest(request).getBody().getData().email();
    }

    private void deleteSubscribe(String email){
        Subscriber subscriber = findSubscriberByEmail(email).orElseThrow(() -> new EmailNoStubscribedException(email));
        subscriberRepository.delete(subscriber);
    }


}
