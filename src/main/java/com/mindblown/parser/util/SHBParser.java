/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.BParser;
import com.mindblown.parser.Binder;
import com.mindblown.parser.ParseRes;
import com.mindblown.parser.Parser;
import com.mindblown.parser.StrBParser;

/**
 *
 * @author beamj
 */
public class SHBParser extends BParser<StrBParser[]> {

    private static Binder<StrBParser[]> binder = (arr1, arr2) -> {
        StrBParser[] arr = new StrBParser[arr1.length + arr2.length];
        for (int i = 0; i < arr1.length; i++) {
            arr[i] = arr1[i];
        }
        for (int i = 0; i < arr2.length; i++) {
            arr[arr1.length + i] = arr2[i];
        }
        return arr;
    };

    private static Parser<ONE> ONE_PARSER = (str) -> {
        StrBParser[] ps = new StrBParser[]{new SYMBOL("one"), new SYMBOL(".")};
        Parser<String> p = new ONE_OF<>(ps);
        ParseRes pr = p.parse(str);
//        ParseRes pr = new SYMBOL("one").parse(str);
        if (!pr.failed()) {
            return new ParseRes(pr.getStrRem(), new ONE());
        } else {
            return new ParseRes<>();
        }
    };

    private static Parser<CHR> CHR_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, new SYMBOL("c"), new SYMBOL("'"),
                new ONE(), new SYMBOL("'"));
        if (pr.failed()) {
            return new ParseRes<>();
        } else {
            return new ParseRes(pr.getStrRem(), new CHR(pr.getParseVal().charAt(2)));
        }
    };

    private static Parser<STR> STR_PARSER = (str) -> {
        ONE_OR_MORE<String> oneOrMoreP = new ONE_OR_MORE<>(new SAT((str1) -> str1.charAt(0) != '"'));
        ParseRes<String> pr = PUtil.parserSeq(str, new SYMBOL("str"), new SPACES(),
                new CHR('"'), oneOrMoreP, new CHR('"'));
        if (pr.failed()) {
            return new ParseRes<>();
        }
        String parseVal = pr.getParseVal();
        String strLookingFor = parseVal.substring(4, parseVal.length() - 1);
        return new ParseRes<>(pr.getStrRem(), new STR(strLookingFor));
    };

    private static Parser<SPACES> SPACES_PARSER = (str) -> {

        Parser<SPACES> p1 = (str1) -> {
            ParseRes<String> pr1 = PUtil.parserSeq(str1, StrBParser.STRING_BINDER, new SYMBOL("spc"),
                    new ONE_OF<String>(new StrBParser[]{new SYMBOL("T"), new SYMBOL("F")}));
            if (pr1.failed()) {
                return new ParseRes<>();
            } else {
                String parseVal = pr1.getParseVal();
                boolean keepSpc = parseVal.charAt(parseVal.length() - 1) == 'T';
                return new ParseRes<>(pr1.getStrRem(), new SPACES(keepSpc));
            }
        };

        Parser<SPACES> p2 = (str1) -> {
            ParseRes<String> pr2 = new SYMBOL("_").parse(str1);
            if (pr2.failed()) {
                return new ParseRes<>();
            } else {
                return new ParseRes<>(pr2.getStrRem(), new SPACES());
            }
        };
        
        return PUtil.pOr(p1, p2, str);
        
//        
//
//        ONE_OF<String> oneOfP = new ONE_OF<>(new StrBParser[]{new STR("spc"), new CHR('_')});
//        ParseRes<String> pr = oneOfP.parse(str);
//
//        if (pr.failed()) {
//            return new ParseRes<SPACES>();
//        }
//
//        return new ParseRes<SPACES>(pr.getStrRem(), new SPACES());
    };
    
    private static Parser<SYMBOL> SYMBOL_PARSER = (str) -> {
        ONE_OR_MORE<String> strInQuotesP = new ONE_OR_MORE<>(new SAT((str1) -> str1.charAt(0) != '"'));
        ParseRes<String> pr = PUtil.parserSeq(str, new SYMBOL("sym"), new SPACES(),
                new CHR('"'), strInQuotesP, new CHR('"'));
        if (pr.failed()) {
            return new ParseRes<>();
        }
        String parseVal = pr.getParseVal();
        String strLookingFor = parseVal.substring(4, parseVal.length() - 1);
        return new ParseRes<>(pr.getStrRem(), new SYMBOL(strLookingFor));
    };

    public SHBParser() {
        super(binder);
    }

    @Override
    public ParseRes<StrBParser[]> parse(String str) {
        ParseRes<StrBParser> pr = new ONE_OF<>(new Parser[]{ONE_PARSER, CHR_PARSER, STR_PARSER, SPACES_PARSER, SYMBOL_PARSER}).parse(str);
        if (pr.failed()) {
            return new ParseRes<>();
        }

        ParseRes<StrBParser[]> nextRes = parse(pr.getStrRem());
        if (nextRes.failed()) {
            return new ParseRes(pr.getStrRem(), new StrBParser[]{pr.getParseVal()});
        } else {
            return new ParseRes(nextRes.getStrRem(), binder.bind(new StrBParser[]{pr.getParseVal()}, nextRes.getParseVal()));
        }
    }

}
