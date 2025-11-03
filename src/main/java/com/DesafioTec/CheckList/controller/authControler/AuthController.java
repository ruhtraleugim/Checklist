package com.DesafioTec.CheckList.controller.authControler;

import com.DesafioTec.CheckList.Security.JwtUtil;
import com.DesafioTec.CheckList.dto.auth.AuthRequestDTO;
import com.DesafioTec.CheckList.dto.auth.AuthResponseDTO;
import com.DesafioTec.CheckList.dto.user.UserRequestDTO;
import com.DesafioTec.CheckList.model.user.UserModel;
import com.DesafioTec.CheckList.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        UserModel user = userRepo.findByEmail(principal.getUsername()).orElseThrow();
        String token = jwtUtil.genereteToken(user.getUserId(), user.getEmail());
        AuthResponseDTO
                resp = new AuthResponseDTO(token);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        UserModel u = new UserModel();
        u.setNameUser(dto.getNameUser());
        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setRoles(1);
        userRepo.save(u);
        return ResponseEntity.status(201).build();
    }
}
