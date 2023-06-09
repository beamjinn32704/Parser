/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mindblown.parser;

import com.mindblown.parser.util.*;

/**
 *
 * @author beamj
 */
public class Main {

    public static void main(String[] args) {
        
        
//        ParseRes pr = new ONE().parse("Hi");
//        ParseRes pr2 = new ONE().parse(pr);
//        ParseRes pr3 = new ONE().parse(pr2);
//        System.out.println(pr);
//        System.out.println(pr2);
//        System.out.println(pr3);
//        System.out.println("");
//        
//        ParseRes pr4 = new CHR('H').parse("Hi?");
//        ParseRes pr5 = new CHR('i').parse(pr4);
//        ParseRes pr6 = new CHR('!').parse(pr5);
//        ParseRes pr7 = new ONE().parse(pr6);
//        System.out.println(pr4);
//        System.out.println(pr5);
//        System.out.println(pr6);
//        System.out.println(pr7);
//        System.out.println("");
//        
//        ParseRes pr8 = new STR("Hi!").parse("Hi!  wfwe ");
//        ParseRes pr9 = new STR("Hi!").parse("HI!");
//        System.out.println(pr8);
//        System.out.println(pr9);
//        System.out.println("");
//        
//        ParseRes pr10 = new SAT((str) -> str.length() % 2 == 0).parse("Hi!");
//        ParseRes pr11 = new SAT((str) -> str.length() % 2 == 0).parse("Hiya! ");
//        System.out.println(pr10);
//        System.out.println(pr11);
//        System.out.println("");
//        
//        ParseRes pr12 = new SPACES().parse("    YOYO     YEP");
//        ParseRes pr13 = new STR("YOYO").parse(pr12);
//        ParseRes pr14 = new SPACES().parse(pr13);
//        ParseRes pr15 = new STR("YEP").parse(pr14);
//        ParseRes pr16 = new SPACES().parse(pr15);
//        System.out.println(pr12);
//        System.out.println(pr13);
//        System.out.println(pr14);
//        System.out.println(pr15);
//        System.out.println(pr16);
//        System.out.println("");
//        
//        ParseRes pr17 = new ONE_OR_MORE(new STR("123")).parse("123123123123123   456  ? ");
//        ParseRes pr18 = new ZERO_OR_MORE(new STR("LES GO"), "").parse(pr17);
//        ParseRes pr19 = new SYMBOL("456").parse(pr18);
//        ParseRes pr20 = new ONE_OR_MORE(new STR("!")).parse(pr19);
//        System.out.println(pr17);
//        System.out.println(pr18);
//        System.out.println(pr19);
//        System.out.println(pr20 + "\n");
//        
//        
//        ONE_OR_MORE<String> p1 = new ONE_OR_MORE<>(new SYMBOL("var"));
//        SYMBOL p2 = new SYMBOL("name");
//        SYMBOL p3 = new SYMBOL("=");
//        SYMBOL p4 = new SYMBOL("value");
//        SYMBOL p5 = new SYMBOL(";");
//        ParseRes pr21 = PUtil.parserSeq("var name = value;   ", p1, p2, p3, p4, p5);
//        System.out.println(pr21);
        
        ParseRes pr22 = PUtil.runParseShorthand("one c 'i'", "Hi!");
        ParseRes pr23 = PUtil.runParseShorthand("c 'H' one one one c 'O'", "HellO!!");
        ParseRes pr24 = PUtil.runParseShorthand("c 'H' one one one c 'O'", "Hello!!");
        ParseRes pr25 = PUtil.runParseShorthand("str \"YO \" ... c '!'", "YO BEN!WASSUP");
        ParseRes pr26 = PUtil.runParseShorthand("_ str \"var\" spc T str \"string\" _ ", "var string = 'LETS GO';");
        ParseRes pr27 = PUtil.runParseShorthand("sym \"var\" _ ...... sym \"=\"", "var string = 'LETS GO';");
        ParseRes pr28 = PUtil.runParseShorthand("tok {str \"var\" _ ......}", "      var string = 'LETS GO';");
        ParseRes pr29 = PUtil.runParseShorthand("oom {c '.'}", ".......YO");
        ParseRes pr30 = PUtil.runParseShorthand("oom {c '.'}", "YO");
        ParseRes pr31 = PUtil.runParseShorthand("zom {c '.'}", ".......YO");
        ParseRes pr32 = PUtil.runParseShorthand("zom {c '.'}", "YO");
        ParseRes pr33 = PUtil.runParseShorthand("oof {c '1'} {c '2'} {c '3'} {c '4'} {c '5'}", "4");
        ParseRes pr34 = PUtil.runParseShorthand("oof {c '1'} {c '2'} {c '3'} {c '4'} {c '5'}", "6");
        ParseRes pr35 = PUtil.runParseShorthand("oof {c '1'} {c '2'} {c '3'} {c '4'} {c '5'} sur '", "5 'Hello there!'");
        System.out.println(pr22);
        System.out.println(pr23);
        System.out.println(pr24);
        System.out.println(pr25);
        System.out.println(pr26);
        System.out.println(pr27);
        System.out.println(pr28);
        System.out.println(pr29);
        System.out.println(pr30);
        System.out.println(pr31);
        System.out.println(pr32);
        System.out.println(pr33);
        System.out.println(pr34);
        System.out.println(pr35);
        
    }
}
