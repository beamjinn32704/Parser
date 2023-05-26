/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import static com.mindblown.parser.BParser.failedParse;
import com.mindblown.parser.ParseRes;
import com.mindblown.parser.StrBParser;

/**
 * Takes one character from the current string
 */
public class ONE extends StrBParser {

    public ParseRes<String> parse(String str) {
        if (PUtil.badStr_ES(str)) {
            return failedParse();
        }

        return new ParseRes(str.substring(1), str.substring(0, 1));
    }
}