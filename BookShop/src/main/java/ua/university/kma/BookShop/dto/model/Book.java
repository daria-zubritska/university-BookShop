package ua.university.kma.BookShop.dto.model;

import ua.university.kma.BookShop.dto.BookDto;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;


    public Book(String title, String isbn, String author) {
        this.author = author;
        this.isbn = isbn;
        this.title = title;
    }

    public Book() {

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



    public Book(BookDto bookDto) {
        this.title = bookDto.getTitle();
        this.isbn = bookDto.getIsbn();
        this.author = bookDto.getAuthor();
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Book))return false;

        Book b = (Book)o;

        return (this.title.equals(b.title))&&(this.author.equals(b.author))&&(this.isbn.equals(b.isbn));
    }
}
