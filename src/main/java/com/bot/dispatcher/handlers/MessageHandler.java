package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageHandler extends BaseHandler<Message> {
    public MessageHandler(Filter<Message> filter, Callback<Message> callback){
        super(filter, callback);
    }
    @Override
    public boolean call(Event event) {
        if(event instanceof BotEvent botEvent) {
            if (botEvent.event instanceof Message message) {
                if (botEvent.userId == -1) {
                    botEvent.userId = message.getFrom().getId();
                    botEvent.state = botEvent.getData("state");
                }
                if (filter.call(event)) {
                    callback.call(event);
                    return true;
                }
            }
        }
        return false;
    }
}
