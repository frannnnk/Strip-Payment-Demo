package com.frank.demo.util;

public interface Translator<T, U> {

    public static final String LANGUAGE_ZH_HK = "tc";
    public static final String LANGUAGE_ZH_CN = "sc";
    public static final String LANGUAGE_EN = "en";

    public U translateTo(T objectToBeTranslated, String targetlanguage);
}
