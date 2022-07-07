package com.frank.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryStringParser {

    private final String queryString;

    private Map<String, List<String>> queryParameters;

    private boolean anyFragment;
    private String fragment;

    private QueryStringParser() {
        this("");
    }

    public QueryStringParser(String queryString) {
        String trimmed = queryString == null ? "" : queryString.trim();
        this.queryString = trimmed;
        parse();
    }

    public Map<String, List<String>> getQueryParameters() {
        return queryParameters;
    }

    public String getQueryParameter(String key) {

        if (!queryParameters.containsKey(key)) {
            return null;
        }

        List<String> values = queryParameters.get(key);
        return handleDuplicatedKeyValues(values);
    }

    public QueryStringParser addQueryParameter(String key, String value) {

        if (value == null) {
            return this;
        }

        List<String> values = queryParameters.getOrDefault(key, new ArrayList<>());
        values.add(value);

        queryParameters.put(key, values);

        return this;
    }

    public QueryStringParser removeQueryParameter(String key) {
        List<String> remove = queryParameters.remove(key);
        return this;
    }

    private void parse() {
        String[] tokens = queryString.split("#");

        this.anyFragment = tokens.length > 1;
        this.queryParameters = parseQuery(tokens[0]);
        this.fragment = anyFragment ? tokens[1] : "";

        return;
    }

    private Map<String, List<String>> parseQuery(String query) {
        Map<String, List<String>> result = new HashMap<>();

        if (query.isEmpty()) {
            return result;
        }

        String[] tokens = query.split("&");

        for (String token : tokens) {
            String[] kvp = token.split("=");

            String key = kvp[0];
            String val = kvp.length < 2 ? "" : kvp[1]; // handle the query string key without value

            List<String> currentValue = result.getOrDefault(key, new ArrayList<>());
            boolean isAdded = currentValue.add(val);

            result.put(key, currentValue);
        }

        return result;
    }

    public String getQueryString() {
        List<String> parameters = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : queryParameters.entrySet()) {
            parameters.add(entry.getKey() + "=" + handleDuplicatedKeyValues(entry.getValue()));
        }

        String query = String.join("&", parameters);
        return anyFragment ? query + "#" + fragment : query;
    }

    public String handleDuplicatedKeyValues(List<String> values) {
        return String.join(",", values);
    }
}
