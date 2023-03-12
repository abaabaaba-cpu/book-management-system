package com.example.demo1.config;

import com.example.demo1.utils.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterCeptorConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new Interceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        static文件夹下的静态文件应该是”/“
        String[] excludePath ={"/holdingInformation/editHoldingInfo","/holdingInformation/getHoldingnumByBookid","/holdingInformation/getBooknumByBookid","/holdingInformation/getBookByBarcode","/holdingInformation/getHoldingnumAndRemainByBookid", "/holdingInformation/getHoldingInformationByBookid",
                "/bookDetail", "/book/getBookById",
                "/server/url",
                "/user/getUser", "/userLogin",
                "/operator/getOperator",
                "/student/login","/student/register","/student/delete", "/student/getStudent", "/student/edit", "/student/changePassword", "/student/getAll",
                "/borrowHistory/getAll",
                "/borrowHistory/getBorrowHistory","/backend",
                "/borrowHistory/getBookidByBarcode",
                "/borrowHistory/getCurrentBorrowingByUserid",
                "/borrowHistory/changeLossState",
                "/borrowHistory/deleteBorrowHistory",
                "/borrowHistory/returnBook",
                "/borrowHistory/pay",
                "/borrowHistory/borrowBooks",
                "/book/add","/book/edit","/book","/book/delete","/book/getRelatedBook",
                "/bookshelf/getAllBookshelfByStudentid",
                "/bookshelf/getAllBookByBookshelfid",
                "/bookshelf/addNewBookShelf",
                "/bookshelf/deleteBookshelfByid",
                "/bookshelf/addBookToShelves",
                "/bookshelf/ifBookExistInShelf",
                "/bookshelf/deletBookfromShelf",
                "/bookshelf/editBookshelf",
                "/search","/home","/operator/login","/personalCenter",
                "/assets/**","/Backend/**","/Login/**","/login","/icon/**","/PersonalCenter/**","/Search/**","/Home/**","/BookDetail/**"};
        registry.addInterceptor(getMyInterceptor())
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}