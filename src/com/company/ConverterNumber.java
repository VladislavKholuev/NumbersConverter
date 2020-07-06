package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConverterNumber {

    public static String ConvertIn10NumSys(String wholeNum, String fracNum, int numSys) {

        /**
         * parse whole number to 10 numeral system
         */

        wholeNum = wholeNum.toUpperCase();
        long wholeNumIn10Sys = Long.parseLong(wholeNum,numSys);

        /**
         * parse fractional number to 10 numeral system
         */

        double fracNumIn10Sys = 0;
        fracNum = fracNum.toUpperCase();
        for (int i = fracNum.length() - 1; i >= 0; i--) {
            char ch = fracNum.charAt(i);
            if (numSys + 55 <= (int) ch)
                throw new IllegalArgumentException("the number is incorrect : fractional number can not be represented in this number system");
            if (Character.isDigit(ch)) {
                fracNumIn10Sys += Character.getNumericValue(ch) * Math.pow(numSys, -(i + 1));
            } else {
                int digit = (int) ch - 55;
                fracNumIn10Sys += digit * Math.pow(numSys, -(i + 1));
            }
        }
        return String.valueOf(fracNumIn10Sys + wholeNumIn10Sys);
    }

    public static String ConverterNumber(String nums, int inputNumSys, int outputNumSys) {
        if (nums.equals("")) {
            throw new IllegalArgumentException("the number is incorrect");
        }
        if (inputNumSys < 2 || inputNumSys > 36) {
            throw new IllegalArgumentException("input numeral system must satisfy the conditions: 1<x<37");
        }
        if (outputNumSys < 2 || outputNumSys > 36) {
            throw new IllegalArgumentException("output numeral system must satisfy the conditions: 1<x<37");
        }

        boolean sign = false;
        if (nums.charAt(0) == '-') {
            sign = true;
            nums = nums.substring(1);
        }

        /**
         * convert in 10 numeral system
         */

        if (nums.contains(".")) {
            String[] arrStr = nums.split("\\.");
            if (arrStr.length == 2 || !arrStr[0].equals("") || !arrStr[1].equals("")) {
                nums = ConvertIn10NumSys(arrStr[0], arrStr[1], inputNumSys);
            }
        } else {
            nums = ConvertIn10NumSys(nums, "0", inputNumSys);
        }

        /**
         * convert fractional number from 10 numeral system in outputSystem
         */

        double num = Double.parseDouble(nums);
        int wholeNum = (int) Math.floor(num);
        num = num - wholeNum;
        StringBuilder resultNum = new StringBuilder("");
        while (wholeNum >= outputNumSys) {
            int temp = wholeNum % outputNumSys;
            wholeNum = (wholeNum - temp) / outputNumSys;
            if (temp > 9)
                resultNum.append((char) (55 + temp));
            else
                resultNum.append(temp);
        }

        resultNum.append(wholeNum);
        if (sign) {
            resultNum.append("-");
        }
        resultNum.reverse();
        resultNum.append(".");

        /**
         * convert whole number from 10 numeral system in outputSystem
         */

        StringBuilder resultNumFracPart = new StringBuilder("");
        ArrayList<Double> list = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        num = Double.parseDouble(decimalFormat.format(num).replace(",", "."));

        while (num > 0) {
            num = Double.parseDouble(decimalFormat.format(num).replace(",", "."));
            num = num * outputNumSys;
            if (!list.contains(num)) {
                int primeNum = (int) Math.floor(num);
                list.add(num);
                num = num - primeNum;
                if (primeNum > 9)
                    resultNumFracPart.append((char) (55 + primeNum));
                else
                    resultNumFracPart.append(primeNum);
            } else {

                /**
                 * if we found period, program write period in (...)
                 */

                resultNumFracPart.append("(");
                for (int i = list.indexOf(num); i < list.size(); i++) {
                    int res = (int) Math.floor(list.get(i));
                    if (res > 9)
                        resultNumFracPart.append((char) (55 + res));
                    else
                        resultNumFracPart.append(res);
                }
                resultNumFracPart.append(")");
                break;
            }
        }
        if(resultNumFracPart.toString().equals(""))
            resultNumFracPart.append("0");
        resultNum.append(resultNumFracPart);

        return resultNum.toString();
    }
}
