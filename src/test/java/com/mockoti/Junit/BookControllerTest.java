package com.mockoti.Junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockoti.Junit.Controller.BookController;
import com.mockoti.Junit.Enitity.Book;
import com.mockoti.Junit.Repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class BookControllerTest {

    private MockMvc mockMvc;

    @Mock // Creates mock objects for the fields marked with this annotation
    private BookRepository bookRepository;

    @InjectMocks // This annotation tells Mockito to inject the mock objects
    private BookController bookController;

    Book book1 = new Book(1L, "The Great Gatsby", 5, "A classic novel set in the Roaring Twenties.");
    Book book2 = new Book(2L, "To Kill a Mockingbird", 4, "A story of racial injustice and childhood innocence.");

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this); // ensures that Mockito processes the @Mock and @InjectMocks annotations to initialize and inject mock dependencies before running the tests.
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build(); //This sets up the MockMvc to work in a standalone mode, meaning it will only set up the BookController (without the full Spring application context) for testing
    }

    @Test
    public void getAllRecords_success() throws Exception {
        List<Book> records = new ArrayList<>(Arrays.asList(book1, book2));

        // Mocking the repository method to return the predefined list of books
        Mockito.when(bookRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/get")  // Assuming this is the correct endpoint for retrieving all books
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Check for HTTP status 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))  // Expect 2 records in the response
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("The Great Gatsby")));  // Check the name of the first book
    }

    @Test
    public void getBookById() throws Exception {
        // Mock the behavior of the bookRepository
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("The Great Gatsby")));
    }

    @Test
    public void createBook() throws Exception{
        Book book = Book.builder()
                .bookId(4l)
                .name("Harry potter")
                .summary("Horror")
                .rating(5)
                .build();

        Mockito.when(bookRepository.save(book)).thenReturn(book);

        String content = new ObjectMapper().writeValueAsString(book);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Harry potter")));
    }

    @Test
    public void updateBookRecord() throws Exception{
        Book updateBook = Book.builder()
                .bookId(1L)
                .name("updated book name")
                .summary("updated book summary")
                .rating(3)
                .build();

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book1));
        Mockito.when(bookRepository.save(updateBook)).thenReturn(updateBook);

        String content = new ObjectMapper().writeValueAsString(updateBook);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("updated book name")));
    }

        @Test
        public void delete() throws Exception {

//            Mockito.when(bookRepository.findById(book1.getBookId())).thenReturn(Optional.ofNullable(book1));

            Mockito.when(bookRepository.findById(book1.getBookId())).thenReturn(Optional.empty());
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/book/delete/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(mockRequest)
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Book not found to delete"));
        }
    }