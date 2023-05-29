/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser;

import com.mindblown.parser.util.PUtil;

/**
 *
 * @author beamj
 */
public class BParser<T> implements Parser<T> {
    
    protected Binder<T> binder;

    public BParser(Binder<T> binder) {
        this.binder = binder;
    }
    
    public static <T> BParser<T> newBParser(Parser<T> p, Binder<T> binder){
        return new BParser<T>(binder){
            public ParseRes<T> parse(String str){
                return p.parse(str);
            }
        };
    }
    
    public static <T> ParseRes<T> newParseRes(String str){
        return new ParseRes<>(str);
    }
    
    public static <T> ParseRes<T> failedParse(){
        return new ParseRes<>();
    }
    
    @Override
    public ParseRes<T> parse(String str){
        return new ParseRes<>(str);
    }
    
    public final ParseRes<T> parse(ParseRes<T> currPR){
        if(currPR.failed()){
            return new ParseRes<>();
        }
        ParseRes<T> pr = parse(currPR.getStrRem());
        if(pr.failed()){
            return new ParseRes<>();
        }
        return new ParseRes<>(pr.getStrRem(), binder.bind(currPR.getParseVal(), pr.getParseVal()));
    }
    
//    public static <T> ParseRes<T> bind (Parser<T>[] ps, Binder<T> binder, String string){
//        if(ps.length == 0){
//            return new ParseRes(string, null);
//        } else if(ps.length == 1){
//            return ps[0].parse(string);
//        }
//        
//        ParseRes<T> currPR = ps[0].parse(string);
//        for(int i = 1; i < ps.length; i++){
//            ParseRes<T> pr = ps[i].parse(currPR.getStrRem());
//            currPR = new ParseRes(pr.getStrRem(), binder.bind(currPR.getParseVal(), pr.getParseVal()));
//        }
//        return currPR;
//    }

    public Binder<T> getBinder() {
        return binder;
    }
    
}
