package com.example.demo.Util;

import com.example.demo.constants.CommonConstants;
import org.antlr.v4.runtime.misc.Pair;

import java.util.Set;

public class StemmerHelper {
    public static Pair<String,String> getPlaceHolders(String text, Set<String> protectedWords){

        String variable = null;

        for (String term : protectedWords){
            if (text.contains(term)){
                variable = term;
                text = text.replace(term, CommonConstants.PLACEHOLDER);
            }
        }
        return Pair.of(text,variable);
    }
}
