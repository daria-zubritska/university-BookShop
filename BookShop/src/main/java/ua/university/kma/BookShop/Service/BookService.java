package ua.university.kma.BookShop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.university.kma.BookShop.db.BookRepository;
import ua.university.kma.BookShop.dto.model.Book;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("bookService")
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Book addBook(String isbn, String author, String title){
        Book book = new Book();
        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setTitle(title);
        return bookRepository.save(book);
    }

    @Transactional
    public Book getBookByIsbn(String isbn){
        return bookRepository.findBookByIsbn(isbn);
    }

    @Transactional
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    @Transactional
    public List<Book> filterBooks(String req){
        List<Book> allBooks = bookRepository.findAll();

            List<Book> resp = new ArrayList<>();
            for (Book b : allBooks) {
                if (b.getAuthor().contains(req) || b.getTitle().contains(req) || b.getIsbn().contains(req))
                    resp.add(b);
            }

            return resp;
    }
}
