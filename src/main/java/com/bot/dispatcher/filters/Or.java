package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

public class Or<T> implements Filter<T> {

    private final Filter<T>[] filters;

    public Or(Filter<T> ... filters){
        this.filters = filters;
    }

    public boolean call(T event){
        for(Filter<T> filter: filters){
            if(filter.call(event)){
                return true;
            }
        }
        return false;
    }
}
