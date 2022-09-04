package com.groupone.controllers;


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

//    private UsersService service;
    public  Notes test() {
        Notes notes = new Notes();
        notes.setNameNotes("Test");
        notes.setContent("blablabla");
        notes.setId(UUID.randomUUID());
        notes.setVisibility(Visibility.PRIVATE);
        return notes;
    }



    @GetMapping("/list")
    public ModelAndView mainUserPage(){


        ModelAndView modelAndView = new ModelAndView("note-list");
//        modelAndView.addObject("listSize", service.listAll().size());
//        modelAndView.addObject("listOfNotes", service.listAll());
        modelAndView.addObject("count", 1);

        modelAndView.addObject("listOfNotes", Collections.singletonList(test()));

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createNote(){
        ModelAndView modelAndView = new ModelAndView("note-create");
//        modelAndView.addObject("note", new Notes());
        return modelAndView;
    }

    @PostMapping("/save")
    public void saveUser(@RequestParam(name = "setNameNotes") String title,
                         @RequestParam(name = "setContent") String content,
                         HttpServletResponse response) {
        System.out.println("title = " + title);
        System.out.println("content = " + content);
//        System.out.println("visibility = " + visibility);
//        service.save(user);
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editNote(@PathVariable("id") UUID uuid){
//        Notes note = service.get(uuid);


        ModelAndView modelAndView = new ModelAndView("note-edit");
        modelAndView.addObject("nameNotes", test().getNameNotes());
        modelAndView.addObject("content", test().getContent());
        modelAndView.addObject("variables", test().getVisibility().name());
//        modelAndView.addObject("note", note);
//        modelAndView.addObject("variables", note.getVisibility().name());
        return modelAndView;
    }

    @GetMapping("/share/{id}")
    public ModelAndView shareNote(@PathVariable("id") UUID uuid){
//        Notes note = service.get(id);
        ModelAndView modelAndView = new ModelAndView("noteById");
//        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public void deleteNote(@PathVariable("id") UUID uuid, HttpServletResponse response){
//        servie

        try {
            response.sendRedirect("/note/list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
