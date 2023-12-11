package com.jobbud.ws.annotations;

import com.jobbud.ws.exceptions.UniqueUsernameValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;


@Constraint(validatedBy = UniqueUsernameValidation.class) // validation annotation
@Target(ElementType.FIELD) // define where the annotation can be use
@Retention(RetentionPolicy.RUNTIME)// tell JVM to persist annotation in runtime
public @interface UniqueUsername {
    String message() default "Username already exists. ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
