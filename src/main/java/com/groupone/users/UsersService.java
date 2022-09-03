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

    public void createUser(String email, String password, List<Notes> notes){
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(password);
        users.setNotesList(notes);
        usersRepository.save(users);
    }

    public void updateUserById(UUID userUuid, String email, String password, List<Notes> notes){
        Users users = getById(userUuid);
        users.setEmail(email);
        users.setPassword(password);
        users.setNotesList(notes);
        usersRepository.save(users);
    }

    private Users getById(UUID userUuid) {
        return usersRepository.getReferenceById(userUuid);
    }

    public void delete(UUID userUuid){
        usersRepository.deleteById(userUuid);
    }
}
