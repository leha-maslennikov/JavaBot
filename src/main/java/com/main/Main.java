package com.main;

import com.bot.Bot;
import com.bot.dispatcher.Dispatcher;
import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Command;
import com.bot.dispatcher.filters.Filter;
import com.bot.dispatcher.filters.State;
import com.bot.dispatcher.handlers.CallbackQueryHandler;
import com.bot.dispatcher.handlers.MessageHandler;
import com.gameprocessor.Box;
import com.gameprocessor.Equipment;
import com.gameprocessor.GameProcessor;
import com.gameprocessor.Sendable;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.LinkedList;
import java.util.List;

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
                        new FilterWithDelete(new Command("start")),
                        (event)->{
                            event.getData("game").thenAccept(
                                    (object)->{
                                        if(object instanceof TelegramGameProcessor tgp){
                                            tgp.start();
                                        }
                                        else{
                                            var tgp = new TelegramGameProcessor(bot, event.event.getMessage().getChatId(), event.userId);
                                            event.setData("game", tgp);
                                            tgp.start();
                                        }
                                    }
                            );
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new FilterWithDelete(new Command("inspect")),
                        (event)->{
                            event.getData("game").thenAccept(
                                    (object)->{
                                        if(object instanceof TelegramGameProcessor tgp){
                                            tgp.inspect();
                                        }
                                    }
                            );
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new Command("data"),
                        (event)->{
                            event.getData("game").thenAccept(
                                    (object)->{
                                        if(object instanceof TelegramGameProcessor tgp){
                                            tgp.data();
                                        }
                                    }
                            );
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new Command("help"),
                        (event)->{
                            event.getData("game").thenAccept(
                                    (object)->{
                                        if(object instanceof TelegramGameProcessor tgp){
                                            tgp.help();
                                        }
                                    }
                            );
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new FilterWithDelete(new Command("retry")),
                        (event)->{
                            event.getData("game").thenAccept(
                                    (object)->{
                                        if(object instanceof TelegramGameProcessor tgp){
                                            tgp.retry();
                                        }
                                    }
                            );
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new FilterWithDelete(new Command("bag")),
                        (event)->{
                            event.getData("game").thenAccept(
                                    (object)->{
                                        if(object instanceof TelegramGameProcessor tgp){
                                            tgp.bag();
                                        }
                                    }
                            );
                        }
                )
        );

        dp.addHandler(
                new CallbackQueryHandler(
                        new FilterWithDelete(new State("send")),
                        event -> {
                            event.getData("game").thenAccept(
                                    object -> {
                                        if(object instanceof TelegramGameProcessor tgp){
                                            tgp.callback(event.event.getCallbackQuery().getData());
                                        }
                                    }
                            );
                            try {
                                event.bot.executeAsync(
                                        AnswerCallbackQuery.builder()
                                                .callbackQueryId(
                                                        event.event.getCallbackQuery().getId()
                                                )
                                                .build()
                                );
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
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

class TelegramGameProcessor extends GameProcessor {
    private final Bot bot;
    private final long chatId;
    private final long userId;
    private List<? extends Sendable> data;
    private Sendable chosen;
    public TelegramGameProcessor(Bot bot, long chatId, long userId){
        this.bot = bot;
        this.chatId = chatId;
        this.userId = userId;
    }
    /**
     * Отправляет текстовое сообщение пользователю
     *
     * @param text текст, для пользователя
     */
    @Override
    public void send(String text) {
        try {
            bot.executeAsync(
                    SendMessage.builder().chatId(this.chatId).text(text).build()
            );
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Отправляет объекты пользователю, с текстом из getShortText,
     * если пользователь выбрал объект, отправляется getLongText
     * и действия из getActions
     *
     * @param text текст, для пользователя
     * @param obj  объекты, который будет отправлен пользователю
     */
    @Override
    public void send(String text, List<? extends Sendable> obj) {
        try {
            //bot.setUserData(userId, "sendable", obj);
            data = obj;
            bot.setUserData(userId, "state", "send");
            Integer n = 0;
            List<List<InlineKeyboardButton>> kb = new LinkedList<>();
            for(Sendable i: obj){
                List<InlineKeyboardButton> row = new LinkedList<>();
                row.add(
                        InlineKeyboardButton.builder()
                                .text(i.getShortText())
                                .callbackData(n.toString())
                                .build()
                        );
                kb.add(row);
                n++;
            }
            bot.executeAsync(
                    SendMessage.builder().chatId(this.chatId).text(text)
                            .replyMarkup(
                                    InlineKeyboardMarkup.builder()
                                            .keyboard(kb)
                                            .build()
                            ).build()
            );
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void callback(String callbackData) {
        if(callbackData.equals("loot") && chosen instanceof Box box){
            loot(box);
            return;
        }
        if(callbackData.equals("equip") && chosen instanceof Equipment equipment){
            equip(equipment);
            return;
        }
        if(callbackData.equals("unequip") && chosen instanceof Equipment equipment){
            unequip(equipment);
            return;
        }
        try {
            Integer n = Integer.parseInt(callbackData);
            List<List<InlineKeyboardButton>> kb = new LinkedList<>();
            Sendable obj = data.get(n);
            chosen = obj;
            for(String i: obj.getActions().getActions()){
                List<InlineKeyboardButton> row = new LinkedList<>();
                row.add(
                        InlineKeyboardButton.builder()
                                .text(i)
                                .callbackData(i)
                                .build()
                );
                kb.add(row);
            }
            bot.executeAsync(
                    SendMessage.builder().chatId(this.chatId).text(obj.getLongText())
                            .replyMarkup(
                                    InlineKeyboardMarkup.builder()
                                            .keyboard(kb)
                                            .build()
                            ).build()
            );

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}