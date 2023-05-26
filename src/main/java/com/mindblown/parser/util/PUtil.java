/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.BParser;
import static com.mindblown.parser.BParser.failedParse;
import com.mindblown.parser.ParseRes;
import com.mindblown.parser.Parser;
import com.mindblown.parser.StrBParser;

/**
 *
 * @author beamj
 */
public class PUtil {

    /**
     * A helper function that determines whether a string given to a Parser is
     * null or empty.
     *
     * @param str
     * @return
     */
    public static boolean badStr_ES(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * If first parseRes didn't fail, return that; otherwise, return the second
     * parseRes.
     *
     * @param <T>
     * @param pr1
     * @param pr2
     * @return
     */
    public static <T> ParseRes<T> pOr(ParseRes<T> pr1, ParseRes<T> pr2) {
        if (!pr1.failed()) {
            return pr1;
        } else {
            return pr2;
        }
    }

    public static <T> ParseRes<T> parserSeq(String str, BParser<T> p1, BParser<T>... ps) {
        ParseRes<T> currPR = p1.parse(str);
        System.out.println("CURRENT PARSE SEQ: " + currPR);
        for (int i = 0; i < ps.length; i++) {
            currPR = ps[i].parse(currPR);
            System.out.println("CURRENT PARSE SEQ: " + currPR);
        }
        return currPR;
    }
}
