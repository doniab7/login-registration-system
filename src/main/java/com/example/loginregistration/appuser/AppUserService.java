package com.example.loginregistration.appuser;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
private final static String USER_NOT_FOUND_MSG = "user with username % not found";
    private final static String EMAIL_NOT_FOUND_MSG = "user with email % not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> optionalUser = appUserRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
        }
    }


    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{

        Optional<AppUser> optionalUser = appUserRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND_MSG, email));
        }
    }

    public ResponseEntity<String> signUpUser(AppUser appUser){
       boolean userExists = appUserRepository.findByEmail(appUser.getEmail())
               .isPresent();
       if (userExists){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already taken");
       }
       String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
       appUser.setPassword(encodedPassword);

       appUserRepository.save(appUser);

        return ResponseEntity.ok("User " + appUser.getName() + " has been registered successfully");
    }

}