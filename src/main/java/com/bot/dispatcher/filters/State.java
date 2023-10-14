package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;

public class State implements Filter<Event<?>>{
    
    private final String state;

    public State(){
        this.state = "";
    }

    public State(String state){
        this.state = state;
    }

    @Override
    public boolean call(Event<?> event){
        Object obj = null;
        try{
            obj = event.getData("state").get();
        }
        catch (Exception e){}
        if(obj == null) return false;
        if(obj instanceof String state){
            return this.state.equals(state);
        }
        return false;
    }
}
