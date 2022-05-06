package ua.university.kma.BookShop.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.university.kma.BookShop.Service.BookService;
import ua.university.kma.BookShop.dto.model.Book;

import java.util.List;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void shouldSaveAndGetAllBooks(){

        bookService.addBook("1", "auth1", "title1");
        bookService.addBook("2", "auth2", "title2");
        bookService.addBook("3", "auth3", "title3");
        bookService.addBook("4", "auth2", "title1");

        List<Book> books = bookService.findAllBooks();

        for (Book b: books) {
            System.out.println(b.getAuthor() + " " + b.getTitle() + " " + b.getIsbn() + "\n");
        }

    }

    @Test
    void shouldSaveAndGetFilteredBooks(){

        bookService.addBook("1", "auth1", "title1");
        bookService.addBook("2", "auth2", "title2");
        bookService.addBook("3", "auth3", "title3");
        bookService.addBook("4", "auth2", "title1");

        List<Book> books = bookService.filterBooks("2");

        for (Book b: books) {
            System.out.println(b.getAuthor() + " " + b.getTitle() + " " + b.getIsbn() + "\n");
        }

    }

}
