package com.groupone.notes;

import com.groupone.users.Users;
import com.groupone.users.UsersService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private final NotesService service;
    private final UsersService usersService;

    @GetMapping("/list")
    public ModelAndView mainUserPage(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Users user = usersService.findByEmail(email);
        List<Notes> notesList = user.getNotesList();

        ModelAndView modelAndView = new ModelAndView("note-list");
        modelAndView.addObject("count", notesList.size());
        modelAndView.addObject("listOfNotes", notesList);

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createNote(@ModelAttribute("notes") Notes notes) {
        return new ModelAndView("note-create");
    }

    @PostMapping("/save")
    public String saveNote(@Valid Notes notes,
                           BindingResult bindingResult,
                           @RequestParam(name = "access") String access,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "note-create";
        }
        System.out.println(notes.getContent());

        String email = request.getUserPrincipal().getName();
        service.createNote(notes.getNameNotes(), notes.getContent(), Visibility.valueOf(access), email);

        return "redirect:/note/list";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editNote(@PathVariable("id") UUID uuid,
                                 HttpServletRequest request) {
        Notes note = service.getNoteByUuid(uuid);

        if (note.getUsers().getEmail().equals(request.getUserPrincipal().getName())) {

            ModelAndView modelAndView = new ModelAndView("note-edit");
            modelAndView.addObject("notes", note);

            return modelAndView;
        } else {
            return new ModelAndView("note-share-error");
        }
    }

    @PostMapping("/edit/{id}/save")
    public String updateNote(@PathVariable("id") UUID uuid,
                             @Valid Notes notes,
                             BindingResult bindingResult,
//                             @RequestParam(name = "access") String access,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("content", notes.getContent());
            model.addAttribute("access", notes.getVisibility());
            model.addAttribute("nameNotes", notes.getNameNotes());
            return "note-edit";
        }

        service.updateNote(uuid, notes.getNameNotes(), notes.getContent(), notes.getVisibility());

        return "redirect:/note/list";
    }


    @GetMapping("/share/{id}")
    public ModelAndView shareNote(@PathVariable("id") UUID uuid,
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
            } else {
                return new ModelAndView("note-share-error");
            }
        } catch (EntityNotFoundException ex) {
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
