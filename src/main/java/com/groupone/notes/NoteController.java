package com.groupone.notes;

import com.groupone.users.Users;
import com.groupone.users.UsersService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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

        ModelAndView modelAndView = new ModelAndView("note-list");
        modelAndView.addObject("count", user.getNotesList().size());
        modelAndView.addObject("listOfNotes", user.getNotesList());

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createNote() {
        ModelAndView modelAndView = new ModelAndView("note-create");
        return modelAndView;
    }

    @PostMapping("/save")
    public String saveNote(@RequestParam(name = "access") String access,
                           @RequestParam(name = "setNameNotes") String title,
                           @RequestParam(name = "setContent") String content,
                           Model model,
                           HttpServletRequest request) {
        if (title.length() < 5 || title.length() > 100 ) {
            model.addAttribute("error", 0);
            model.addAttribute("content", content);
            return "note-create";
        }

        String email = request.getUserPrincipal().getName();
        service.createNote(title, content, Visibility.valueOf(access), email);

        return "redirect:/note/list";
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
    public String updateNote(@PathVariable("id") UUID uuid,
                                   @RequestParam(name = "access") String access,
                                   @RequestParam(name = "setNameNotes") String title,
                                   @RequestParam(name = "setContent") String content,
                                   Model model) {
        if (title.length() < 5 || title.length() > 100 ) {
            model.addAttribute("error", 0);
            model.addAttribute("content", content);
            model.addAttribute("access", access);
            model.addAttribute("nameNotes", "");
            return "note-edit";
        }
        service.updateNote(uuid, title, content, Visibility.valueOf(access));

        return"redirect:/note/list";
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
