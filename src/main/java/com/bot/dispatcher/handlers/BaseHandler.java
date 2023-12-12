package com.bot.dispatcher.handlers;

import com.bot.dispatcher.Callback;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Filter;

public abstract class BaseHandler<T> {
    protected final Filter<T> filter;
    protected final Callback<T> callback;
    public BaseHandler(Filter<T> filter, Callback<T> callback) {
        this.filter = filter;
        this.callback = callback;
    }
    public abstract boolean call(Event event);
}
