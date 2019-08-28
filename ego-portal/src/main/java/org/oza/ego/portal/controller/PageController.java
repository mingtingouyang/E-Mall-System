package org.oza.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    //
    @RequestMapping("/{page}")
    public String showIndex(@PathVariable("page") String page) {
        return page;
    }
}
