package ua.university.kma.BookShop.model;

import ua.university.kma.BookShop.dto.BookDto;

public class Book {
    public Book(String title, String isbn, String author) {
        this.author = author;
        this.isbn = isbn;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String title;
    private String isbn;
    private String author;

    public Book(BookDto bookDto) {
        this.title = bookDto.getTitle();
        this.isbn = bookDto.getIsbn();
        this.author = bookDto.getAuthor();
    }
}
