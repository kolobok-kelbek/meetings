package com.meetings.repository;

import com.meetings.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  @Query("select t from #{#entityName} t where t.username = ?1")
  Optional<User> findByUsername(final String username);
}
