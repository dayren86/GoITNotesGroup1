package com.groupone.notes;

import com.groupone.users.Users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Notes {
    @Id
    @GeneratedValue
    private UUID id;

    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters inclusive")
    private String nameNotes;

    @NotEmpty(message = "Content cannot be empty")
    @Size(min = 5, message = "Content minimum size 10 characters")
    private String content;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @ManyToOne
    private Users users;
}
