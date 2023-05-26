/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.parser.util;

import com.mindblown.parser.ParseRes;
import com.mindblown.parser.StrBParser;

/**
 *
 * @author beamj
 */
public class CHR extends StrBParser {

        private char c;

        public CHR(char c) {
            this.c = c;
        }

        public ParseRes<String> parse(String str) {
            Pred p = new Pred() {
                @Override
                public boolean sat(String str) {
                    return str.charAt(0) == c;
                }
            };
            return new SAT(p).parse(str);
        }
    }