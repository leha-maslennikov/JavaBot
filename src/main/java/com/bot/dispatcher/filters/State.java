package com.bot.dispatcher.filters;

import com.bot.dispatcher.Event;
import com.bot.dispatcher.handlers.BotEvent;

public class State implements Filter {
    
    private final String state;

    public State(){
        this.state = "";
    }

    public State(String state){
        this.state = state;
    }

    @Override
    public boolean call(Event event){
        if(event instanceof BotEvent botEvent) {
            if (botEvent.state == null) return false;
            try {
                Object obj = botEvent.state.get();
                if (obj == null) obj = "";
                if (obj instanceof String state) {
                    return this.state.equals(state);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
