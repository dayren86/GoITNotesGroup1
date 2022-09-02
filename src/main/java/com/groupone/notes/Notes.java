package com.groupone.notes;

import com.groupone.users.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Notes {
    @Id
    @GeneratedValue
    private UUID id;

    private String nameNotes;

    private String content;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @ManyToOne
    private Users users;
}
