package com.example.wijih.a310.model;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BookTest {


    @Test
    public void testBookConstruction() {
        Book testBook = new Book("test", "test description", "1234",
                false, new ArrayList<String>());

        assertEquals("test", testBook.getTitle());
        assertEquals("test description", testBook.getDescription());
        assertEquals("1234", testBook.getOwnerID());
        assertEquals(false, testBook.isForSale());
    }

}