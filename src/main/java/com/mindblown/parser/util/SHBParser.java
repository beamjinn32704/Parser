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
public class SHBParser extends BParser<Parser<String>[]> {

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
        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER, 
                new SYMBOL("str"), new TOKEN(new SURROUND('\"')));
        
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
    };
    
    private static Parser<SYMBOL> SYMBOL_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER, 
                new SYMBOL("sym"), new TOKEN(new SURROUND('\"')));
        if (pr.failed()) {
            return new ParseRes<>();
        }
        String parseVal = pr.getParseVal();
        String strLookingFor = parseVal.substring(4, parseVal.length() - 1);
        return new ParseRes<>(pr.getStrRem(), new SYMBOL(strLookingFor));
    };
    
    private static Parser<TOKEN> TOKEN_PARSER = (str) -> {
        ParseRes<String> pr = PUtil.parserSeq(str, StrBParser.STRING_BINDER, new SYMBOL("tok"), 
                new TOKEN(new SURROUND('{', '}')));
        
        
        if (pr.failed()) {
            return new ParseRes<>();
        }
        String parseVal = pr.getParseVal();
        String parseSH = parseVal.substring(4, parseVal.length() - 1);
        Parser<String>[] ps = PUtil.getParsersSH(parseSH);
        if(ps == null || ps.length == 0){
            return new ParseRes<>();
        }
        Parser<String> tokenP = (str1) -> PUtil.parserSeq(str1, StrBParser.STRING_BINDER, ps);
        return new ParseRes<TOKEN>(pr.getStrRem(), new TOKEN<>(tokenP));
    };

    public SHBParser() {
        super(PUtil.makeBinder());
    }

    @Override
    public ParseRes<Parser<String>[]> parse(String str) {
//        ONE_OF<Parser<String>> parser = new ONE_OF<>(new Parser<Parser<String>>[]{ONE_PARSER, CHR_PARSER, STR_PARSER, SPACES_PARSER, SYMBOL_PARSER, TOKEN_PARSER});
        ONE_OF<Parser<String>> parser = new ONE_OF(ONE_PARSER, CHR_PARSER, STR_PARSER, SPACES_PARSER, SYMBOL_PARSER, TOKEN_PARSER);
        
        ParseRes<Parser<String>[]> pr = ONE_OR_MORE.parseNoB(str, parser);
        
        return pr;
        
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
