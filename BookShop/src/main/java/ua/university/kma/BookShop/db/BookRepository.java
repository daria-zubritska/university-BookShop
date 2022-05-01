package ua.university.kma.BookShop.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.university.kma.BookShop.dto.model.Book;

import java.util.List;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findAll();

    Book findBookByIsbn(String isbn);

}
