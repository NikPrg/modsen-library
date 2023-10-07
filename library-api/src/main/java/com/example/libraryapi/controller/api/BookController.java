package com.example.libraryapi.controller.api;

import com.example.libraryapi.dto.BookInfoRequest;
import com.example.libraryapi.dto.PageRequestInfo;
import com.example.libraryapi.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapi.utils.HttpUtils.PUBLIC_API_V1;

@Controller
@RequestMapping(PUBLIC_API_V1)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public String findAll(
            @RequestParam(name = "page", required = false) @Positive Integer page,
            @RequestParam(name = "amount", required = false) @Positive Integer amount,
            Model model
    )
    {
        model.addAttribute("books", bookService.findAll(new PageRequestInfo(page, amount)));
        return "books/index";
    }

    @GetMapping("/books/{externalId}")
    public String findByExternalId(@PathVariable(name = "externalId") String id, Model model){
        model.addAttribute("book", bookService.findByExternalId(id));
        return "books/show";
    }

    @GetMapping("/books/isbn/{isbn}")
    public String findByIsbn(@PathVariable(name = "isbn") String isbn, Model model){
        model.addAttribute("book", bookService.findByIsbn(isbn));
        return "books/show";
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid BookInfoRequest request){
        bookService.create(request);
    }

    @PutMapping("/books/{externalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid BookInfoRequest request,
                       @PathVariable(name = "externalId") String id){
        bookService.update(request, id);
    }

}
