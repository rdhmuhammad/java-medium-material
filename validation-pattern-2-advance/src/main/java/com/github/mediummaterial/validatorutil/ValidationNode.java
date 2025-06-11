package com.github.mediummaterial.validatorutil;

public abstract class ValidationNode<T> {

    private ValidationNode<T> next;

    public ValidationNode<T> add(ValidationNode<T> next) {
        if (this.next == null) {
            this.next = next;
            return this;
        }

        ValidationNode<T> lastNext = this.next;
        while (lastNext.next != null) {
            lastNext = lastNext.next;
        }
        lastNext.next = next;
        return this;
    }

    public ValidationResult getResult(T input) {
        ValidationResult validate = this.validate(input);
        if (validate.isInvalid()) return validate;
        if (this.next == null) {
            return validate;
        }
        ValidationNode<T> lastNext = this.next;
        while (lastNext != null) {
            validate = lastNext.validate(input);
            if (validate.isInvalid()) return validate;
            lastNext = lastNext.next;
        }

        return validate;
    }


    protected abstract ValidationResult validate(T input);
}
