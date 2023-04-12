package kz.kurol.auctionapi.utils.validators;


import kz.kurol.auctionapi.models.Client;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


//@Component
public class ClientValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        Client cl = (Client) target;
//        if(clientService.findByEmail(cl.getEmail()).isPresent){
//            errors.rejectValue("email","","This email is already taken");
//        }
    }
}
