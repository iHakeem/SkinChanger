package com.bydeadbeaf.pronick.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkinTextureParser {
    public String getSkin(String data){
        final Pattern pattern = Pattern.compile("\"value\" : \"(.*?)\",");
        final Matcher matcher = pattern.matcher(data);
        matcher.find();
        return matcher.group(1);
    }
    public String getSig(String data){
        final Pattern pattern = Pattern.compile("\"signature\" : \"(.*?)\"");
        final Matcher matcher = pattern.matcher(data);
        matcher.find();
        return matcher.group(1);
    }
}
