package com.main;

import com.bot.Bot;
import com.bot.dispatcher.Dispatcher;
import com.bot.dispatcher.filters.Command;
import com.bot.dispatcher.filters.State;
import com.bot.dispatcher.handlers.MessageHandler;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static final Bot bot = new Bot(
            options(),
            "5787246678:AAFXIoyuSRUw95D4a56XVZaY8TSXZl-NHDM",
            "RPG_Bot",
            createDispatcher()
    );

    public static void main(String[] args){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static Dispatcher createDispatcher(){
        Dispatcher dp = new Dispatcher();
        dp.addHandler(
                new MessageHandler(
                        new Command("start"),
                        event -> {
                            System.out.println("start"+event.event.getText());
                        }
                )
        );


        dp.addHandler(
                new MessageHandler(
                        event -> {
                            return true;
                        },
                        event -> {
                            System.out.println(event.event.getText());
                        }
                )
        );

        return dp;
    }

    public static DefaultBotOptions options(){
        DefaultBotOptions options = new DefaultBotOptions();
        options.setMaxThreads(2);
        return options;
    }
}

