/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.ParseRes;
import com.mindblown.parser.StrBParser;

/**
 *
 * @author beamj
 */
public class SPACES extends StrBParser {
    
    private boolean keepSpaces;

    public SPACES() {
        super();
        keepSpaces = false;
    }
    
    /**
     * 
     * @param keepSpaces whether to put the spaces absorbed into the ParseRes' output
     */
    public SPACES(boolean keepSpaces) {
        this.keepSpaces = keepSpaces;
    }

    @Override
    public ParseRes<String> parse(String str) {
        SAT spaceP = new SAT((string) -> Character.isWhitespace(string.charAt(0)));
        ParseRes pr = new ZERO_OR_MORE(spaceP, "").parse(str);
        if(keepSpaces){
            return pr;
        } else {
            return new ParseRes(pr.getStrRem(), "");
        }
    }

}
