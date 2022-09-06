package com.groupone.users;

import com.groupone.notes.Notes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public void createUser(String email, String password, List<Notes> notes) {
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(password);
        users.setNotesList(notes);
        usersRepository.save(users);
    }

    public Users getUserByUuid(UUID userUuid) {
        return usersRepository.getReferenceById(userUuid);
    }

    public Users getUserByName(String name) {
        return usersRepository.findByEmail(name);
    }

    public void updateUserByUuid(UUID userUuid, String email, String password, List<Notes> notes) {
        Users users = getUserByUuid(userUuid);
        users.setEmail(email);
        users.setPassword(password);
        users.setNotesList(notes);
        usersRepository.save(users);
    }

    public void deleteUserByUuid(UUID userUuid) {
        usersRepository.deleteById(userUuid);
    }
}