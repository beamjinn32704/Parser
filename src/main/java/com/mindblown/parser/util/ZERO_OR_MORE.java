/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.BParser;
import com.mindblown.parser.ParseRes;
import static com.mindblown.parser.util.PUtil.pOr;

/**
 *
 * @author beamj
 */
public class ZERO_OR_MORE<T> extends BParser<T> {

    private BParser<T> parser;
    private T def;

    /**
     *
     * @param parser
     * @param def a default value for the ParseRes to be returned from parse()
     * if the parser given doesn't parse anything.
     */
    public ZERO_OR_MORE(BParser<T> parser, T def) {
        super(parser.getBinder());
        this.parser = parser;
        this.def = def;
    }

    public ParseRes<T> parse(String str) {
        ONE_OR_MORE<T> oneOrMore = new ONE_OR_MORE<>(parser);
        return pOr(oneOrMore.parse(str), new ParseRes(str, def));
    }

}