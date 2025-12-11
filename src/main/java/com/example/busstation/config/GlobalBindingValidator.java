package com.example.busstation.config;

import com.example.busstation.exception.BusStatusInvalid;
import com.example.busstation.model.BusStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

@ControllerAdvice
public class GlobalBindingValidator {

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(BusStatus.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) throws IllegalArgumentException {

                if (text == null || text.isBlank()) {
                    throw new BusStatusInvalid("Status cannot be empty");
                }

                String value = text.trim().toUpperCase();

                if (!value.equals("ACTIVE") && !value.equals("DOWN")) {
                    throw new BusStatusInvalid("Status must be ACTIVE or DOWN");
                }

                setValue(BusStatus.valueOf(value));
            }
        });
    }
}

