package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Event;

public interface BaseHandler{
    boolean call(Event<?> event);
}
