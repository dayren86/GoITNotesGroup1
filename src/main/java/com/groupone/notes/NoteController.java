package com.groupone.notes;


import com.groupone.notes.Notes;
import com.groupone.notes.Visibility;
import com.groupone.users.Users;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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

    private NotesService service;


//    public Notes test() {
//        Notes notes = new Notes();
//        notes.setNameNotes("Test");
//        notes.setContent("blablabla");
//        notes.setId(UUID.randomUUID());
//        notes.setVisibility(Visibility.PRIVATE);
//        return notes;
//    }


    @GetMapping("/list")
    public ModelAndView mainUserPage() {


        ModelAndView modelAndView = new ModelAndView("note-list");
        modelAndView.addObject("count", service.getAllNotes().size());
        modelAndView.addObject("listOfNotes", service.getAllNotes());

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
                         HttpServletResponse response) {

        service.createNote(title, content, Visibility.valueOf(access));
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
    public ModelAndView shareNote(@PathVariable("id") UUID uuid) {
        try {
            Notes note = service.getNoteByUuid(uuid);
            ModelAndView modelAndView = new ModelAndView("note-share");
            modelAndView.addObject("getNameNotes", note.getNameNotes());
            modelAndView.addObject("getContent", note.getContent());
            modelAndView.addObject("getId", note.getId());
            return modelAndView;
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
