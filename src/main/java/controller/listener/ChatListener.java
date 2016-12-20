package controller.listener;


import commands.ICommand;
import commands.AddNameCommand;
import controller.Configurations;
import org.pircbotx.Configuration;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Oscar on 11/17/2016.
 */
public class ChatListener extends ListenerAdapter {

    private Configuration config;
    private static final Logger LOGGER=LoggerFactory.getLogger(ChatListener.class);
    private ICommand addNameCommand;
    private Configurations configurations;

    //Strings for commands


    public ChatListener(){
        configurations = new Configurations();
        //Create config
        config=new Configuration.Builder()
                .setAutoNickChange(false)
                .setOnJoinWhoEnabled(false)
                .addCapHandler(new EnableCapHandler(configurations.CAP_HANDLER))
                .setName(configurations.BOT_NAME)
                .addServer(configurations.SERVER_NAME)
                .setServerPassword(configurations.OAUTH)
                .addAutoJoinChannel(configurations.CHANNEL_NAME)
                .addListener(this)
                .buildConfiguration();
        //Init Commands
        initCommands();
    }

    private void initCommands(){
        //CreateCommands
        addNameCommand = new AddNameCommand();
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
        if(event.getMessage().equals(Configurations.ADD_NAME))
                return addNameCommand;
        return null;
    }
}
