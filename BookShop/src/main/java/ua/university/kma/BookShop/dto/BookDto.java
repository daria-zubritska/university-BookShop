package ua.university.kma.BookShop.dto;

import ua.university.kma.BookShop.dto.model.Book;

public class BookDto {
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
    private boolean liked = false;

    public BookDto(String title, String isbn, String author){
        this.author = author;
        this.isbn = isbn;
        this.title = title;
    }

    public BookDto(Book book, boolean liked){
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.liked = liked;
    }

}
