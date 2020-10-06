package com.bydeadbeaf.pronick.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDHelper {


    public String addCharToString(String str, char c, int pos) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(pos, c);
        return stringBuilder.toString();
    }

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
        String uuid = formatUUID(id);
        return uuid;
    }
}
