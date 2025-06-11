package com.github.mediummaterial.controller;

import com.github.mediummaterial.entity.User;
import com.github.mediummaterial.exception.InvalidDataException;
import com.github.mediummaterial.request.RegisterRequest;
import com.github.mediummaterial.validator.BIChekingValidate;
import com.github.mediummaterial.validator.EmailPhoneDuplicateValidate;
import com.github.mediummaterial.validator.KYCValidate;
import com.github.mediummaterial.validator.SpoofingValidate;
import com.github.mediummaterial.validatorutil.ColumnDuplicationValidate;
import com.github.mediummaterial.validatorutil.RequestClassLoader;
import com.github.mediummaterial.validatorutil.ValidationNode;
import com.github.mediummaterial.validatorutil.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    EmailPhoneDuplicateValidate emailPhoneDuplicateValidate;

    @Autowired
    BIChekingValidate biChekingValidate;

    @Autowired
    KYCValidate kycValidate;

    @Autowired
    SpoofingValidate spoofingValidate;


    // ================= validation utility ============================
    @Autowired
    private RequestClassLoader requestClassLoader;

    @Autowired
    private EntityManager entityManager;

    protected <E, T> ValidationNode<T> columnDuplicationValidate(Class<E> entity, Class<T> request) {
        return new ColumnDuplicationValidate<>(entity, request, entityManager, requestClassLoader);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        ValidationResult validationResult = columnDuplicationValidate(User.class, RegisterRequest.class)
                .add(biChekingValidate)
                .add(kycValidate)
                .add(spoofingValidate)
                .getResult(request);

        if (validationResult.isInvalid()) {
            throw new InvalidDataException(validationResult.getMessage());
        }

        return new ResponseEntity<>(validationResult, HttpStatus.OK);
    }
}
