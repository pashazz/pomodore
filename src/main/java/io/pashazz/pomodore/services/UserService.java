package io.pashazz.pomodore.services;

import io.pashazz.pomodore.entities.User;
import io.pashazz.pomodore.repositories.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repo;

  @Transactional
  public User getOrDefault(long userId)  {
    return repo.findById(userId)
      .orElse(createDefault(userId));
  }

  protected User createDefault(long userId) {
    User user = new User();
    user.setUserId(userId);
    user.setWorkPhaseLengthSec(10);
    user.setRelaxPhaseLengthSec(5);
    repo.save(user);
    return user;
  }

  @Transactional
  @NotNull
  public User getUser(long userId) {
    User user = repo.findById(userId).orElseThrow(() -> new RuntimeException("Cannot find user by userId: " + userId));
    return user;
  }

}
