package com.mittings.repository;

import com.mittings.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  @Query("select t from #{#entityName} t where t.name = ?1")
  Role findByName(String name);
}
