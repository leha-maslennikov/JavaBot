package com.bot.dispatcher.handlers;

import com.bot.Bot;
import com.bot.dispatcher.Event;

import java.util.concurrent.CompletableFuture;

public class BotEvent<T> extends Event<T> {
    public final Bot bot;
    public long userId;
    public CompletableFuture<Object> state;

    public BotEvent(Bot bot, T event, long userId) {
        super(event);
        this.bot = bot;
        this.userId = userId;
        this.state = null;
    }

    public BotEvent(Bot bot, T event) {
        this(bot, event, -1);
    }

    /**
     * @param key    название ключа для данных
     * @param value  данные
     * @return возвращает передаваемые данные
     */
    public CompletableFuture<Object> setData(String key, Object value){
        return bot.setUserData(this.userId, key, value);
    }

    /**
     * @param key название ключа для данных
     * @return возвращает данные или null
     */
    public CompletableFuture<Object> getData(String key){
        return bot.getUserData(this.userId, key);
    }

    /**
     * Сбрасывает все данные пользователя
     * @return CompletableFuture<Void>
     */
    public CompletableFuture<Void> clear(){
        return bot.clearUserData(this.userId);
    }
}
