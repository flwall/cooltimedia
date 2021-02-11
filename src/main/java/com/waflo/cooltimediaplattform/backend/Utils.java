package com.waflo.cooltimediaplattform.backend;

public class Utils {

    public static String toValidFileName(String title) {
        return title.replaceAll("\\W+", "-");

    }
}
