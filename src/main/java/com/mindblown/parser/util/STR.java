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
public class STR extends StrBParser {

    private String strLookFor;

    public STR(String strLookFor) {
        super();
        this.strLookFor = strLookFor;
    }

    public ParseRes<String> parse(String str) {
        ParseRes currPR = new ParseRes(str, ""); //start empty
        for (int i = 0; i < strLookFor.length(); i++) {
            char c = strLookFor.charAt(i);
            currPR = new CHR(c).parse(currPR);
        }
        return currPR;
    }
}
