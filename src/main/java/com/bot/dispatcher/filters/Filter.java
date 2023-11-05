package com.bot.dispatcher.filters;

public interface Filter<T> {
    boolean call(T event);
}
