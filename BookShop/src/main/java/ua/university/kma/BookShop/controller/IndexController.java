package ua.university.kma.BookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.university.kma.BookShop.Config.MyPasswordEncoder;
import ua.university.kma.BookShop.Service.BookService;
import ua.university.kma.BookShop.Service.UserService;
import ua.university.kma.BookShop.dto.BookDto;
import ua.university.kma.BookShop.dto.model.Book;
import ua.university.kma.BookShop.dto.model.User;
//import ua.university.kma.BookShop.dto.model.WishList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class IndexController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping({"/", ""})
    public String index() {
        return "index";
    }

    @RequestMapping({"/register", ""})
    public String register() {
        return "register";
    }

    @RequestMapping({"/book/{isbn}", ""})
    public String showInfoPage(@PathVariable(name = "isbn") String isbn, HttpSession session) {
        Book book = bookService.getBookByIsbn(isbn);
        if (book != null) {

            String liked = "no";

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();

                Set<Book> likedBooks = userService.getUserByUsername(currentUserName).getLikedBooks();

                if(likedBooks != null) {
                    liked = likedBooks.contains(book)? "yes" : "no";
                }
            }

            BookDto bookDto = new BookDto(book, liked);
            session.setAttribute("book", bookDto);
            return "book";
        }
        return "wrong_id_book";
    }

    @RequestMapping({"/profile", ""})
    public String getWishList(Model model, Authentication authentication) {

        User user = userService.getUserByUsername(authentication.getName());

        Set<Book> likedBooks = user.getLikedBooks();

        if (likedBooks == null)
            likedBooks = new HashSet<>();

        model.addAttribute("books", likedBooks);

        return "profile";
    }

    @RequestMapping(value = "/registerForm", method = RequestMethod.POST)
    public String form(@RequestParam(name = "username") String username,
                       @RequestParam(name = "psw") String psw,
                       @RequestParam(name = "psw-repeat") String pswRepeat, Model model, HttpServletRequest request) {

        System.out.println(username + " " + psw + " " + pswRepeat);

        if (psw.compareTo(pswRepeat) != 0) {
            model.addAttribute("problem", "Password error");
            return "forward:/register";
        }

        if (userService.getUserByUsername(username) != null) {
            model.addAttribute("problem", "This user alresdy exists");
            return "forward:/register";
        }

        userService.createUser(username, myPasswordEncoder.encode(psw));

        return "redirect:/";
    }

    @PostMapping("/add-to-wish")
    public String addToWishList(Authentication authentication, HttpSession session) {
        if (authentication == null)
            return "redirect:/";

        BookDto book = (BookDto) session.getAttribute("book");

        String bookIsbn = book.getIsbn();

        User byLogin = userService.getUserByUsername(authentication.getName());
        Set<Book> likedBooks = byLogin.getLikedBooks();

        if(likedBooks == null) likedBooks = new HashSet<>();

        likedBooks.add(bookService.getBookByIsbn(bookIsbn));

        userService.save(byLogin);

        return "redirect:/profile";
    }

    @PostMapping("/delete-from-wish")
    public String deleteFromWishList(Authentication authentication, HttpSession session) {
        if (authentication == null)
            return "redirect:/";

        BookDto book = (BookDto) session.getAttribute("book");

        String bookIsbn = book.getIsbn();

        User byLogin = userService.getUserByUsername(authentication.getName());
        Set<Book> likedBooks = byLogin.getLikedBooks();

        if(likedBooks == null) likedBooks = new HashSet<>();

        likedBooks.remove(bookService.getBookByIsbn(bookIsbn));

        userService.save(byLogin);

        return "redirect:/profile";
    }

}
