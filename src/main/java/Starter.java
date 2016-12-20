import controller.listener.ChatListener;
import controller.sheets.SheetController;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

/**
 * Created by Oscar on 11/17/2016
 */
public class Starter {

    public static void main(String[] args){
        ChatListener chatListener = new ChatListener();
        PircBotX bot = new PircBotX(chatListener.getConfig());
        try {
            bot.startBot();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
    }
}
