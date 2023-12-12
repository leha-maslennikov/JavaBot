package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CallbackQueryHandler extends BaseHandler<CallbackQuery> {
    public CallbackQueryHandler(Filter<CallbackQuery> filter, Callback<CallbackQuery> callback) {
        super(filter, callback);
    }
    @Override
    public boolean call(Event event) {
        if(event instanceof BotEvent botEvent) {
            if (botEvent.event instanceof CallbackQuery callbackQuery) {
                if (botEvent.userId == -1) {
                    botEvent.userId = callbackQuery.getFrom().getId();
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
