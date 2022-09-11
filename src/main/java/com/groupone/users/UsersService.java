package com.groupone.users;

import com.groupone.notes.Notes;
import com.groupone.security.CryptConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final CryptConfig cryptConfig;

    public void createUser(String email, String password) {
        String encodedPassword = cryptConfig.passwordEncoder().encode(password);

        Users users = new Users();
        users.setEmail(email);
        users.setPassword(encodedPassword);

        usersRepository.save(users);
    }

    public Users getUserByUuid(UUID userUuid) {
        return usersRepository.getReferenceById(userUuid);
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

    public Users findByEmail(String email){
        return usersRepository.findByEmail(email);
    }
}