package ua.university.kma.BookShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.university.kma.BookShop.dto.AddResponseDto;
import ua.university.kma.BookShop.dto.BookDto;
import ua.university.kma.BookShop.db.DBManager;
import ua.university.kma.BookShop.dto.FilterResponseDto;
import ua.university.kma.BookShop.dto.model.Book;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyRestController {

    private DBManager dbm = new DBManager();

    @RequestMapping(value = "/add-book", method = RequestMethod.POST)
    public ResponseEntity<AddResponseDto> add(@RequestBody final BookDto bookDto){

        if(bookDto.getIsbn() == "" || bookDto.getTitle() == "" || bookDto.getAuthor() == ""){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                    .body(new AddResponseDto("Failure"));
        }

        dbm.addBook(bookDto);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                .body(new AddResponseDto("Success"));
    }

    @RequestMapping(value = "/show-books")
    public List<Book> show(){
        return dbm.getList();
    }

    @RequestMapping(value = "/filter-book", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> filter(@RequestBody final FilterResponseDto search) {

        List<Book> allBooks = dbm.getList();

        if (!search.getSearch().equals("")) {
            List<Book> resp = new ArrayList<>();
            for (Book b : allBooks) {
                if (b.getTitle().contains(search.getSearch()) || b.getIsbn().contains(search.getSearch()))
                    resp.add(b);
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                    .body(resp);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.AUTHORIZATION, "generated-jwt-token")
                    .body(allBooks);
        }
    }



}
