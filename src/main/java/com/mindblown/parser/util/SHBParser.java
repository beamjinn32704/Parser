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
    
    private final static Parser<StrBParser> PARSER_PARSER = (str1) -> {
        ParseRes<String> pr1 = new TOKEN(new SURROUND('{', '}')).parse(str1);
        if (pr1.failed()) {
            return new ParseRes<>();
        }

        String parseVal = pr1.getParseVal();
        String parseSH = parseVal.substring(1, parseVal.length() - 1);
        StrBParser[] ps = PUtil.getParsersSH(parseSH);
        if (ps == null) {
            return new ParseRes<>();
        }

        Parser totalParser = (str2) -> PUtil.parserSeq(str2, ps);
        StrBParser strBTotalParser = StrBParser.makeParser(totalParser);

        return new ParseRes<>(pr1.getStrRem(), strBTotalParser);
    };

    private final static Parser<ONE> ONE_PARSER = (str) -> {
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

    private final static Parser<CHR> CHR_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, new SYMBOL("c"), new SYMBOL("'"),
                new ONE(), new SYMBOL("'"));
        if (pr.failed()) {
            return new ParseRes<>();
        } else {
            return new ParseRes(pr.getStrRem(), new CHR(pr.getParseVal().charAt(2)));
        }
    };

    private final static Parser<STR> STR_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER,
                new SYMBOL("str"), new TOKEN(new SURROUND('\"')));

        if (pr.failed()) {
            return new ParseRes<>();
        }
        String parseVal = pr.getParseVal();
        String strLookingFor = parseVal.substring(4, parseVal.length() - 1);
        return new ParseRes<>(pr.getStrRem(), new STR(strLookingFor));
    };

    private final static Parser<SPACES> SPACES_PARSER = (str) -> {

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
    };

    private final static Parser<SYMBOL> SYMBOL_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER,
                new SYMBOL("sym"), new TOKEN(new SURROUND('\"')));
        if (pr.failed()) {
            return new ParseRes<>();
        }
        String parseVal = pr.getParseVal();
        String strLookingFor = parseVal.substring(4, parseVal.length() - 1);
        return new ParseRes<>(pr.getStrRem(), new SYMBOL(strLookingFor));
    };

    private final static Parser<TOKEN> TOKEN_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER, new SYMBOL("tok"),
                new TOKEN(new SURROUND('{', '}')));

        if (pr.failed()) {
            return new ParseRes<>();
        }
        String parseVal = pr.getParseVal();
        String parseSH = parseVal.substring(4, parseVal.length() - 1);
        Parser<String>[] ps = PUtil.getParsersSH(parseSH);
        if (ps == null || ps.length == 0) {
            return new ParseRes<>();
        }
        Parser<String> tokenP = (str1) -> PUtil.parserSeq(str1, StrBParser.STRING_BINDER, ps);
        return new ParseRes<TOKEN>(pr.getStrRem(), new TOKEN<>(tokenP));
    };

    private final static Parser<ONE_OR_MORE> ONE_OR_MORE_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER, new SYMBOL("oom"),
                new TOKEN(new SURROUND('{', '}')));
        if (pr.failed()) {
            return new ParseRes<ONE_OR_MORE>();
        }

        String parseVal = pr.getParseVal();
        String parseSH = parseVal.substring(4, parseVal.length() - 1);
        Parser<String>[] ps = PUtil.getParsersSH(parseSH);
        if (ps == null || ps.length == 0) {
            return new ParseRes<>();
        }
        StrBParser[] oomPs = PUtil.getParsersSH(parseSH);
        StrBParser oomP = new StrBParser() {
            @Override
            public ParseRes<String> parse(String str1) {
                return PUtil.parserSeq(str1, oomPs);
            }
        };
        return new ParseRes<ONE_OR_MORE>(pr.getStrRem(), new ONE_OR_MORE<>(oomP));
    };

    private final static Parser<ZERO_OR_MORE> ZERO_OR_MORE_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER, new SYMBOL("zom"),
                new TOKEN(new SURROUND('{', '}')));
        if (pr.failed()) {
            return new ParseRes<ZERO_OR_MORE>();
        }

        String parseVal = pr.getParseVal();
        String parseSH = parseVal.substring(4, parseVal.length() - 1);
        Parser<String>[] ps = PUtil.getParsersSH(parseSH);
        if (ps == null || ps.length == 0) {
            return new ParseRes<>();
        }
        StrBParser[] zomPs = PUtil.getParsersSH(parseSH);
        StrBParser zomP = new StrBParser() {
            @Override
            public ParseRes<String> parse(String str1) {
                return PUtil.parserSeq(str1, zomPs);
            }
        };
        return new ParseRes<ZERO_OR_MORE>(pr.getStrRem(), new ZERO_OR_MORE<>(zomP, ""));
    };

    private final static Parser<ONE_OF> ONE_OF_PARSER = (str) -> {
        TOKEN<String> parserSyntaxP = new TOKEN(new SURROUND('{', '}'));
        StrBParser p = StrBParser.makeParser(parserSyntaxP);
        ONE_OR_MORE<String> notFirstParsers = new ONE_OR_MORE<>(p);

        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER,
                new SYMBOL("oof"), parserSyntaxP, notFirstParsers);

        if (pr.failed()) {
            return new ParseRes<ONE_OF>();
        }

        String parseVal = pr.getParseVal();
        String parsesStr = parseVal.substring(3, parseVal.length());
        
        ParseRes<Object[]> parsersRes = ONE_OR_MORE.parseNoB(parsesStr, PARSER_PARSER);
        if(parsersRes.failed()){
            return new ParseRes<ONE_OF>();
        }
        
        Object[] pObjs = parsersRes.getParseVal();
        StrBParser[] parsers = new StrBParser[pObjs.length];
        for(int i = 0; i < pObjs.length; i++){
            parsers[i] = StrBParser.makeParser((Parser<String>) pObjs[i]);
        }
        
        return new ParseRes<ONE_OF>(pr.getStrRem(), new ONE_OF<>(parsers));
    };

    public SHBParser() {
        super(PUtil.makeBinder());
    }

    @Override
    public ParseRes<StrBParser[]> parse(String str) {
//        ONE_OF<Parser<String>> parser = new ONE_OF<>(new Parser<Parser<String>>[]{ONE_PARSER, CHR_PARSER, STR_PARSER, SPACES_PARSER, SYMBOL_PARSER, TOKEN_PARSER});
        ONE_OF<Parser<String>> parser = new ONE_OF(ONE_PARSER, CHR_PARSER, STR_PARSER,
                SPACES_PARSER, SYMBOL_PARSER, TOKEN_PARSER, ONE_OR_MORE_PARSER,
                ZERO_OR_MORE_PARSER, ONE_OF_PARSER);

        ParseRes<Object[]> pr = ONE_OR_MORE.parseNoB(str, parser);
        
        Object[] pObjs = pr.getParseVal();
        StrBParser[] ps = new StrBParser[pObjs.length];
        for(int i = 0; i < pObjs.length; i++){
            ps[i] = StrBParser.makeParser((Parser<String>) pObjs[i]);
        }

        return new ParseRes<StrBParser[]>(pr.getStrRem(), ps);

//        ParseRes<StrBParser> pr = parser.parse(str);
//        if (pr.failed()) {
//            return new ParseRes<>();
//        }
//
//        ParseRes<StrBParser[]> nextRes = parse(pr.getStrRem());
//        if (nextRes.failed()) {
//            return new ParseRes(pr.getStrRem(), new StrBParser[]{pr.getParseVal()});
//        } else {
//            return new ParseRes(nextRes.getStrRem(), binder.bind(new StrBParser[]{pr.getParseVal()}, nextRes.getParseVal()));
//        }
    }

}
