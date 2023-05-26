/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser;

/**
 *
 * @author beamj
 */
public class StrBParser extends BParser<String> {
    
    public static Binder<String> STRING_BINDER = (s1, s2) -> s1 + s2;

    public StrBParser() {
        super(STRING_BINDER); // used lambda to create a Binder interface that concats two strings
    }
    
//    public static UParser.ONE_OR_MORE<String> oneOrMore(String str){
//        return new UParser.ONE_OR_MORE<>(STRING_BINDER, str);
//    }
    
}
