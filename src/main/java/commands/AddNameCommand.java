package commands;

import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Oscar on 11/28/2016.
 */
public class AddNameCommand implements ICommand {

    private static final Logger LOGGER= LoggerFactory.getLogger(AddNameCommand.class);
    public AddNameCommand(){
    }

    public void execute(GenericMessageEvent event) {
        LOGGER.info("Added "+event.getUser().getNick()+" to the queue");
    }
}
