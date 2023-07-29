package io.pashazz.pomodore.repositories;

import io.pashazz.pomodore.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
