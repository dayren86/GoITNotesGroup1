package com.groupone.users;

import com.groupone.notes.Notes;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private List<Notes> notesList;
}