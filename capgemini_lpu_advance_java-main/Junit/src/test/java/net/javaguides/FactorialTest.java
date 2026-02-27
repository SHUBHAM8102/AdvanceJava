package net.javaguides;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    public void fact(){

        Factorial factorial=new Factorial();

        int achualresult=factorial.fac(5);

        assertEquals(120,achualresult);

    }



}