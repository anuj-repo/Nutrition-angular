package com.fertilizer.annotation;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern.Flag;

import com.fertilizer.annotation.validator.BlankOrPatternValidator;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = BlankOrPatternValidator.class)
public @interface BlankOrPattern {
    String regexp();
    Flag[] flags() default {};
    String message() default "Invalid Pattern";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
