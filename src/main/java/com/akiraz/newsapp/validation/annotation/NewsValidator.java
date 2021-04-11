package com.akiraz.newsapp.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.akiraz.newsapp.validation.NewsValidatorCheck;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NewsValidatorCheck.class)
@Documented
public @interface NewsValidator
{
    String message() default "News id not exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}