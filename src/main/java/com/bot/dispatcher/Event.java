package com.bot.dispatcher;

import com.bot.Bot;
import com.fasterxml.jackson.databind.deser.std.MapEntryDeserializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Event<T> {
    public final T event;
    public Event(T event) {
        this.event = event;
    }
}
