/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mindblown.parser.util;

/**
 * Short for Predicate, is a helper interface that determines whether a string
 * should be parsed.
 *
 * @author beamj
 */
public interface Pred {

    public boolean sat(String str);
}
