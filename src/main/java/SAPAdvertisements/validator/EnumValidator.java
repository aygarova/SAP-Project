package SAPAdvertisements.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValidator{
    Class<? extends Enum<?>> enumClass();
    String message() default "must be any of enum UserType(USER, ADMIN) or AnnouncementStatus(Active, Inactive)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
