package ua.university.kma.BookShop.db;

import org.springframework.stereotype.Repository;
import ua.university.kma.BookShop.dto.BookDto;
import ua.university.kma.BookShop.dto.model.Book;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DBManager {

    private List<Book> books;

    public DBManager(){
        books = new ArrayList<>();

        books.add(new Book("title1", "isbn1", "author1"));
        books.add(new Book("title2", "isbn2", "author2"));
        books.add(new Book("title3", "isbn3", "author3"));
    }

    public List<Book> getList() {
        return books;
    }

    public void addBook(BookDto bookDto){
        Book book = new Book(bookDto);

        if(!books.contains(book))books.add(book);
    }
}
