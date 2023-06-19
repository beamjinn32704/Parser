/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.ParseRes;
import com.mindblown.parser.Parser;

/**
 *
 * @author beamj
 */
public class TOKEN<T> implements Parser<T> {

    private Parser<T> parser;

    public TOKEN(Parser<T> p) {
        parser = p;
    }

    @Override
    public ParseRes<T> parse(String str) {
        SPACES spaces = new SPACES();

        ParseRes pr1 = spaces.parse(str);
        ParseRes<T> pr2 = parser.parse(pr1.getStrRem());
        ParseRes pr3 = spaces.parse(pr2.getStrRem());
        
        if(pr2.failed()){
            return new ParseRes<>();
        }

        return new ParseRes<>(pr3.getStrRem(), pr2.getParseVal()); //send the string after the spaces have 
        //been removed, and return the value from the Parser
    }
}
