package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackQueryHandler implements BaseHandler{
    private final Filter<Event<CallbackQuery>> filter;
    private final Callback<Event<CallbackQuery>> callback;
    public CallbackQueryHandler(Filter<Event<CallbackQuery>> filter, Callback<Event<CallbackQuery>> callback){
        this.filter = filter;
        this.callback = callback;
    }
    @Override
    public boolean call(Event<?> event){
        if(event.event instanceof CallbackQuery callbackQuery){
            var e = new Event<>(event.bot, callbackQuery, callbackQuery.getFrom().getId());
            if(filter.call(e)){
                callback.call(e);
                return true;
            }
        }
        return false;
    }
}
