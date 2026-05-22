package com.posepal.posepal.controller;

import com.posepal.posepal.dto.AuthResponse;
import com.posepal.posepal.dto.LoginRequest;
import com.posepal.posepal.dto.SignupRequest;
import com.posepal.posepal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request){
    return ResponseEntity.ok(authService.signup(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
    return ResponseEntity.ok(authService.login(request));
  }

}
