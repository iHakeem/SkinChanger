package com.bydeadbeaf.pronick.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDHelper {


    public String addCharToString(String str, char c, int pos) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(pos, c);
        return stringBuilder.toString();
    }

    // this is a manual way to format uuid I am sure there is better ways
    public String formatUUID(String id){
        String uuid = addCharToString(id , '-' , 8);
        uuid = addCharToString(uuid , '-',13);
        uuid = addCharToString(uuid , '-',18);
        uuid = addCharToString(uuid , '-',23);
        return uuid;
    }

    public String getUUID(String body){
        final Pattern pattern = Pattern.compile("id\":\"(.*?)\"}");
        final Matcher matcher = pattern.matcher(body);
        matcher.find();
        String id = matcher.group(1);
        return formatUUID(id);
    }
}
