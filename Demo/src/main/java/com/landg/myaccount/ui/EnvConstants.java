package com.landg.myaccount.ui;

import java.util.Properties;

public enum EnvConstants {
    DATASOURCE,
    USERTABLE,
    JDBC,
    USERNAME,
    PASSWORD;


    private static final String PATH  = "/master.properties";


    private static Properties properties;

    private String value;
    private String env;

    private void init(String env) {
        if (properties == null) {
            properties = new Properties();
            System.out.println(EnvConstants.class.getResource(PATH));
            try {
                properties.load(EnvConstants.class.getResourceAsStream(PATH));
            }
            catch (Exception e) {
                System.out.println("Unable to load " + PATH + " file from classpath."+e);
                System.exit(1);
            }
        }
        this.env = env;
        value = (String) properties.get(env+"_"+this.toString());
    }

    public String getValue(String env) {
        if (value == null){
            init(env);
        }
        else if (value != null && this.env != env){
            init(env);
        }

        return value;
    }
}
