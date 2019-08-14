package com.atguigu.gulimall.pms.util;

public class StringAppendUtils {

    public static String  stringAppend(String[] images){

        StringBuffer sb = new StringBuffer();

        if(images != null && images.length > 0){


            for (String image : images) {

                sb.append(image);
                sb.append(",");

            }

            return  sb.toString().substring(0,sb.length() - 1);

        }

       return "";

    }

}
