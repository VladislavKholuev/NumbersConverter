package com.company;

public class Main {

    public static void main(String[] args) {
        //example
        try {
            ConverterNumber.ConvertIn10NumSys("2","33",4);
            System.out.println(ConverterNumber.ConverterNumber("3.36",10,7));
        }catch (IllegalArgumentException e ){
            System.out.println(e);
        }
    }
}
