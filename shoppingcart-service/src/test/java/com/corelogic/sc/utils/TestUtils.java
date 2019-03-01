package com.corelogic.sc.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;

public class TestUtils {

    private static JsonParser parser = new JsonParser();


    public static String readFixture(String service, String fixtureName) throws IOException {
        return readFixture(service + "/" + fixtureName);
    }

    public static String readFixture(String fixtureName) throws IOException {
        String result = IOUtils.toString(TestUtils.class.getResourceAsStream("/fixtures/" + fixtureName),
                Charset.defaultCharset());

        System.out.println(result);
        return result;
    }

    public static String readJsonFixture(String fixtureName, Map<String, Object> values) throws IOException {
        Scanner scanner = new Scanner(TestUtils.class.getResourceAsStream("/fixtures/" + fixtureName))
                .useDelimiter("\\A");
        String jsonAsString = scanner.hasNext() ? scanner.next() : "";
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            jsonAsString = jsonAsString.replaceAll("==" + entry.getKey() + "==",
                    entry.getValue() == null ? null : entry.getValue().toString());
        }

        if (null != jsonAsString && jsonAsString.length() > 0) {
            jsonAsString = jsonAsString.trim();
            JsonElement jsonElement = parser.parse(jsonAsString);
            if (jsonAsString.startsWith("[")) {
                return jsonElement.getAsJsonArray().toString();
            } else {
                return jsonElement.getAsJsonObject().toString();
            }
        }
        throw new IOException("Cannot find fixture: " + fixtureName);
    }
}
