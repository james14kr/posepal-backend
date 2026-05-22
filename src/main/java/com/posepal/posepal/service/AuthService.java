package com.posepal.posepal.service;

import com.posepal.posepal.dto.AuthResponse;
import com.posepal.posepal.dto.LoginRequest;
import com.posepal.posepal.dto.SignupRequest;
import com.posepal.posepal.entity.User;
import com.posepal.posepal.repository.UserRepository;
import com.posepal.posepal.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthResponse signup(SignupRequest request){
    if(userRepository.existsByEmail(request.getEmail())){
      throw new RuntimeException("이미 사용중인 이메일입니다.");
    }
    User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .nickname(request.getNickname())
            .build();
    userRepository.save(user);
    String token = jwtUtil.generateToken(user.getEmail());
    return new AuthResponse(token, user.getNickname());
  }

  public AuthResponse login(LoginRequest request){
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
    if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
    String token = jwtUtil.generateToken(user.getEmail());
    return new AuthResponse(token, user.getNickname());
  }

}
