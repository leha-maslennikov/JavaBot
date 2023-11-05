package com.main;

import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Command;
import com.bot.dispatcher.filters.Filter;
import com.bot.dispatcher.handlers.CallbackQueryHandler;
import com.bot.dispatcher.handlers.MessageHandler;
import com.game.*;

import com.bot.Bot;
import com.bot.dispatcher.Dispatcher;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

//    public static void Battle(Enemy enemy,Character character )
//    {
//        Scanner console = new Scanner(System.in);
//        System.out.println("?????????? "+enemy.name);
//        System.out.println("/Attack - ?????????, /Wait - ?????");
//        while (character.getHealth()!=0&&enemy.getHealth()!=0)
//        {
//            System.out.println("???????? = "+character.getHealth());
//            String request=console.nextLine();
//            int damage = character.getDamage()+character.weapon.damage;
//            while(true)
//            {
//                if(request.equals("/Wait"))
//                {
//                    break;
//                }
//                else if (request.equals("/Attack"))
//                {
//                    System.out.println("?? ????????");
//                    System.out.println(enemy.name +" ???????? "+ damage +" ?????");
//                    enemy.getHit(damage);
//                    break;
//                }
//            }
//            if(enemy.getHealth()!=0)
//            {
//                System.out.println(enemy.name + " ???????");
//                System.out.println("?? ????????? "+enemy.getDamage()+" ?????");
//                character.getHit(enemy.getDamage());
//            }
//
//        }
//        if(character.getHealth()!=0)
//        {
//            System.out.println(enemy.name+ " ?????????!");
//        }
//
//    }




//    public static void main() {
//        while(true)
//        {
//            System.out.println("????? ?????????? ? ???? ????????? ???");
//            System.out.println("???? ??????: ????????? ?? ??????????");
//            System.out.println("/Data - ?????????? ?????????? ? ?????????");
//            System.out.println("/Interact - ?????????????? ? ?????????? ? ???????");
//            System.out.println("/Equip - ??????????? ??????? ?? ?????????");
//            System.out.println("/Inventory - ??????? ?????????");
//            System.out.println("/Retry - ?????? ??????");
//            System.out.println("??????? /Start, ????? ??????");
//            RoomManager roomManager = new RoomManager();
//
//            Weapon woodenSword= new Weapon();
//            woodenSword.damage=5;
//            woodenSword.name="?????????? ???";
//
//            roomManager.chests[0][0].item=woodenSword;
//            roomManager.chests[0][1].isEmpty=true;
//
//
//
//
//            Character character = new Character(100,10);
//            boolean gameIsStart=false;
//            Scanner console = new Scanner(System.in);
//
//
//
//
//
//
//            String request=console.nextLine();
//            while(true){
//
//                while(!(gameIsStart))
//                {
//                    if (request.equals("/Start"))
//                    {
//                        gameIsStart=true;
//                        break;
//                    }
//                    else
//                    {
//                        System.out.println("??????? /Start, ????? ??????");
//                    }
//                    request=console.nextLine();
//
//                }
//                System.out.println("?? ? ??????? "+roomManager.RoomNumber);
//                request=console.nextLine();
//                if (request.equals("/Data"))
//                {
//                    System.out.println(character.Character_data());
//                }
//                else if (request.equals("/Interact"))
//                {
//                    System.out.println(roomManager.Interact());
//                    while(true)
//                    {
//                        request=console.nextLine();
//                        try {
//                            int a = Integer.parseInt(request);
//                            if (a>0 && a<=roomManager.chests[roomManager.RoomNumber-1].length)
//                            {
//
//                                System.out.println("?????????? ???????: "+roomManager.chests[roomManager.RoomNumber-1][a-1].ShowItem());
//                                if (!roomManager.chests[roomManager.RoomNumber-1][a-1].isEmpty)
//                                {
//                                    System.out.println("????? ???????? (Y/N)");
//                                    request=console.nextLine();
//                                    while (!(request.equals("Y") || request.equals("N")))
//                                    {
//                                        System.out.println("???????? ???????");
//                                        request=console.nextLine();
//                                    }
//                                    if(request.equals("Y"))
//                                    {
//                                        roomManager.chests[roomManager.RoomNumber-1][a-1].isEmpty=true;
//                                        for(int i = 0;i<character.inventory.length;i++)
//                                        {
//                                            if(character.inventory[i].isUsed)
//                                            {
//                                                character.inventory[i]=roomManager.chests[roomManager.RoomNumber-1][a-1].item;
//                                                character.inventory[i].isUsed=false;
//                                                System.out.println("?? ????? "+ character.inventory[i].name);
//                                                break;
//                                            }
//                                        }
//                                    }
//                                }
//
//                                break;
//
//                            }
//                            a-=roomManager.chests[roomManager.RoomNumber-1].length;
//                            if (a>0 && a<=roomManager.doors[roomManager.RoomNumber-1].length)
//                            {
//                                System.out.println("????? ? ?????? (Y/N)");
//                                request=console.nextLine();
//                                while (!(request.equals("Y") || request.equals("N")))
//                                {
//                                    System.out.println("???????? ???????");
//                                    request=console.nextLine();
//                                }
//                                if(request.equals("Y"))
//                                {
//                                    System.out.println("?? ????? ? ?????");
//                                    roomManager.doors[roomManager.RoomNumber-1][a-1].isOpen=true;
//                                    roomManager.doors[roomManager.doors[roomManager.RoomNumber-1][a-1].nextRoom-1][roomManager.doors[roomManager.RoomNumber-1][a-1].linkedDoor].isOpen=true;
//                                    roomManager.RoomNumber=roomManager.doors[roomManager.RoomNumber-1][a-1].nextRoom;
//                                    if(roomManager.enemies[roomManager.RoomNumber-1].getHealth()!=0)
//                                    {
//                                        Battle(roomManager.enemies[roomManager.RoomNumber-1],character);
//                                    }
//                                }
//                                break;
//                            }
//                            System.out.println("???????? ?????");
//
//
//
//                        }
//                        catch (NumberFormatException e) {
//                            if(request.equals("/Back"))
//                            {
//                                break;
//                            }
//                            else
//                            {
//                                System.out.println("???????? ???????");
//                            }
//                        }
//
//
//
//                    }
//
//
//
//                }
//                else if(request.equals("/Inventory"))
//                {
//                    System.out.println(character.ShowInventory());
//                }
//                else if(request.equals("/Equip"))
//                {
//                    System.out.println("???????? ??????????? ???????(/Back - ????????? ?????)");
//                    System.out.println(character.ShowInventory());
//                    while (true)
//                    {
//                        request=console.nextLine();
//                        try {
//                            int a =Integer.parseInt(request);
//                            if(a<0||a>character.inventory.length)
//                            {
//                                System.out.println("???????????? ?????");
//                                continue;
//                            }
//                            if(character.Equip(a))
//                            {
//                                System.out.println("??????? ??????????");
//                                break;
//                            }
//                            else
//                            {
//                                System.out.println("??????? ?????? ???????????");
//                            }
//                        }
//                        catch (NumberFormatException e) {
//                            if(request.equals("/Back"))
//                            {
//                                break;
//                            }
//                            System.out.println("???????? ???????");
//                        }
//                    }
//
//                }
//                else if(request.equals("/Retry"))
//                {
//                    break;
//                }
//                else
//                {
//                    System.out.println("???????? ???????");
//                }
//
//
//                if(character.getHealth()==0)
//                {
//                    System.out.println("?? ?????????");
//                    break;
//                }
//
//
//            }
//            if(request.equals("/Retry"))
//            {
//                continue;
//            }
//            request="";
//            while (!request.equals("Y"))
//            {
//                System.out.println("??????? (Y/N)");
//                request=console.nextLine();
//            }
//        }
//
//
//
//    }




    public static final Bot bot = new Bot(
            options(),
            "5787246678:AAFXIoyuSRUw95D4a56XVZaY8TSXZl-NHDM",
            "RPG_Bot",
            createDispatcher()
    );

    public static void main(String[] args){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            bot.execute(
                    SetMyCommands.builder()
                            .command(BotCommand.builder().command("/start").description("чтобы начать").build())
                            .command(BotCommand.builder().command("/retry").description("начать заново").build())
                            .command(BotCommand.builder().command("/data").description("посмотреть информацию о персонаже").build())
                            .command(BotCommand.builder().command("/bag").description("открыть инвентарь").build())
                            .command(BotCommand.builder().command("help").description("помощь").build()).build()
            );
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static Dispatcher createDispatcher(){
        Dispatcher dp = new Dispatcher();

        dp.addHandler(
                new CallbackQueryHandler(
                        (Event<CallbackQuery> event)->{
                            return event.event.getData().equals("open");
                        },
                        (Event<CallbackQuery> event)->{
                            try{
                                event.bot.executeAsync(AnswerCallbackQuery.builder().callbackQueryId(event.event.getId()).build());
                                if(event.getData("data").get() instanceof Door door){
                                    event.setData("room", door.getNextRoom());
                                    event.bot.executeAsync(SendMessage.builder().chatId(event.userId)
                                            .text("дверь открыта").build());
                                }
                            }
                            catch (Exception e){}
                        }
                )
        );

        dp.addHandler(
                new CallbackQueryHandler(
                        (Event<CallbackQuery> event)->{
                            return event.event.getData().equals("equip");
                        },
                        (Event<CallbackQuery> event)->{
                            try{
                                event.bot.executeAsync(AnswerCallbackQuery.builder().callbackQueryId(event.event.getId()).build());
                                var p = event.getData("player");
                                if(event.getData("data").get() instanceof Item item){
                                    if(p.get() instanceof Creature player)
                                    {
                                        var builder = item.equip(player).chatId(event.userId);
                                        event.bot.executeAsync(builder.build());
                                    }
                                }
                            }
                            catch (Exception e){}
                        }
                )
        );

        dp.addHandler(
                new CallbackQueryHandler(
                        (Event<CallbackQuery> event)->{
                            return event.event.getData().equals("quit");
                        },
                        (Event<CallbackQuery> event)->{
                            try{
                                event.bot.executeAsync(AnswerCallbackQuery.builder().callbackQueryId(event.event.getId()).build());
                                var r = event.getData("room").get();
                                if(r instanceof Box room){
                                    event.setData("data", room);
                                    var builder = room.inspect().chatId(event.userId);
                                    event.bot.executeAsync(builder.build());
                                }
                            }
                            catch (Exception e){}
                        }
                )
        );

        dp.addHandler(
                new CallbackQueryHandler(
                        (Event<CallbackQuery> event)->{
                            return event.event.getData().equals("inspect");
                        },
                        (Event<CallbackQuery> event)->{
                            try{
                                event.bot.executeAsync(AnswerCallbackQuery.builder().callbackQueryId(event.event.getId()).build());
                                if(event.getData("data").get() instanceof Item item){
                                    var builder = item.inspect().chatId(event.userId);
                                    event.bot.executeAsync(builder.build());
                                }
                            }
                            catch (Exception e){}
                        }
                )
        );


        dp.addHandler(
                new CallbackQueryHandler(
                        (Event<CallbackQuery> event)->{
                            var obj = event.getData("data");
                            String[] q = event.event.getData().split("_");
                            try {
                                if(q[0].equals("t") && obj.get() instanceof Box data)
                                {
                                    event.setData("data", data.get(Integer.parseInt(q[1])-1));
                                    return true;
                                }
                            }
                            catch (Exception e){}
                            return false;
                        },
                        (Event<CallbackQuery> event)->{
                            try{
                                event.bot.executeAsync(AnswerCallbackQuery.builder().callbackQueryId(event.event.getId()).build());
                                if(event.getData("data").get() instanceof Item item){
                                    var builder = item.actions().chatId(event.userId);
                                    event.bot.executeAsync(builder.build());
                                }
                            }
                            catch (Exception e){}
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new Command("start"),
                        (Event<Message> event)->{
                            try {
                                var msg = SendMessage.builder().chatId(event.userId).text("""
                                        Добро пожаловать в нашу текстовую РПГ
                                        Ваша задача: выбраться из подземелья
                                        /data - посмотреть информацию о персонаже
                                        /bag - открыть инвентарь
                                        /retry - начать заново
                                        /help - помощь""").build();
                                bot.executeAsync(msg);
                                Box room = createRoom();
                                event.setData("room", room);
                                event.setData("player", new Creature("Player"));
                                event.setData("data", room);
                                event.setData("callback", bot.executeAsync(room.inspect().chatId(event.userId).build()));
                            }
                            catch (Exception e) {}
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new Command("help"),
                        (Event<Message> event)->{
                            try {
                                var msg = SendMessage.builder().chatId(event.userId).text("""
                                        Добро пожаловать в нашу текстовую РПГ
                                        Ваша задача: выбраться из подземелья
                                        /data - посмотреть информацию о персонаже
                                        /bag - открыть инвентарь
                                        /retry - начать заново
                                        /help - помощь""").build();
                                bot.executeAsync(msg);
                            }
                            catch (Exception e) {}
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new Command("data"),
                        (Event<Message> event)->{
                            try {
                                var obj = event.getData("player");
                                if(obj.get() instanceof Creature player){
                                    var msg = SendMessage.builder().chatId(event.userId).text(
                                            "Имя: " + player.getName()+
                                            "\nHp: " + player.getHp()+
                                            "\nAp: " + player.getAp()
                                    ).build();
                                    bot.executeAsync(msg);
                                }
                            }
                            catch (Exception e) {}
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new Command("bag"),
                        (Event<Message> event)->{
                            try {
                                var obj = event.getData("player");
                                if(obj.get() instanceof Creature player){
                                    event.setData("data", player.getBag());
                                    var msg = player.getBag().inspect().text("Инвентарь:").chatId(event.userId).build();
                                    bot.executeAsync(msg);
                                }
                            }
                            catch (Exception e) {}
                        }
                )
        );

        dp.addHandler(
                new MessageHandler(
                        new Command("retry"),
                        (Event<Message> event)->{
                            try {
                                var msg = SendMessage.builder().chatId(event.userId).text("""
                                        Добро пожаловать в нашу текстовую РПГ
                                        Ваша задача: выбраться из подземелья
                                        /data - посмотреть информацию о персонаже
                                        /bag - открыть инвентарь
                                        /retry - начать заново
                                        /help - помощь""").build();
                                bot.executeAsync(msg);
                                Box room = new Box("Комната 1").put(new Box("Room 2"));
                                event.setData("room", room);
                                event.setData("player", new Creature("Player"));
                                event.setData("callback", bot.executeAsync(room.inspect().chatId(event.userId).build()));
                            }
                            catch (Exception e) {}
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

    public static Box createRoom(){
        return new Box("Комната 1")
                .put(new Door("Door to Room 2", new Box("Room 2")))
                .put(new Box("Chest")
                        .put(new Sword("Меч", 10)))
                .put(new Box("Chest")
                );
    }
}

