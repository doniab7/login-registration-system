package com.example.loginregistration.registration;

import com.example.loginregistration.appuser.AppUser;
import com.example.loginregistration.appuser.AppUserRole;
import com.example.loginregistration.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private EmailValidator emailValidator;
    private final AppUserService appUserService;

    public ResponseEntity<String> register(RegistrationRequest request) {
    boolean isValidEmail = emailValidator.test(request.getEmail());
    if (!isValidEmail){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not valid");
    }

    String fullName = request.getFirstName() + " " + request.getLastName();

        return appUserService.signUpUser(
                new AppUser( fullName, request.getFirstName(), request.getEmail(), request.getPassword(), AppUserRole.USER)
        );
    }
}
