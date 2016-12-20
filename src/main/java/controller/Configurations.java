package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Oscar on 12/19/2016.
 */
public class Configurations {
    private static Properties properties = new Properties();
    private static final Logger LOGGER= LoggerFactory.getLogger(Configurations.class);

    static{
        String path = "."+ File.separator+"config.properties";
        try {
            FileInputStream file = new FileInputStream(path);
            properties.load(file);
            file.close();
        }catch(FileNotFoundException e){
            LOGGER.error("Properties File Not Found!", e);
            System.exit(0);
        }catch(IOException e){
            LOGGER.error("Properties Load Error!", e);
            System.exit(0);
        }
    }

    public static final String ADD_NAME = getProperty("addName");
    public static final String BOT_NAME = getProperty("botName");
    public static final String SERVER_NAME = getProperty("serverName");
    public static final String OAUTH = getProperty("oauth");
    public static final String CHANNEL_NAME = getProperty("channelName");
    public static final String CAP_HANDLER = getProperty("capHandler");
    public static final String START_POLL = getProperty("startPoll");
    public static final String END_POLL = getProperty("endPoll");

    private static String getProperty(String key){
        if(key!=null && properties.getProperty(key)!=null){
            return properties.getProperty(key);
        }
        LOGGER.info("Property returning empty string!");
        return "";
    }
}
