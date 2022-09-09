package com.groupone.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {
    @Query("SELECT u FROM Users u WHERE u.email = ?1")
    Users findByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.id = ?1")
    Users findById(String id);
}
