package com.sbs.qnaService.boundedContext.user.service;

import com.sbs.qnaService.boundedContext.user.entity.SiteUser;
import com.sbs.qnaService.boundedContext.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public SiteUser create(String username, String email, String password) {
    SiteUser user = new SiteUser();
    user.setUsername(username);
    user.setEmail(email);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
    return user;
  }
}
