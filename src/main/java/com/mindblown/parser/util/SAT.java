/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import static com.mindblown.parser.BParser.failedParse;
import com.mindblown.parser.ParseRes;
import com.mindblown.parser.StrBParser;

/**
 * Takes a character from the current string if the current string satisfies the
 * Pred given
 */
public class SAT extends StrBParser {

    private Pred pred;

    public SAT(Pred doParse) {
        super();
        pred = doParse;
    }

    public ParseRes<String> parse(String str) {
        if (PUtil.badStr_ES(str)) {
            return failedParse();
        }

        if (pred.sat(str)) {
            return new ONE().parse(str);
        } else {
            return failedParse();
        }
    }
}
