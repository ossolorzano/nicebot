package controller.listener;


import commands.ICommand;
import commands.WriteNameCommand;
import controller.sheets.SheetController;
import org.pircbotx.Configuration;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Oscar on 11/17/2016.
 */
public class ChatListener extends ListenerAdapter {

    private Configuration config;
    private String botName, serverName, oauth, channelName, capHandler, startPoll;
    private static final Logger LOGGER=LoggerFactory.getLogger(ChatListener.class);
    private ICommand writeNameCommand;
    private SheetController sheetController;

    public ChatListener(){
        //Init string values from properties
        initConfigSettings();
        //Create config
        config=new Configuration.Builder()
                .setAutoNickChange(false)
                .setOnJoinWhoEnabled(false)
                .addCapHandler(new EnableCapHandler(capHandler))
                .setName(botName)
                .addServer(serverName)
                .setServerPassword(oauth)
                .addAutoJoinChannel(channelName)
                .addListener(this)
                .buildConfiguration();
        sheetController = new SheetController();
        //Init Commands
        initCommands();
    }

    private void initConfigSettings(){
        //Setup Properties file
        Properties properties = new Properties();
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
        //Get String values from properties file
        botName = properties.getProperty("botName");
        serverName = properties.getProperty("serverName");
        oauth = properties.getProperty("oauth");
        channelName = properties.getProperty("channelName");
        capHandler = properties.getProperty("capHandler");

        startPoll = properties.getProperty("startPoll");
    }

    private void initCommands(){
        //CreateCommands
        writeNameCommand = new WriteNameCommand(sheetController.getService(),sheetController.getSpreadsheetId());
    }

    public Configuration getConfig(){
        return config;
    }

    @Override
    public void onGenericMessage(GenericMessageEvent event){
        ICommand command = handleInput(event);
        if(command!=null) {
            command.execute(event);
        }
    }

    private ICommand handleInput(GenericMessageEvent event){
        switch(event.getMessage()){
            case "ayy":
                writeNameCommand.execute(event);
            default:
                return null;
        }
    }
}
