package com.main;

import java.io.Console;

import com.bot.dispatcher.Event;
import com.bot.dispatcher.filters.Command;
import com.bot.dispatcher.handlers.MessageHandler;
import com.game.*;

import com.bot.Bot;
import com.bot.dispatcher.Dispatcher;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
                                Room room = new Room("Комната 1").put(new Room("Room 2"));
                                event.setData("room", room);
                                bot.executeAsync(room.inspect().chatId(event.userId).build());
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
}

