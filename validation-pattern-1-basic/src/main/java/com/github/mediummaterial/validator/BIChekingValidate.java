package com.github.mediummaterial.validator;

import com.github.mediummaterial.request.RegisterRequest;
import com.github.mediummaterial.validatorutil.ValidationNode;
import com.github.mediummaterial.validatorutil.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BIChekingValidate extends ValidationNode<RegisterRequest> {
    @Override
    protected ValidationResult validate(RegisterRequest input) {
        log.info("hit service BI");
        return ValidationResult.valid();
    }
}