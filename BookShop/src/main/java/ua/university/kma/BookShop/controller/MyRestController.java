package ua.university.kma.BookShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.university.kma.BookShop.Config.MyPasswordEncoder;
import ua.university.kma.BookShop.Service.BookService;
import ua.university.kma.BookShop.Service.UserService;
import ua.university.kma.BookShop.db.UserRepository;
import ua.university.kma.BookShop.db.WishListRepository;
import ua.university.kma.BookShop.dto.AddResponseDto;
import ua.university.kma.BookShop.dto.BookDto;
import ua.university.kma.BookShop.dto.FilterResponseDto;
import ua.university.kma.BookShop.dto.model.Book;
import ua.university.kma.BookShop.dto.model.User;
import ua.university.kma.BookShop.dto.model.WishList;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyRestController {

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

    @RequestMapping(value = "/add-book", method = RequestMethod.POST)
    public ResponseEntity<AddResponseDto> add(@RequestBody final BookDto bookDto) {

        if (bookDto.getIsbn() == "" || bookDto.getTitle() == "" || bookDto.getAuthor() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                    .body(new AddResponseDto("Failure"));
        }

        if (bookService.addBook(bookDto.getIsbn(), bookDto.getAuthor(), bookDto.getTitle()) != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                    .body(new AddResponseDto("Success"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                    .body(new AddResponseDto("Failure"));
        }
    }

    @RequestMapping(value = "/show-books")
    public List<Book> show() {
        return bookService.findAllBooks();
    }

    @RequestMapping(value = "/filter-book", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> filter(@RequestBody final FilterResponseDto search) {

        List<Book> allBooks = bookService.findAllBooks();

        if (!search.getSearch().equals("")) {
            List<Book> resp = bookService.filterBooks(search.getSearch());

            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                    .body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                    .body(allBooks);
        }
    }

    @GetMapping("/wishlist")
    public String addToWishList(@RequestParam(name = "isbn") String bookIsbn, Authentication authentication) {
        if (authentication == null)
            return "No authenticated";

        User byLogin = userService.getUserByUsername(authentication.getName());
        WishList wishList = byLogin.getWishList();

        if (wishList == null)
            wishList = new WishList();

        wishList.getBooks().add(bookService.getBookByIsbn(bookIsbn));
        wishList.setUser(byLogin);
        wishListRepository.save(wishList);
        return "Action approved for " + authentication.getAuthorities();
    }

}
