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
import ua.university.kma.BookShop.db.WishListRepository;
import ua.university.kma.BookShop.dto.BookDto;
import ua.university.kma.BookShop.dto.model.Book;
import ua.university.kma.BookShop.dto.model.User;
import ua.university.kma.BookShop.dto.model.WishList;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping({"/", ""})
    public String index() {
        return "index";
    }

    @RequestMapping({"/register", ""})
    public String register(){
        return "register";
    }

    @RequestMapping({"/book/{isbn}", ""})
    public String showInfoPage(@PathVariable(name = "isbn") String isbn, Model model) {
        Book book = bookService.getBookByIsbn(isbn);
        if (book != null) {

            boolean liked = false;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();

                List<Book> likedList = userService.getUserByUsername(currentUserName).getWishList().getBooks();

                liked = likedList.contains(book);
            }

            BookDto bookDto = new BookDto(book, true);
            model.addAttribute("book", bookDto);
            return "book";
        }
        return "wrong_id_book";
    }

    @RequestMapping ({"/profile", ""})
    public String getWishList(Model model, Authentication authentication){

        User user = userService.getUserByUsername(authentication.getName());

        WishList wishList = user.getWishList();

        if(wishList == null)
            model.addAttribute("books", new ArrayList<Book>());
        else model.addAttribute("books", wishList.getBooks());

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

//    @RequestMapping(value = "/add-to-wish", method = RequestMethod.POST)
//    public String addToWishlist(Model model){
//        BookDto bookToLike = (BookDto) model.getAttribute("book");
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            String currentUserName = authentication.getName();
//
//            User user = userService.getUserByUsername(currentUserName);
//
//            wishListRepository.save();
//        }
//    }

}
