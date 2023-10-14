package com.bot.dispatcher.filters;

public interface Filter<T> {
    default boolean call(T event){
        return true;
    }
}
