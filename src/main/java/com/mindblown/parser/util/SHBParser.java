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
        ParseRes pr = new SYMBOL("one").parse(str);
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

    public SHBParser() {
        super(binder);
    }

    @Override
    public ParseRes<StrBParser[]> parse(String str) {
        ParseRes<StrBParser> pr = new ONE_OF<>(new Parser[]{ONE_PARSER, CHR_PARSER}).parse(str);
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