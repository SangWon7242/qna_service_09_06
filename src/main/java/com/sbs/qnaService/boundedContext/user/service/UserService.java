package com.sbs.qnaService.boundedContext.user.service;

import com.sbs.qnaService.boundedContext.user.entity.SiteUser;
import com.sbs.qnaService.boundedContext.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SiteUser create(String username, String email, String password) {
    SiteUser user = new SiteUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
    return user;
  }
}
