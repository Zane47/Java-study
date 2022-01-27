package com.example.i18n;

import java.io.FileInputStream;

import java.net.URLDecoder;
import java.util.Properties;

public class Application {
    public static void main(String[] args) {
        Application.say();
    }

    public static void say() {
        Properties properties = new Properties();
        String configPath = Application.class.getResource("/config.properties").getPath();
        try {
            configPath = URLDecoder.decode(configPath, "UTF-8");
            properties.load(new FileInputStream(configPath));

            // 国际化实现类
            String language = properties.getProperty("language");

            I18N i18n = (I18N) Class.forName(language).newInstance();
            System.out.println(i18n.say());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


