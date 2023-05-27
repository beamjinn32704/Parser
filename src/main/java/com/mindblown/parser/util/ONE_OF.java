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
public class ONE_OF<T> implements Parser<T> {
    
    private Parser<T>[] parserLst;

    public ONE_OF(Parser<T>[] parserLst) {
        this.parserLst = parserLst;
    }

    @Override
    public ParseRes<T> parse(String str) {
        if(parserLst.length == 0){
            return new ParseRes<>(); //return a failed parse
        }
        
        for(int i = 0; i < parserLst.length; i++){
            ParseRes<T> pr = parserLst[i].parse(str);
            if(!pr.failed()){
                return pr;
            }
        }
        
        return new ParseRes<>();
    }
    
}
