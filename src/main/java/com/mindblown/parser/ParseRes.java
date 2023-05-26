/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser;

/**
 *
 * @author beamj
 * @param <T> Class type of the value the ParseRes returns
 */
public class ParseRes<T> {

    private String strRem;
    private T parseVal;
    private boolean parseFail;

    public ParseRes(String currString, T parseValue) {
        if (currString == null) {
            fail();
        } else {
            strRem = currString;
            parseVal = parseValue;
            parseFail = false;
        }

    }

    public ParseRes(String str) {
        if (str == null) {
            fail();
        } else {
            strRem = str;
            parseVal = null;
            parseFail = false;
        }
    }

    private void fail() {
        parseFail = true;
        strRem = null;
        parseVal = null;
    }

    /**
     * Create a ParseRes whose status is "Failed".
     */
    public ParseRes() {
        fail();
    }

    public boolean failed() {
        return parseFail;
    }

    public T getParseVal() {
        assert !parseFail;
        return parseVal;
    }

    public String getStrRem() {
        assert !parseFail;
        return strRem;
    }

//    public void setParseVal(T parseVal) {
//        assert !parseFail;
//        this.parseVal = parseVal;
//    }
//
//    public void setStrRem(String strRem) {
//        assert !parseFail;
//        this.strRem = strRem;
//
//    }
    
    @Override
    public String toString() {
        if (failed()) {
            return "(FAILED PARSE RES)";
        }
        return "(Remains: '" + strRem + "', Val: '" + parseVal + "')";
    }

}
