package com.groupone.notes;


import com.groupone.notes.Notes;
import com.groupone.notes.Visibility;
import com.groupone.users.Users;
import com.groupone.users.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;


@Controller
@AllArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NotesService service;
    private final UsersService uservice;


    @GetMapping("/list")
    public ModelAndView mainUserPage(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Users byEmail = uservice.findByEmail(email);

        ModelAndView modelAndView = new ModelAndView("note-list");
        modelAndView.addObject("count", byEmail.getNotesList().size());
        modelAndView.addObject("listOfNotes", byEmail.getNotesList());

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createNote() {
        ModelAndView modelAndView = new ModelAndView("note-create");
        modelAndView.addObject("note", new Notes());
        return modelAndView;
    }

    @PostMapping("/save")
    public void saveNote(@RequestParam(name = "access") String access,
                         @RequestParam(name = "setNameNotes") String title,
                         @RequestParam(name = "setContent") String content,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        String email = request.getUserPrincipal().getName();
        service.createNote(title, content, Visibility.valueOf(access), email);

        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editNote(@PathVariable("id") UUID uuid) {
        Notes note = service.getNoteByUuid(uuid);

        ModelAndView modelAndView = new ModelAndView("note-edit");
        modelAndView.addObject("id", note.getId());
        modelAndView.addObject("nameNotes", note.getNameNotes());
        modelAndView.addObject("content", note.getContent());
        modelAndView.addObject("access", note.getVisibility().name());

        return modelAndView;
    }

    @PostMapping("/edit/{id}/save")
    public void updateNote2(@PathVariable("id") UUID uuid,
                            @RequestParam(name = "access") String access,
                            @RequestParam(name = "setNameNotes") String title,
                            @RequestParam(name = "setContent") String content,
                            HttpServletResponse response) {
        service.updateNote(uuid, title, content, Visibility.valueOf(access));
        try {
            response.sendRedirect("/note/list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/share/{id}")
    public ModelAndView shareNote(@PathVariable("id") UUID uuid,
                                  HttpServletResponse response,
                                  HttpServletRequest request) {
        try {
            Notes note = service.getNoteByUuid(uuid);
            if (note.getVisibility().equals(Visibility.PUBLIC) ||
                    note.getUsers().getEmail().equals(request.getUserPrincipal().getName())) {

                ModelAndView modelAndView = new ModelAndView("note-share");
                modelAndView.addObject("getNameNotes", note.getNameNotes());
                modelAndView.addObject("getContent", note.getContent());
                modelAndView.addObject("getId", note.getId());
                return modelAndView;
            }else {
                return new ModelAndView("note-share-error");
            }

        } catch (Exception ex) {
            return new ModelAndView("note-share-error");
        }
    }

    @PostMapping("/delete/{id}")
    public void deleteNote(@PathVariable("id") UUID uuid, HttpServletResponse response) {
        service.deleteNoteByUuid(uuid);
        try {
            response.sendRedirect("/note/list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
