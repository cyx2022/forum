package com.cyx.main.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringArrayToString {
    public static String transform(String[] arr,String separator){
        if(arr==null||arr.length==0)return "";
        String res = Arrays.stream(arr).collect(Collectors.joining(separator));
        return res;
    }
}
