/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mindblown.parser;

/**
 *
 * @author beamj
 */
public interface Parser<T> {
    ParseRes<T> parse(String str);
}
