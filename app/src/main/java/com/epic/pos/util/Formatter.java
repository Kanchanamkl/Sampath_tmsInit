package com.epic.pos.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by harshana_m on 11/7/2018.
 */

public class Formatter {
    public static String removeDecimalPlace(String value) {
        int decimalIndex = value.indexOf(".");

        if (decimalIndex > 0) {
            //re arrange the amount to be converted
            String before = value.substring(0, decimalIndex);
            String after = value.substring(decimalIndex + 1, value.length());

            value = before + after;
        }
        return value;
    }

    public static String fixedLengthString(String string, int length) {
        return String.format("%1$"+length+ "s", string);
    }

    public static String formatAmount(long amount, String curSymbol) {
        String amt = Long.toString(amount);
        int len = amt.length();

        String front = "",back = "";

        if (len > 2) {
            back  = amt.substring(len - 2, len);
            front = amt.substring(0,len - 2);

            amt = front + "." + back;
        }
        else {
            if (len == 1) {
                amt = "0.0" + amt;
            }
            else {
                amt = "0." + amt;
            }
        }

        amt = amt + " " + curSymbol;

        return amt;
    }

    public static String formatLocalAmount(long amount, String curSymbol) {  //Added by Indeevari for DCC
        String amt = Long.toString(amount);
        String finalAmt;
        int len = amt.length();

        String front = "",back = "";

        if (len > 2) {
            back  = amt.substring(len - 2, len);
            front = amt.substring(0,len - 2);

            amt = front + "." + back;
        }
        else {
            if (len == 1) {
                amt = "0.0" + amt;
            }
            else {
                amt = "0." + amt;
            }
        }

        finalAmt = curSymbol + "     " + amt;

        return finalAmt;
    }

    public static String maskString(String pan, String pattern, char maskChar) {
        String formatted = "";
        char toAdd = 0;

        for (int i = 0; i < pan.length(); i++) {
            if (i < pattern.length() && pattern.charAt(i) == maskChar)
                toAdd = maskChar;
            else
                toAdd = pan.charAt(i);

            formatted += toAdd;

            if (i >= pan.length())
                break;
        }
        return formatted;
    }

    public static String formatForSixDigits(int invoiceNumber) {
        String inv = Integer.toString(invoiceNumber);
        int padLen = 6 - inv.length();
        String front = "";

        for (int i = 0 ; i < padLen; i++)
          front += "0";

        return front + inv;
    }

    public static String formatForGivenLength(String str, int length, String padwith, boolean padRight) {
        int padLen = length - str.length();
        String front = "";

        for (int i = 0 ; i < padLen; i++)
            front += padwith;

        if(padRight) {
            return front + str;
        }
        else {
            return str + front;
        }
    }

    public static String getCurrentTimeFormatted() {
        SimpleDateFormat dt = new SimpleDateFormat("HHmmss");
        String currentTime = dt.format(new Date());
        return currentTime;
    }

    public static String getCurrentDateFormatted() {
        SimpleDateFormat dt = new SimpleDateFormat("yyMMdd");
        String currentDate = dt.format(new Date());
        currentDate = currentDate.substring(2,currentDate.length());
        return currentDate;
    }

    public static String getDateFormattedForReceipt(String date) {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        String Date = year + "/" + date.substring(0,2) + "/" + date.substring(2,4);
        return Date;
    }

    public static String getTimeFormattedForReceipt(String time) {
        String Time = time.substring(0,2) + ":" + time.substring(2,4) + ":" + time.substring(4,6);
        return Time;
    }

    public static String getCountFormattedForSettlement(int count) {
        String strCount = Integer.toString(count);
        String pad = "";

        int lenGap = 3 - strCount.length();
        for (int i = 0 ; i < lenGap; i++)
            pad += "0";

        return pad + strCount;
    }

    public static String getAmountFormattedForSettlement(long amount) {
        String strAmount = Long.toString(amount);
        String pad = "";

        int lenGap = 12 - strAmount.length();
        for (int i = 0 ; i < lenGap; i++)
            pad += "0";

        return pad + strAmount;
    }

    public static String fillInBack(String c, String data , int expecteLen) {
        int fillLen = expecteLen - data.length();
        String filler = "";

        for (int i = 0; i < fillLen; i++)
            filler += c;

        return data +filler;
    }

    public static String fillInFront(String c, String data , int expecteLen) {
        int fillLen = expecteLen - data.length();
        String filler = "";

        for (int i = 0; i < fillLen; i++)
            filler += c;

        return filler + data ;
    }

    public static String replaceNII(String tpdu, String newNII) {
        String start = tpdu.substring(0,3);
        String last = tpdu.substring(tpdu.length() - 4,tpdu.length());

        return (start + newNII + last);
    }
}