package ua.university.kma.BookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.university.kma.BookShop.db.BookService;
import ua.university.kma.BookShop.dto.model.Book;

@Controller
public class IndexController {

    @Autowired
    private BookService bookService;

    @RequestMapping({"/", ""})
    public String index() {
        return "index";
    }

    @RequestMapping({"/book/{isbn}",""})
    public String showInfoPage(@PathVariable(name = "isbn") String isbn, Model model){
        Book book = bookService.getBookByIsbn(isbn);
        if (book != null) {
            model.addAttribute("book", book);
            return "book";
        }
        return "wrong_id_book";
    }

}

