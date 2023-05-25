package uz.softex.securitystore.workers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.security.JwtProvider;
import uz.softex.securitystore.workers.service.AuthService;
import uz.softex.securitystore.workers.dto.Login;
import uz.softex.securitystore.workers.entity.Workers;
import uz.softex.securitystore.workers.dto.WorkersDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final
     AuthService service;
    private final
     AuthenticationManager manager;
    private final
     JwtProvider provider;

    public AuthController(AuthService service, AuthenticationManager manager, JwtProvider provider) {
        this.service = service;
        this.manager = manager;
        this.provider = provider;
    }


    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody WorkersDto dto) {
        ApiResponse register = service.register(dto);
        return ResponseEntity.status(register.isSuccess() ? 200 : 409).body(register);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody Login login) {
        try {

            Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            Workers user = (Workers) authenticate.getPrincipal();
            String token = provider.generateToken(user.getUsername(),user.getPosition());
            ApiResponse apiResponse = new ApiResponse(token, true);
            System.out.println(user);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(409).body(new ApiResponse(" login yoki parol xato", false));
        }
    }
}
