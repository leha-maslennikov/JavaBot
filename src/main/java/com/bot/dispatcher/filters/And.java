package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

public class And<T> implements Filter<T> {

    private final Filter<T>[] filters;

    public And(Filter<T> ... filters){
        this.filters = filters;
    }

    @Override
    public boolean call(T event){
        for(Filter<T> filter: filters){
            if(!filter.call(event)){
                return false;
            }
        }
        return true;
    }
}
