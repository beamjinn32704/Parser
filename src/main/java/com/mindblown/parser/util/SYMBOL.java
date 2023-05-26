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
public class SYMBOL extends StrBParser {

        private String strToLookFor;

        public SYMBOL(String strToLookFor) {
            this.strToLookFor = strToLookFor;
        }

        @Override
        public ParseRes<String> parse(String str) {
            return new TOKEN(new STR(strToLookFor)).parse(str);
        }

    }