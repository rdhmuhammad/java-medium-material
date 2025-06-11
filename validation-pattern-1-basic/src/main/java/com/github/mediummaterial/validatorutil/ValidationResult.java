package com.github.mediummaterial.validatorutil;

import lombok.Data;

@Data
public class ValidationResult {
    private final Boolean isValid;

    private final String message;

    public ValidationResult(Boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public boolean isInvalid(){
        return Boolean.FALSE.equals(this.isValid);
    }

    public boolean isValid(){
        return Boolean.TRUE.equals(this.isValid);
    }

    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult invalid(String message) {
        return new ValidationResult(false, message);
    }

}
