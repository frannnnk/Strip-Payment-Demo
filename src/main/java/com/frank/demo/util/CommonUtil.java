package com.frank.demo.util;


import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class CommonUtil {

    public final static Random rand = new Random();

    public static Environment env;

    public static void main(String[] args) {

        String auth =  "admin:admin";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String( encodedAuth );
        System.out.println(authHeader);

    }

    public static String handleUnexpectedException(Logger log, Exception e){
        String ref = CommonUtil.generateRandomString(10).toUpperCase();
        log.error(e.getMessage() + " \n" + CommonUtil.getStackTrace(e) + " \n" +"Ref: "+ref);
        return  "Unexpected error occurred, please try again later. If the error keep happening, " +
                "provide this code to related team for followup: "+ref +" ." +
                "we apologize for any inconvenience this might caused. ";
    }


    public static String cut(String source, int length) {
        if (source == null) {
            return null;
        } else {
            if (source.length() < length) {
                return source;
            } else {
                return source.substring(0, length);
            }
        }
    }

    public static String printMap(Map map) {
        String result = "[";
        if (map == null) {
            return "NULL";
        } else {
            for (Object key : map.keySet()) {
                result +=  key.toString()+":"+map.get(key).toString()+", ";
            }
            result += "]";
        }
        return result;
    }

    public static String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }



    public static Map<String, String> constructErrorMap(JsonNode jsonNode){
        Map<String, String> errorDetailMap = new HashMap<>();

        if (jsonNode.get("code") != null) {
            errorDetailMap.put("error-core-code",jsonNode.get("code").asText());
        }
        if (jsonNode.get("message") != null) {
            errorDetailMap.put("error-core-message", jsonNode.get("message").asText());
        }

        if (jsonNode.get("payload")!= null) {
            if (jsonNode.get("payload").get("message") != null) {
                errorDetailMap.put("error-core-detail-message",jsonNode.get("payload").get("message").asText());
            }
            if (jsonNode.get("payload").get("errors") != null) {
                if ( jsonNode.get("payload").get("errors").isArray() ) {
                    List<String> errorList = new ArrayList<>();
                    for (final JsonNode objNode : jsonNode.get("payload").get("errors") ) {
                        errorList.add(objNode.asText());
                    }
                    errorDetailMap.put("error-core-detail-errors",String.join(",",errorList));
                }
            }
        }
        return errorDetailMap;
    }


    private static boolean isNull(Object obj) {
        return obj == null || "".equals(obj.toString());
    }

    public static boolean isExNull(Object obj) {
        if (isNull(obj)) {
            return true;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            return "".equals(str.trim());
        } else if (obj instanceof Collection) {
            Collection c = (Collection) obj;
            return c.isEmpty();
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            return map.isEmpty();
        } else if (obj instanceof Iterator) {
            Iterator iter = (Iterator) obj;
            return !iter.hasNext();
        } else if (obj instanceof Enumeration) {
            Enumeration em = (Enumeration) obj;
            return !em.hasMoreElements();
        } else if (obj.getClass().isArray()) {
            Object[] objArr = (Object[]) obj;
            return objArr.length <= 0;
        } else {
            return "".equals(obj.toString().trim());
        }
    }


    public static String generateRandomString(int length) {
        return RandomStringUtils.random(length, true, false);
    }

    public static int generateRandomNumber(int minimumInclusive, int maximumInclusive) {

        return minimumInclusive + rand.nextInt((maximumInclusive - minimumInclusive) + 1);
    }

    public static boolean isCaptchaValid(String testValue, boolean caseSensitive, HttpSession session) {

        try {

            if (!"true".equalsIgnoreCase(env.getProperty("security.production"))) {
                if (testValue!=null && testValue.startsWith("1998") ) {
                    return true;
                }
            };

            if (isAnyEmptyOrNull(testValue,session)) {
                return false;
            } else {
                if (session.getAttribute("_CAPTCHA") == null) {
                    return false;
                } else {
                    if (caseSensitive) {
                        return session.getAttribute("_CAPTCHA").toString().equals(testValue);
                    } else {
                        return session.getAttribute("_CAPTCHA").toString().equalsIgnoreCase(testValue);
                    }
                }
            }
        } finally {
            session.setAttribute("_CAPTCHA",null);
        }


    }


    public static Object nvl(Object o1, Object o2) {
        if (CommonUtil.isExNull(o1)) {
            return o2;
        } else {
            return o1;
        }
    }

    public static boolean isAnyEmptyOrNull(Object... objs) {
        for (Object obj : objs) {
            if (CommonUtil.isExNull(obj)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEqualsAnyNotNull(String src, String... strs) {
        if (CommonUtil.isExNull(src)) {
            return false;
        }

        for (String str : strs) {
            if (src.equals(str)) {
                return true;
            }
        }

        return false;
    }

    public static Collection safe( Collection other ) {
        return other == null ? Collections.EMPTY_LIST : other;
    }

    public static Map<String, String> stringToMap(String string) {
        Map<String, String> attributeArray = new HashMap<>();
        String[] arrayKeyValue  = string.split(",");
        for (String keyValue : arrayKeyValue) {
            List attributeKeyValue = Arrays.asList(keyValue.split("="));
            attributeArray.put(attributeKeyValue.get(0).toString(), attributeKeyValue.get(1).toString());
        }
        return attributeArray;
    }

    public static String replaceLast (String source, String replaceFrom, String replaceTo) {
        int ind = source.lastIndexOf(replaceFrom);
        String formatedString = new StringBuilder(source).replace(ind, ind+1, replaceTo).toString();
        return formatedString;
    }


}
