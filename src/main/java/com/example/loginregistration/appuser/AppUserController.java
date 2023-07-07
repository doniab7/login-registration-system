package com.example.loginregistration.appuser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {

    private final SessionRepository<Session> sessionRepository;

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(SessionRepository<Session> sessionRepository, AppUserService appUserService) {
        this.sessionRepository = sessionRepository;
        this.appUserService = appUserService;
    }

    @GetMapping("/api/user/{username}")
    public ResponseEntity<String> getUser(@PathVariable String username, @RequestHeader("Session-Id") String sessionId) {
        Session session = sessionRepository.findById(sessionId);

        if (session != null) {
            String storedEmail = (String) session.getAttribute("email");
            System.out.println(storedEmail);
            UserDetails appUser = appUserService.loadUserByEmail(storedEmail);

            if (appUser.getUsername().equals(username)) {
                // Process the user information
                return ResponseEntity.ok(appUser.toString());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session: Unauthorized access to user profile.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid session: Unauthorized access to user profile.");
        }
    }


}
