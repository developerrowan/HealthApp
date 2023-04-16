package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation {
    public Validation () {}

    public boolean isWithinRange(int target, int min, int max) {
        return min <= target && target <= max;
    }

    public boolean isLengthWithinBounds(String s, int min, int max) {
        int l = s.length();

        return min <= l && l <= max;
    }

    public boolean isValidDateFormat(String date, String desiredFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(desiredFormat);

        try {
            simpleDateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
