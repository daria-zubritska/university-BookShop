package ua.university.kma.BookShop.db;

import org.springframework.stereotype.Service;
import ua.university.kma.BookShop.dto.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("bookService")
public class BookService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Book addBook(String isbn, String author, String title){
        Book book = new Book();
        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setTitle(title);
        return entityManager.merge(book);
    }

    @Transactional
    public Book getBookByIsbn(String isbn){
        return entityManager.find(Book.class, isbn);
    }

    @Transactional
    public List<Book> findAllBooks(){
        return entityManager.createQuery("FROM Book", Book.class).getResultList();
    }

    @Transactional
    public List<Book> filterBooks(String req){
        List<Book> allBooks = entityManager.createQuery("FROM Book", Book.class).getResultList();

            List<Book> resp = new ArrayList<>();
            for (Book b : allBooks) {
                if (b.getAuthor().contains(req) || b.getTitle().contains(req) || b.getIsbn().contains(req))
                    resp.add(b);
            }

            return resp;
    }
}
