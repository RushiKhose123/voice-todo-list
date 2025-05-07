package com.example.demo.ExceptionHandling;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;

public class HandleException {

    public static void throwRuntimeException(String customMessage, Exception exception) {
        if (customMessage==null || customMessage.isEmpty()) {
            throw new RuntimeException(exception.getMessage(), exception);
        }else{
            throw new RuntimeException(customMessage, exception);
        }
    }


    public static void throwMailException(String customMessage, Exception exception) {
        if (customMessage==null || customMessage.isEmpty()) {
            throw new MailSendException(exception.getMessage(), exception);
        }else{
            throw new MailSendException(customMessage, exception);
        }
    }

    public static void throwIllegarArgumentException(String customMessage, Exception exception) {
        if (customMessage==null || customMessage.isEmpty()) {
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }else{
            throw new IllegalArgumentException(customMessage, exception);
        }
    }

}
