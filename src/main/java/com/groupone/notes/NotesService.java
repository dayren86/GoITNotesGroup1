package com.groupone.notes;

import com.groupone.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotesService {
    private final NotesRepository notesRepository;
    private final UsersService usersService;

    public void createNote(String nameNotes, String content, Visibility access, String email) {
        Notes notes = new Notes();
        notes.setNameNotes(nameNotes);
        notes.setContent(content);
        notes.setVisibility(access);
        notes.setUsers(usersService.findByEmail(email));
        notesRepository.save(notes);
    }


    public Notes getNoteByUuid(UUID uuid) {
        return notesRepository.getReferenceById(uuid);
    }

    public void updateNote(UUID uuid, String nameNotes, String content, Visibility visibility) {
        Notes notes = getNoteByUuid(uuid);
        notes.setNameNotes(nameNotes);
        notes.setContent(content);
        notes.setVisibility(visibility);
        notesRepository.save(notes);
    }

    public void deleteNoteByUuid(UUID uuid) {
        notesRepository.deleteById(uuid);
    }
}
