/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.BParser;
import com.mindblown.parser.Parser;
import com.mindblown.parser.ParseRes;
import com.mindblown.parser.StrBParser;
import java.util.Arrays;

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
//        System.out.println("CURRENT PARSE SEQ: " + currPR);
        for (int i = 0; i < ps.length; i++) {
            currPR = ps[i].parse(currPR);
//            System.out.println("CURRENT PARSE SEQ: " + currPR);
        }
        return currPR;
    }
    
    public static <T> ParseRes<T> parserSeq(String str, BParser<T>[] ps){
        assert ps.length > 0;
        return parserSeq(str, ps[0], Arrays.copyOfRange(ps, 1, ps.length));
    }
    
    /**
     * Uses the parse string argument to define a set of Parser operations on strToParse, essentially 
     * providing a way to shorthand parsing.
     * @param <T>
     * @param parse The parse string follows this grammar ('|' means or, describes multiple ways of defining a type of parser): <br>
     * <ul> <li> ONE: . | one </li>
     * <li> SAT: sat </li>
     * <li> CHR: c 'character' </li>
     * <li> STR: str "String" </li>
     * <li> SPACES: spc | _ </li>
     * <li> SYMBOL: sym "String" </li>
     * <li> TOKEN: tok {Parser} </li>
     * <li> ONE_OR_MORE: onem {Parser} </li>
     * <li> ZERO_OR_MORE: zerom {Parser} </li>
     * <li> ONE_OF: onef {Parser1} {Parser2} etc. </li>
     * </ul>
     * @param strToParse
     * @return 
     */
    public static ParseRes<String> runParseShorthand(String parse, String strToParse){
        StrBParser[] parsers = getParsersSH(parse);
        return parserSeq(strToParse, parsers);
    }
    
    private static <T> StrBParser[] getParsersSH(String parse){
        ParseRes<StrBParser[]> res = new SHBParser().parse(parse);
        System.out.println("GET PARSERS SH FAILED: " + res.failed());
        return res.getParseVal();
    }
    
    
}
