package com.waflo.cooltimediaplattform.backend;

public class Utils {

    public static String toValidFileName(String title) {
        return title.replaceAll("\\W+", "-");

    }
    public static String generateTempPublicId(String fn){
        int rand=(int)(Math.random()*100);
        return "tmp/"+rand+"-"+fn;
    }
}
