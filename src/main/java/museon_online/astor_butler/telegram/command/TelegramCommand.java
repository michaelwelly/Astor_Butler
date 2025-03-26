package museon_online.astor_butler.telegram.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TelegramCommand {
    String value();
    String description() default "";
}