package com.example.demo1.web;

import com.example.demo1.mapper.BookMapper;
import com.example.demo1.mapper.BookshelfbookMapper;
import com.example.demo1.mapper.BookshelfuserMapper;
import com.example.demo1.model.Book;
import com.example.demo1.model.BookshelfbookKey;
import com.example.demo1.model.Bookshelfuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class BookshelfController {
    @Autowired
    private BookshelfbookKey bookshelfbook;
    @Autowired
    private BookshelfbookMapper bookshelfbookMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookshelfuserMapper bookshelfuserMapper;
    @Autowired
    private Bookshelfuser bookshelfuser;
    @PostMapping(value = "/bookshelf/getAllBookshelfByStudentid")
    public @ResponseBody
    List<Bookshelfuser> getAllBookshelfByStudentid(HttpServletRequest req, HttpServletResponse rep) {
        String id = req.getParameter("id");
        ArrayList<Bookshelfuser> bookshelfusers= new ArrayList<>();
        bookshelfuser = new Bookshelfuser();
        bookshelfuser = bookshelfuserMapper.selectByPrimaryKey(Integer.valueOf(id));
        bookshelfusers = bookshelfuserMapper.$selectByStudentid(Integer.valueOf(id));
        return bookshelfusers;
    }
    @PostMapping(value = "/bookshelf/ifBookExistInShelf")
    public @ResponseBody
    boolean ifBookExistInShelf(HttpServletRequest req, HttpServletResponse rep) {
        String bookshelfid = req.getParameter("bookshelfid");
        String bookid = req.getParameter("bookid");
        ArrayList<BookshelfbookKey> bookshelfbookKeys = bookshelfbookMapper.$selectByBookshelfid(Integer.valueOf(bookshelfid));
        for(Iterator i = bookshelfbookKeys.iterator();i.hasNext();)
        {
            if(((BookshelfbookKey)(i.next())).getBookid() == Integer.valueOf(bookid))
                return true;
        }
        return false;
    }
    @PostMapping(value = "/bookshelf/getAllBookByBookshelfid")
    public @ResponseBody
    List<Book> getAllBookByBookshelfid(HttpServletRequest req, HttpServletResponse rep) {
        ArrayList<BookshelfbookKey> bookshelfbooks = new ArrayList<>();
        String id = req.getParameter("bookshelfid");
        bookshelfbooks = bookshelfbookMapper.$selectByBookshelfid(Integer.valueOf(req.getParameter("bookshelfid")));
        int[] bookids = new int[bookshelfbooks.size()];
        ArrayList<Book> books = new ArrayList<>();
        int i = 0;
        for(Iterator j = bookshelfbooks.iterator(); j.hasNext();i++)
        {
            bookids[i] = ((BookshelfbookKey)(j.next())).getBookid();
            books.add(bookMapper.selectByPrimaryKey(bookids[i]));
        }
        return books;
    }
    @PostMapping(value = "/bookshelf/editBookshelf")
    public @ResponseBody
    boolean editBookshelf(HttpServletRequest req, HttpServletResponse rep) {
       int bookshelfid = Integer.valueOf(req.getParameter("bookshelfid"));
       String newProfile = req.getParameter("bookshelfprofile");
       String newName = req.getParameter("bookshelfname");
       bookshelfuser = new Bookshelfuser();
       bookshelfuser.setId(bookshelfid);
       bookshelfuser.setProfile(newProfile);
       bookshelfuser.setName(newName);
       if(bookshelfuserMapper.updateByPrimaryKeySelective(bookshelfuser) != 0)
            return true;
       else
           return false;
    }
    @PostMapping(value = "/bookshelf/addNewBookShelf")
    public @ResponseBody
    boolean addNewBookShelf(HttpServletRequest req, HttpServletResponse rep) {
        String name = req.getParameter("bookShelfname");
        String id = req.getParameter("studentid");
        bookshelfuser = new Bookshelfuser();
        bookshelfuser.setName(req.getParameter("bookShelfname"));
        bookshelfuser.setStudentid(Integer.valueOf(req.getParameter("studentid")));
        bookshelfuser.setProfile(req.getParameter("bookShelfProfile"));
        if(bookshelfuserMapper.insert(bookshelfuser) != 0)
            return true;
        else
            return false;
    }
    @PostMapping(value = "/bookshelf/deleteBookshelfByid")
    public @ResponseBody
    boolean deleteBookshelf(HttpServletRequest req, HttpServletResponse rep) {
        String name = req.getParameter("bookShelfname");
        String id = req.getParameter("studentid");
        if(bookshelfuserMapper.deleteByPrimaryKey(Integer.valueOf(req.getParameter("bookshelfid"))) != 0)
            return true;
        else
            return false;
    }
    @PostMapping(value = "/bookshelf/addBookToShelves")
    public @ResponseBody
    boolean addBookToshelves(HttpServletRequest req, HttpServletResponse rep) {
        int bookid = Integer.valueOf(req.getParameter("bookid"));
        int selectedLength = Integer.valueOf(req.getParameter("selectedLength"));
        int notselectedLength = Integer.valueOf(req.getParameter("notselectedLength"));
        int[] selectedShelfidList = new int[selectedLength];
        int[] notselectedShelfidList = new int[notselectedLength];
        for(int i = 0;i < selectedLength;i++)
            selectedShelfidList[i] = Integer.valueOf(req.getParameter(String.format("selectedShelfids[%d]",i)));
        for(int i = 0;i < notselectedLength;i++)
            notselectedShelfidList[i] = Integer.valueOf(req.getParameter(String.format("notselectedShelfids[%d]",i)));
        for(int i = 0;i < selectedLength;i++)
        {
            bookshelfbook.setBookid(bookid);
            bookshelfbook.setBookshelfid(selectedShelfidList[i]);
            ArrayList<BookshelfbookKey> bookshelfbookKeys = new ArrayList<>();
            bookshelfbookKeys = bookshelfbookMapper.$selectByBookshelfid(bookshelfbook.getBookshelfid());
            boolean exist = false;
            for(Iterator j = bookshelfbookKeys.iterator();j.hasNext();)
            {
                if(((BookshelfbookKey)(j.next())).getBookid() == bookid)
                    exist = true;
            }
            if(exist == false)
                bookshelfbookMapper.insertSelective(bookshelfbook);
        }
        for(int i = 0;i < notselectedLength;i++)
        {
            bookshelfbook.setBookid(bookid);
            bookshelfbook.setBookshelfid(notselectedShelfidList[i]);
            ArrayList<BookshelfbookKey> bookshelfbookKeys = new ArrayList<>();
            bookshelfbookKeys = bookshelfbookMapper.$selectByBookshelfid(bookshelfbook.getBookshelfid());
            for(Iterator j = bookshelfbookKeys.iterator();j.hasNext();)
            {
                if(((BookshelfbookKey)(j.next())).getBookid() == bookid)
                    bookshelfbookMapper.deleteByPrimaryKey(bookshelfbook);
            }
        }
        return true;
    }
    @PostMapping(value = "/bookshelf/deletBookfromShelf")
    public @ResponseBody
    boolean deletBookfromShelf(HttpServletRequest req, HttpServletResponse rep) {
        int bookid = Integer.valueOf(req.getParameter("bookid"));
        int shelfid = Integer.valueOf(req.getParameter("shelfid"));
        bookshelfbook.setBookid(bookid);
        bookshelfbook.setBookshelfid(shelfid);
        bookshelfbookMapper.deleteByPrimaryKey(bookshelfbook);
        return true;
    }
}
