package ua.university.kma.BookShop.db;

import ua.university.kma.BookShop.dto.BookDto;
import ua.university.kma.BookShop.model.Book;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private List<Book> books;

    public DBManager(){
        books = new ArrayList<>();
        books.add(new Book("book1", "isbn1", "author1"));
        books.add(new Book("book2", "isbn2", "author2"));
        books.add(new Book("book3", "isbn3", "author3"));
    }

    public List<Book> getList() {
        return books;
    }

    public void addBook(BookDto bookDto){
        Book book = new Book(bookDto);

        if(!books.contains(book)){
            books.add(book);
        }
    }
}
