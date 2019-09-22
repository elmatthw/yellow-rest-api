package by.yellow.running;

import by.yellow.running.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (!user.getPasswordConfirm().equals(user.getPassword())){
            errors.reject("passwordConfirm");
        }
    }
}
