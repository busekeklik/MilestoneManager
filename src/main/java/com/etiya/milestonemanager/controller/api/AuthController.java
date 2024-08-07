package com.etiya.milestonemanager.controller.api;

import com.etiya.milestonemanager.business.dto.UserDto;
import com.etiya.milestonemanager.business.services.IUserServices;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/api/1.0")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final IUserServices<UserDto, UserEntity> userService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(IUserServices<UserDto, UserEntity> userService, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        logger.info("Authentication request received for email: {}", request.getEmail());

        Optional<UserEntity> userOptional = userRepository.findFirstByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            logger.info("User found with email: {}", request.getEmail());

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                logger.info("Password matches for email: {}", request.getEmail());
                UserDto userDto = userService.entityToDto(user);
                return ResponseEntity.ok(new AuthResponse("success", "User authenticated successfully", userDto));
            } else {
                logger.warn("Password does not match for email: {}", request.getEmail());
            }
        } else {
            logger.warn("No user found with email: {}", request.getEmail());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("error", "Geçersiz kullanıcı adı ya da şifre :(", null));
    }

    @Setter
    @Getter
    public static class AuthRequest {
        private String email;
        private String password;
    }

    @Setter
    @Getter
    public static class AuthResponse {
        private String status;
        private String message;
        private UserDto user;

        public AuthResponse(String status, String message, UserDto user) {
            this.status = status;
            this.message = message;
            this.user = user;
        }
    }
}
