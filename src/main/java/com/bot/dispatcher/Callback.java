package com.bot.dispatcher;

public interface Callback<T> {
    void call(T event);
}
