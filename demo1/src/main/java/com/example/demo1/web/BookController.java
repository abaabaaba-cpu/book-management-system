package com.example.demo1.web;

import com.example.demo1.mapper.BookMapper;
import com.example.demo1.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BookController {
    @Autowired
    private BookMapper bookMapper;
    @PostMapping(value = "/book")
    public @ResponseBody
    List<Book> getAllBook(HttpServletRequest req, HttpServletResponse rep) {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList = bookMapper.selectAll();
        rep.setStatus(200);
        return bookList;
    }
    @PostMapping(value = "/book/delete")
    public @ResponseBody
    List<Book> deleteBook(HttpServletRequest req, HttpServletResponse rep) {
        ArrayList<Book> bookList = new ArrayList<>();
        String id = req.getParameter("deleteid");
        if(bookMapper.deleteByPrimaryKey(Integer.valueOf(id)) != 0)
            rep.setStatus(200);
        else
            rep.setStatus(201);
        bookList = bookMapper.selectAll();
        return bookList;
    }
    @PostMapping(value = "/book/edit")
    public @ResponseBody
    boolean editBook(HttpServletRequest req, HttpServletResponse rep) throws ParseException {
        Book book = new Book();
        int bookid = Integer.valueOf(req.getParameter("bookid"));
        String bookname = req.getParameter("bookname");
        String author = req.getParameter("author");
        String press = req.getParameter("press");
        String category = req.getParameter("category");
        String language = req.getParameter("language");
        String publicationdate = req.getParameter("publicationdate");
        publicationdate = publicationdate.replace("Z"," UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        book.setPublicationdate(simpleDateFormat.parse(publicationdate));
        book.setBookid(bookid);
        book.setBookname(bookname);
        book.setAuthor(author);
        book.setPress(press);
        book.setCategory(category);
        book.setLanguage(language);
        bookMapper.updateByPrimaryKeySelective(book);
        return true;
    }
    @PostMapping(value = "/book/add")
    public @ResponseBody
    boolean addBook(HttpServletRequest req, HttpServletResponse rep) throws ParseException {
        String bookname = req.getParameter("bookname");
        String author = req.getParameter("author");
        String press = req.getParameter("press");
        String category = req.getParameter("category");
        String publicationdate = req.getParameter("publicationdate");
        String language = req.getParameter("language");
        Book book = new Book();
        book.setBookname(bookname);
        book.setAuthor(author);
        book.setPress(press);
        book.setCategory(category);
        publicationdate = publicationdate.replace("Z"," UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        book.setPublicationdate(simpleDateFormat.parse(publicationdate));
        book.setLanguage(language);
        if(bookMapper.insertSelective(book) != 0)
            return true;
        else
            return false;
    }

    @PostMapping(value = "/book/getBookById")
    public @ResponseBody
    Book getBookById(HttpServletRequest req, HttpServletResponse rep) {
        String id = req.getParameter("bookid");
        Book book = bookMapper.selectByPrimaryKey(Integer.valueOf(id));
        return book;
    }
    @PostMapping(value = "/book/getRelatedBook")
    public @ResponseBody
    ArrayList<Book> getRelatedBook(HttpServletRequest req, HttpServletResponse rep) {
        String category = req.getParameter("category");
        ArrayList<Book> temp = new ArrayList<>();
        temp = bookMapper.$selectByCategory(category);
        ArrayList<Book> books = new ArrayList<>();
        if(temp.size() <= 10) {
            books = temp;
        }
        else{
            Set idset = new HashSet<>();
            while(idset.size() < 10){
                Random rand = new Random();
                int id = rand.nextInt(temp.size());
                idset.add(id);
            }
            for(Iterator iterator = idset.iterator();iterator.hasNext();) {
                books.add(temp.get((int)(iterator.next())));
            }
        }
        return books;
    }
}
