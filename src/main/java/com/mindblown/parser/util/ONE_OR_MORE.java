/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.BParser;
import com.mindblown.parser.ParseRes;
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

}
