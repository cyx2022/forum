package com.cyx.main.constant;

import java.util.ArrayList;
import java.util.List;

public class SensitiveProperties {
    public static List<String> properties=new ArrayList<>();
    static {
        properties.add("name");
        properties.add("title");

        properties.add("content");
        properties.add("tip");
        properties.add("text");




    }
}
