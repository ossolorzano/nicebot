package commands;

import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar on 11/28/2016.
 */
public class AddNameCommand implements ICommand {

    private static final Logger LOGGER= LoggerFactory.getLogger(AddNameCommand.class);


    public AddNameCommand(){
    }

    public void execute(GenericMessageEvent event) {
        List<Object> data1 = new ArrayList<>();
        data1.add(event.getUser().getNick());
        List<List<Object>> data = new ArrayList<>();
        data.add(data1);
    }
}
