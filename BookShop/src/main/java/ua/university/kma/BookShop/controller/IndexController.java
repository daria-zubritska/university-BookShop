package ua.university.kma.BookShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.university.kma.BookShop.db.DBManager;

@Controller
public class IndexController {

    DBManager dbm = new DBManager();

    @RequestMapping({"/", ""})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/books-list", method = RequestMethod.GET)
    public String booksList() {

        return "index";
    }

    @RequestMapping(value = "/add-book", method = RequestMethod.POST)
    public String addNewBook(Model model) {

        return "redirect:/books-list";
    }


}

