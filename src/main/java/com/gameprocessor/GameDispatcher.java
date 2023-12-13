package com.gameprocessor;

import com.gameprocessor.dispatcher.Dispatcher;
import com.gameprocessor.dispatcher.filters.State;
import com.gameprocessor.dispatcher.handlers.Handler;

public class GameDispatcher {
    public static Dispatcher get() {
        Dispatcher dp = new Dispatcher();
        dp.addHandler(
                new Handler(
                        new State(GameProcessor.CREATE),
                        request -> {

                        }
                )
        );

        return dp;
    }
}
