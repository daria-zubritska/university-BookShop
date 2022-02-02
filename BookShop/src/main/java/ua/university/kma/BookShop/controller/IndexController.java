package ua.university.kma.BookShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.university.kma.BookShop.db.DBManager;
import ua.university.kma.BookShop.dto.BookDto;

@Controller
public class IndexController {

    DBManager dbm = new DBManager();

    @RequestMapping({"/", ""})
    public String index() {
        return "redirect:/add-book";
    }

    @RequestMapping(value = "/books-list", method = RequestMethod.GET)
    public String booksList(Model model) {
        model.addAttribute("books", dbm.getList());
        return "index";
    }

    @RequestMapping(value = "/books-list", method = RequestMethod.POST)
    public String redirectToAdd() {
        return "redirect:/add-book";
    }

    @RequestMapping(value = "/add-book", method = RequestMethod.POST)
    public String addNewBook(BookDto bookDto) {
        dbm.addBook(bookDto);
        return "redirect:/books-list";
    }

    @RequestMapping(value = "/add-book", method = RequestMethod.GET)
    public String showForm() {
        return "add-form";
    }



}

