package com.harishsai.HelloWorld.controller;

import com.harishsai.HelloWorld.repository.UserRepository;
import com.harishsai.HelloWorld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.harishsai.HelloWorld.Models.User;
import com.harishsai.HelloWorld.utils.JwtUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String , String> body) {
        String email = body.get("email");
        String password = passwordEncoder.encode(body.get("password"));
        if(userRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("user already exists" , HttpStatus.CONFLICT);
        }
        userService.createUser(User.builder().email(email).password(password).build());
        return new ResponseEntity<>("user created sucessfully" , HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String , String> body) {
        String email = body.get("email");
        String password = body.get("password");
        var OptionalUser = userRepository.findByEmail(email);
        if(OptionalUser.isEmpty())
            return new ResponseEntity<>("invalid user", HttpStatus.NOT_FOUND);
        User user = OptionalUser.get();
        if(!passwordEncoder.matches(password , user.getPassword()))
            return new ResponseEntity<>("incorrect password", HttpStatus.NOT_FOUND);
        String token = jwtUtil.generateJwtToken(email);
        return ResponseEntity.ok(Map.of("token" , token));
    }
}
