package com.groupone.controllers;


import com.groupone.notes.Notes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@AllArgsConstructor
public class NoteController {

    private UsersService service;

    @GetMapping("/note/list")
    public ModelAndView mainUserPage(){
        ModelAndView modelAndView = new ModelAndView("note-list");
        modelAndView.addObject("listSize", service.listAll().size());
        modelAndView.addObject("listOfNotes", service.listAll());
        return modelAndView;
    }

    @PostMapping("note/create")
    public ModelAndView createNote(){
        ModelAndView modelAndView = new ModelAndView("note-create");
        modelAndView.addObject("note", new Notes());
        return modelAndView;
    }

    @PostMapping("note/edit")
    public ModelAndView editNote(@RequestParam(name = "noteId") Long id){
        Notes note = service.get(id);
        ModelAndView modelAndView = new ModelAndView("note-edit");
        modelAndView.addObject("note", note);
        modelAndView.addObject("variables", note.getVisibility().name());
        return modelAndView;
    }

    @GetMapping("/note/share/{id}")
    public ModelAndView shareNote(@PathVariable("id") Long id){
        Notes note = service.get(id);
        ModelAndView modelAndView = new ModelAndView("noteById");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

}
