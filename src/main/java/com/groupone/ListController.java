package com.groupone;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

    @Controller
    public class ListController {
        @RequestMapping(method = RequestMethod.GET, value = "/note-list")
        public ModelAndView getNotesCount(){
            ModelAndView result = new ModelAndView("note-list");
            result.addObject("count", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return result;
        }
    }


