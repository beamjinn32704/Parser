/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.BParser;
import com.mindblown.parser.ParseRes;
import com.mindblown.parser.Parser;
import static com.mindblown.parser.util.PUtil.pOr;

/**
 *
 * @author beamj
 */
public class ONE_OR_MORE<T> extends BParser<T> {

    private BParser<T> parser;

    public ONE_OR_MORE(BParser<T> parser) {
        super(parser.getBinder());
        this.parser = parser;
    }

    public ParseRes<T> parse(String str) {
        ParseRes<T> currPR = parser.parse(str);
        
        if(currPR.failed()){
            return new ParseRes<>();
        }
        
        ParseRes<T> pr = parse(currPR); //send it to the binder parse.

        return pOr(pr, currPR); // eventually parse(currPR) will return a failed parse, and 
        // when that happens, return currPR
    }
    
    public static <T> ParseRes<T[]> parseNoB(String str, Parser<T> p){
        Parser<T[]> newP = (str1) -> {
            ParseRes<T> pr = p.parse(str1);
            if(pr.failed()){
                return new ParseRes<>();
            }
            return new ParseRes<T[]>(pr.getStrRem(), Util.toLst(pr.getParseVal()));
        };
        
        ONE_OR_MORE<T[]> oomP = new ONE_OR_MORE<>(BParser.newBParser(newP, PUtil.makeBinder()));
        return oomP.parse(str);
    }

}
