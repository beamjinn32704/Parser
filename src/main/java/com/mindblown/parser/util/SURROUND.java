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
public class SURROUND extends StrBParser {
    
    private char startC;
    private char endC;

    public SURROUND(char startC, char endC) {
        this.startC = startC;
        this.endC = endC;
    }

    public SURROUND(char c) {
        startC = endC = c;
    }

    @Override
    public ParseRes<String> parse(String str) {
        ONE_OR_MORE<String> surroundedP = new ONE_OR_MORE<>(new SAT((str1) -> str1.charAt(0) != endC));
        return PUtil.parserSeq(str, new CHR(startC), surroundedP, new CHR(endC));
    }
    
}
