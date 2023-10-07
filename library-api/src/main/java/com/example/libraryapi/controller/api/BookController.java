package com.example.libraryapi.controller.api;

import com.example.libraryapi.dto.PageRequestInfo;
import com.example.libraryapi.service.BookService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.libraryapi.utill.HttpUtils.PUBLIC_API_V1;

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

}
