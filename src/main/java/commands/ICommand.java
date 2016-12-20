package commands;

import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Created by Oscar on 11/28/2016.
 */
public interface ICommand {
    void execute(GenericMessageEvent event);
}
