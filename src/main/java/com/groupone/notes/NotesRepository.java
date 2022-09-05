package com.groupone.notes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotesRepository extends JpaRepository<Notes, UUID> {
}
