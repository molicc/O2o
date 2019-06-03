package com.imooc.o2o;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        String seperator = System.getProperty(File.separator);
        System.out.println(seperator);
        System.out.println(os.substring(0,3));
    }
}
