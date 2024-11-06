package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.Subscriber;
import ua.everybuy.database.repository.SubscriberRepository;
import ua.everybuy.errorhandling.exception.impl.EmailAlreadyExistsException;
import ua.everybuy.routing.model.request.SubscriberRequest;
import ua.everybuy.routing.model.response.resposedataimpl.StatusResponse;
import ua.everybuy.routing.model.response.resposedataimpl.SubscriberResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public StatusResponse addEmailToMailing(SubscriberRequest subscriberRequest){
        String email = subscriberRequest.getEmail();
        emailAlreadyExists(email);
        saveSubscriber(email);
        return new StatusResponse(HttpStatus.CREATED.value(), new SubscriberResponse(email));
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

}
