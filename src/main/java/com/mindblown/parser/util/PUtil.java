/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.BParser;
import com.mindblown.parser.Binder;
import com.mindblown.parser.Parser;
import com.mindblown.parser.ParseRes;
import com.mindblown.parser.StrBParser;
import java.util.ArrayList;
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

    /**
     * If first parser doesn't fail, return the result from that; otherwise,
     * return the result from the second parser.
     *
     * @param <T>
     * @param p1
     * @param p2
     * @param str
     * @return
     */
    public static <T> ParseRes<T> pOr(Parser<T> p1, Parser<T> p2, String str) {
        ParseRes<T> pr1 = p1.parse(str);
        if (!pr1.failed()) {
            return pr1;
        } else {
            return p2.parse(str);
        }
    }

    public static <T> ParseRes<T[]> parserSeqNoB(String str, Parser<T> p1, Parser<T>... ps) {
        ParseRes<T> firstPR = p1.parse(str);
        if(firstPR.failed()){
            return new ParseRes<>();
        }
        Binder<T[]> binder = makeBinder();
        ParseRes<T[]> currPR = new ParseRes<>(firstPR.getStrRem(), 
                Util.toLst(firstPR.getParseVal()));
        for (int i = 0; i < ps.length; i++) {
            ParseRes<T> pr = ps[i].parse(currPR.getStrRem());
            if (pr.failed()) {
                return new ParseRes<>();
            }
            currPR = new ParseRes<>(pr.getStrRem(),
                    binder.bind(currPR.getParseVal(), Util.toLst(pr.getParseVal())));
        }
        return currPR;
    }
    
    public static <T> ParseRes<T[]> parserSeqNoB(String str, Parser<T>[] ps) {
        assert ps.length > 0;
        return parserSeqNoB(str, ps[0], Arrays.copyOfRange(ps, 1, ps.length));
    }
    
    public static <T> Binder<T[]> makeBinder() {
        Binder<T[]> binder = (a1, a2) -> {
            ArrayList<T> tList = new ArrayList<>();
            for (int i = 0; i < a1.length; i++) {
                tList.add(a1[i]);
            }
            for (int i = 0; i < a2.length; i++) {
                tList.add(a2[i]);
            }
            return tList.toArray(a1);
        };
        return binder;
    }

    public static <T> ParseRes<T> parserSeq(String str, Binder<T> binder, Parser<T> p1, Parser<T>... ps) {
        ParseRes<T> currPR = p1.parse(str);
        for (int i = 0; i < ps.length; i++) {
            ParseRes<T> pr = ps[i].parse(currPR.getStrRem());
            if (pr.failed()) {
                return new ParseRes<>();
            }
            currPR = new ParseRes<>(pr.getStrRem(),
                    binder.bind(currPR.getParseVal(), pr.getParseVal()));
        }
        return currPR;
    }

    public static <T> ParseRes<T> parserSeq(String str, Binder<T> binder, Parser<T>[] ps) {
        assert ps.length > 0;
        return parserSeq(str, binder, ps[0], Arrays.copyOfRange(ps, 1, ps.length));
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

    public static <T> ParseRes<T> parserSeq(String str, BParser<T>[] ps) {
        assert ps.length > 0;
        return parserSeq(str, ps[0], Arrays.copyOfRange(ps, 1, ps.length));
    }

    /**
     * Uses the parse string argument to define a set of Parser operations on
     * strToParse, essentially providing a way to shorthand parsing.
     *
     * @param <T>
     * @param parse The parse string follows this grammar ('|' means or,
     * describes multiple ways of defining a type of parser): <br>
     * <ul> <li> ONE: . | one </li>
     * <li> SAT: sat </li>
     * <li> CHR: c 'character' </li>
     * <li> STR: str "String" </li>
     * <li> SPACES: spc T | spc F | _ </li>
     * <li> SYMBOL: sym "String" </li>
     * <li> TOKEN: tok {ParserSH} </li>
     * <li> ONE_OR_MORE: oom {ParserSH} </li>
     * <li> ZERO_OR_MORE: zom {ParserSH} </li>
     * <li> ONE_OF: oof {ParserSH1} {ParserSH2} etc. </li>
     * <li> SURROUND: sur char | sur startChar endChar
     * </ul>
     * @param strToParse
     * @return
     */
    public static ParseRes<String> runParseShorthand(String parse, String strToParse) {
        Parser<String>[] parsers = getParsersSH(parse);
        if (parsers == null) {
            return new ParseRes<>();
        }
        return parserSeq(strToParse, StrBParser.STRING_BINDER, parsers);
    }

    public static StrBParser[] getParsersSH(String parse) {
        ParseRes<StrBParser[]> res = new SHBParser().parse(parse);
        
        if(res.failed() || !res.getStrRem().isBlank()){
            return null;
        }
        
        return res.getParseVal();
    }

}
