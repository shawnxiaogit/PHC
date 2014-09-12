package com.mobilercn.util;

import java.text.NumberFormat;

public class NumberUtils
{
 
    private static final NumberUtils numberUtils = new NumberUtils();
 
    public NumberUtils()
    {
    }
 
    public static NumberUtils getInstance()
    {
        return numberUtils;
    }
 
    public static String format(double d, int places)
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(places);
        nf.setMaximumFractionDigits(places);
        return nf.format(d);
    }
 
    public static String format(Double d, int places)
    {
        return format(d.doubleValue(), places);
    }
 
    public static String format(String d, int places)
    {
        return format(Double.parseDouble(d), places);
    }
 
    public static String getPrecent(float a, float b, int cnt)
    {
        String Precent = "";
        if(b == 0.0F)
        {
            Precent = "0";
            Precent = Precent + ".";
            Precent = Precent + "0";
            Precent = Precent + "%";
            return Precent;
        }
        if(a == b)
        {
            Precent = "100";
            Precent = Precent + "%";
            return Precent;
        }
        float result = (a * 100F) / b;
        String vresult = String.valueOf(result);
        int nn = vresult.indexOf(".");
        if(vresult.length() >= nn + cnt + 1)
        {
            Precent = vresult.substring(0, nn + cnt + 1);
            getInstance();
            Precent = format(Precent, cnt);
        } else
        {
            Precent = vresult;
            String pointStr = Precent.substring(Precent.lastIndexOf(".") + 1, Precent.length());
            for(int i = 0; i < cnt - pointStr.length(); i++)
                Precent = Precent + 0;
 
        }
        Precent = Precent + "%";
        return Precent;
    }
    
    
    
    public static String getPrecentPoint(float a, float b, int cnt)
    {
        String Precent = "";
        if(b == 0.0F)
        {
            Precent = "0";
            Precent = Precent + ".";
            Precent = Precent + "0";
            Precent = Precent + "0";
            return Precent;
        }
        if(a == b)
        {
            Precent = "100";
            Precent = Precent + ".";
            Precent = Precent + "0";
            Precent = Precent + "0";
            return Precent;
        }
        float result = (a * 1F) / b;
        String vresult = String.valueOf(result);
        int nn = vresult.indexOf(".");
        if(vresult.length() >= nn + cnt + 1)
        {
            Precent = vresult.substring(0, nn + cnt + 1);
            getInstance();
            Precent = format(Precent, cnt);
        } else
        {
            Precent = vresult;
            String pointStr = Precent.substring(Precent.lastIndexOf(".") + 1, Precent.length());
            for(int i = 0; i < cnt - pointStr.length(); i++)
                Precent = Precent + 0;
 
        }
        return Precent;
    }
    
    
 
}
