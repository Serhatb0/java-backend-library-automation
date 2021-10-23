package com.tamercapital.tamercapital.validator.abstracts;


import com.tamercapital.tamercapital.validator.concretes.UniqueBookNameValidator;

import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = {UniqueBookNameValidator.class})
public @interface UniqueBookName {
    String message() default "Bu Kitap Adı Zaten Mevcut";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}