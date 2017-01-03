package com.romao.nhlspider.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class ParserUtil {

    private static List<Character> digits = new ArrayList<>();

    static {
        digits.add('0');
        digits.add('1');
        digits.add('2');
        digits.add('3');
        digits.add('4');
        digits.add('5');
        digits.add('6');
        digits.add('7');
        digits.add('8');
        digits.add('9');
    }

    public static boolean isNumber(final String str) {
        if(str == null || str.equals("")) {
            return false;
        }
        for(char ch : str.toCharArray()) {
            if(!digits.contains(ch)) {
                return false;
            }
        }
        return true;
    }

    public static int convertTimeStringToSeconds(final String timeString) {
        StringTokenizer stTok = new StringTokenizer(timeString, ":");
        int minutes = Integer.parseInt(stTok.nextToken());
        int seconds = Integer.parseInt(stTok.nextToken());

        return minutes*60 + seconds;
    }
}
