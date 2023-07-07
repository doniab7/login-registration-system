package com.example.loginregistration.login;

import com.example.loginregistration.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

private final AppUserService appUserService;
    private final SessionRepository<Session> sessionRepository;

@Autowired
    public LoginController(AppUserService appUserService, SessionRepository<Session> sessionRepository) {
        this.appUserService = appUserService;
    this.sessionRepository = sessionRepository;
}

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isValid = appUserService.isValidLogin(loginRequest.getUsername(), loginRequest.getEmail(), loginRequest.getPassword());
        if (isValid) {
            // Authentication successful
            Session session = sessionRepository.createSession();
            session.setAttribute("username", loginRequest.getUsername());
            session.setAttribute("email", loginRequest.getEmail());
            sessionRepository.save(session);
            String sessionId = session.getId();

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Session-Id", sessionId);

            return  ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body("Login successful");
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


}
