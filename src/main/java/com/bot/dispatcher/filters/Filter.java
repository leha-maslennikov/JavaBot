package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

public interface Filter<T> {
    boolean call(Event<T> event);
}
