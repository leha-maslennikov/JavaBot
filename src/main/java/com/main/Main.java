package com.main;

import java.io.Console;
import com.game.Character;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.game.RoomManager;
import com.game.Chest;
import com.game.Item;
import com.game.Weapon;
import com.game.Enemy;

import com.bot.Bot;
import com.bot.dispatcher.Dispatcher;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void Battle(Enemy enemy,Character character )
    {
        Scanner console = new Scanner(System.in);
        System.out.println("Появляется "+enemy.name);
        System.out.println("/Attack - атаковать, /Wait - ждать");
        while (character.getHealth()!=0&&enemy.getHealth()!=0)
        {
            System.out.println("Здоровье = "+character.getHealth());
            String request=console.nextLine();
            int damage = character.getDamage()+character.weapon.damage;
            while(true)
            {
                if(request.equals("/Wait"))
                {
                    break;
                }
                else if (request.equals("/Attack"))
                {
                    System.out.println("Вы атакуете");
                    System.out.println(enemy.name +" получает "+ damage +" урона");
                    enemy.getHit(damage);
                    break;
                }
            }
            if(enemy.getHealth()!=0)
            {
                System.out.println(enemy.name + " атакует");
                System.out.println("Вы получаете "+enemy.getDamage()+" Урона");
                character.getHit(enemy.getDamage());
            }

        }
        if(character.getHealth()!=0)
        {
            System.out.println(enemy.name+ " побеждена!");
        }

    }




    public static void main(String[] args) {
        while(true)
        {
            System.out.println("Добро пожаловать в нашу текстовую РПГ");
            System.out.println("Ваша задача: выбраться из подземелья");
            System.out.println("/Data - Посмотреть информацию о персонаже");
            System.out.println("/Interact - Взаимодействие с предметами в комнате");
            System.out.println("/Equip - Экипировать предмет из инвентаря");
            System.out.println("/Inventory - открыть инвентарь");
            System.out.println("/Retry - начать заново");
            System.out.println("Введите /Start, Чтобы начать");
            RoomManager roomManager = new RoomManager();

            Weapon woodenSword= new Weapon();
            woodenSword.damage=5;
            woodenSword.name="Деревянный меч";

            roomManager.chests[0][0].item=woodenSword;
            roomManager.chests[0][1].isEmpty=true;




            Character character = new Character(100,10);
            boolean gameIsStart=false;
            Scanner console = new Scanner(System.in);






            String request=console.nextLine();
            while(true){

                while(!(gameIsStart))
                {
                    if (request.equals("/Start"))
                    {
                        gameIsStart=true;
                        break;
                    }
                    else
                    {
                        System.out.println("Введите /Start, Чтобы начать");
                    }
                    request=console.nextLine();

                }
                System.out.println("Вы в комнате "+roomManager.RoomNumber);
                request=console.nextLine();
                if (request.equals("/Data"))
                {
                    System.out.println(character.Character_data());
                }
                else if (request.equals("/Interact"))
                {
                    System.out.println(roomManager.Interact());
                    while(true)
                    {
                        request=console.nextLine();
                        try {
                            int a = Integer.parseInt(request);
                            if (a>0 && a<=roomManager.chests[roomManager.RoomNumber-1].length)
                            {

                                System.out.println("Содержимое сундука: "+roomManager.chests[roomManager.RoomNumber-1][a-1].ShowItem());
                                if (!roomManager.chests[roomManager.RoomNumber-1][a-1].isEmpty)
                                {
                                    System.out.println("Взять предмет? (Y/N)");
                                    request=console.nextLine();
                                    while (!(request.equals("Y") || request.equals("N")))
                                    {
                                        System.out.println("Неверная команда");
                                        request=console.nextLine();
                                    }
                                    if(request.equals("Y"))
                                    {
                                        roomManager.chests[roomManager.RoomNumber-1][a-1].isEmpty=true;
                                        for(int i = 0;i<character.inventory.length;i++)
                                        {
                                            if(character.inventory[i].isUsed)
                                            {
                                                character.inventory[i]=roomManager.chests[roomManager.RoomNumber-1][a-1].item;
                                                character.inventory[i].isUsed=false;
                                                System.out.println("Вы взяли "+ character.inventory[i].name);
                                                break;
                                            }
                                        }
                                    }
                                }

                                break;

                            }
                            a-=roomManager.chests[roomManager.RoomNumber-1].length;
                            if (a>0 && a<=roomManager.doors[roomManager.RoomNumber-1].length)
                            {
                                System.out.println("Войти в дверь? (Y/N)");
                                request=console.nextLine();
                                while (!(request.equals("Y") || request.equals("N")))
                                {
                                    System.out.println("Неверная команда");
                                    request=console.nextLine();
                                }
                                if(request.equals("Y"))
                                {
                                    System.out.println("Вы вошли в дверь");
                                    roomManager.doors[roomManager.RoomNumber-1][a-1].isOpen=true;
                                    roomManager.doors[roomManager.doors[roomManager.RoomNumber-1][a-1].nextRoom-1][roomManager.doors[roomManager.RoomNumber-1][a-1].linkedDoor].isOpen=true;
                                    roomManager.RoomNumber=roomManager.doors[roomManager.RoomNumber-1][a-1].nextRoom;
                                    if(roomManager.enemies[roomManager.RoomNumber-1].getHealth()!=0)
                                    {
                                        Battle(roomManager.enemies[roomManager.RoomNumber-1],character);
                                    }
                                }
                                break;
                            }
                            System.out.println("Неверное число");



                        }
                        catch (NumberFormatException e) {
                            if(request.equals("/Back"))
                            {
                                break;
                            }
                            else
                            {
                                System.out.println("Неверная команда");
                            }
                        }



                    }



                }
                else if(request.equals("/Inventory"))
                {
                    System.out.println(character.ShowInventory());
                }
                else if(request.equals("/Equip"))
                {
                    System.out.println("Выберите экипируемый предмет(/Back - вернуться назад)");
                    System.out.println(character.ShowInventory());
                    while (true)
                    {
                        request=console.nextLine();
                        try {
                            int a =Integer.parseInt(request);
                            if(a<0||a>character.inventory.length)
                            {
                                System.out.println("Неправильное число");
                                continue;
                            }
                            if(character.Equip(a))
                            {
                                System.out.println("Предмет экипирован");
                                break;
                            }
                            else
                            {
                                System.out.println("Предмет нельзя экипировать");
                            }
                        }
                        catch (NumberFormatException e) {
                            if(request.equals("/Back"))
                            {
                                break;
                            }
                            System.out.println("Неверная команда");
                        }
                    }

                }
                else if(request.equals("/Retry"))
                {
                    break;
                }
                else
                {
                    System.out.println("Неверная команда");
                }


                if(character.getHealth()==0)
                {
                    System.out.println("Вы проиграли");
                    break;
                }


            }
            if(request.equals("/Retry"))
            {
                continue;
            }
            request="";
            while (!request.equals("Y"))
            {
                System.out.println("Заново? (Y/N)");
                request=console.nextLine();
            }
        }



    }


}


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

        return dp;
    }

    public static DefaultBotOptions options(){
        DefaultBotOptions options = new DefaultBotOptions();
        options.setMaxThreads(2);
        return options;
    }
}

