package com.jobbud.ws.exceptions;

import com.jobbud.ws.annotations.UniqueUsername;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

import org.springframework.stereotype.Component;

@Component
public class UniqueUsernameValidation implements ConstraintValidator<UniqueUsername, String> {
    private final UserRepository userRepository;

    public UniqueUsernameValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        UserEntity userEntity = userRepository.findByUsername(value);
        if (userEntity != null) return false;
        else return true;
    }
}
