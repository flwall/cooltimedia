package com.waflo.cooltimediaplattform.backend;

public class Utils {

    public static String toValidFileName(String title) {
        return title.replaceAll("\\W+", "-");

    }
    public static String generateTempPublicId(String fn, boolean useFileExt){
        int rand=(int)(Math.random()*100);
        var id= "tmp/"+rand+"-"+fn;
        if(!useFileExt&&id.contains("."))
            id=id.substring(0, id.lastIndexOf("."));
        return id;
    }
}
