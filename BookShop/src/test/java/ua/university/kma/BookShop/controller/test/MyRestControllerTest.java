package ua.university.kma.BookShop.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.university.kma.BookShop.controller.MyRestController;
import ua.university.kma.BookShop.db.DBManager;
import ua.university.kma.BookShop.dto.AddResponseDto;
import ua.university.kma.BookShop.dto.BookDto;
import ua.university.kma.BookShop.dto.FilterResponseDto;
import ua.university.kma.BookShop.dto.model.Book;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(MyRestController.class)
public class MyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private DBManager dbm;

    @Test
    @SneakyThrows
    void testAdd() throws Exception {
        final String jsonReq = objectMapper.writeValueAsString(new BookDto("titleTest", "isbnTest", "authorTest"));
        final String expectedResp = objectMapper.writeValueAsString(new AddResponseDto("Success"));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResp));

    }

    @Test
    @SneakyThrows
    void testAddFail() throws Exception {
        final String jsonReq = objectMapper.writeValueAsString(new BookDto("", "isbnTest", ""));
        final String expectedResp = objectMapper.writeValueAsString(new AddResponseDto("Failure"));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/add-book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonReq)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(expectedResp));

    }

    @Test
    @SneakyThrows
    void testFilterBooksEmpty() throws Exception {
        final String jsonReq = objectMapper.writeValueAsString(new FilterResponseDto(""));
        final String expectedResp = objectMapper.writeValueAsString(dbm.getList());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/filter-book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonReq)
                )
                .andExpect(MockMvcResultMatchers.content().json(expectedResp));

    }

    @Test
    @SneakyThrows
    void testFilterBooks() throws Exception {
        FilterResponseDto req = new FilterResponseDto("1");

        final String jsonReq = objectMapper.writeValueAsString(req);

        List<Book> allBooks = dbm.getList();
        List<Book> resp = new ArrayList<>();

        for (Book b : allBooks) {
            if (b.getTitle().contains(req.getSearch()) || b.getIsbn().contains(req.getSearch()))
                resp.add(b);
        }

        final String expectedResp = objectMapper.writeValueAsString(resp);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/filter-book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonReq)
                )
                .andExpect(MockMvcResultMatchers.content().json(expectedResp));

    }

}
