package com.example.spring.controllers;

import com.example.spring.test.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class NoteController {

    private AppUserService service;

    @GetMapping("/note/list")
    public String mainUserPage(Model model){
        model.addAttribute("listSize", service.listAll().size());
        model.addAttribute("listOfNotes", service.listAll());
        return "note-list";
    }

    @PostMapping("note/create")
    public String createNote(Model model){
        model.addAttribute("note", new Note());
        return "note-create";
    }

    @PostMapping("note/edit")
    public String editNote(Model model, HttpServletRequest req){
        Note note = service.get(req.getParameter("noteId"));
        model.addAttribute("note", note);
        return "note-create";
    }

    @GetMapping("/note/share/{id}")
    public String shareNote(@PathVariable("id") Long id, Model model){
        Note note = service.get(id);
        model.addAttribute("note", note);
        return "noteById";
    }

}
