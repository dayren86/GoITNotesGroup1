package com.groupone.notes;

import com.groupone.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface NotesRepository extends JpaRepository<Notes, UUID> {
}
