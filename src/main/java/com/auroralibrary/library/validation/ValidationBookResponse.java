package com.auroralibrary.library.validation;

public class ValidationBookResponse {

    private static final String directory = "/BookCovers/";
    public static String directoryBookCover(String bookCover){
        return directory + bookCover;
    }

}
