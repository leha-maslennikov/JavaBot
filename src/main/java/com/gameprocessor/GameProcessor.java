package com.gameprocessor;

import java.util.List;

/**
 * Класс, реализующий игровые механики.
 * Имеет в себе 2 типа публичных функций.
 * 1. публичная функция без аргументов:
 *      является игровой коммандой, которую
 *      может вызвать пользователь
 * 2. публичная функция принимающая в качестве
 * аргумента объект реализующий Sendable:
 *      является реализацией действия,
 *      которой совершается с этим объектом
 */
public abstract class GameProcessor {

    private Creature player;
    private Room room;
    private boolean combatFlag;

    /**
     * Отправляет текстовое сообщение пользователю
     * @param text текст, для пользователя
     */
    public abstract void send(Request request, String text);

    /**
     * Отправляет объекты пользователю, с текстом из getShortText,
     * если пользователь выбрал объект, отправляется getLongText
     * и действия из getActions
     * @param obj объекты, который будет отправлен пользователю
     */
    public abstract void send(Request request, String text, List<? extends Sendable> obj);

    public Response handleRequest(Request request){
        return new Response();
    }

    /**
     * Не использовать
     */
    public void callback(String callbackData){
        if(combatFlag){
            if(callbackData.equals("/attack")){
                attack(room.getEnemies().get(0));
            }
            else if(callbackData.equals("/await")){
                await();
            }
            else {
                await();
            }
            return;
        }
        if(callbackData.equals("/start")){
            start();
            return;
        }
        if(callbackData.equals("/inspect")){
            inspect();
            return;
        }
        if(callbackData.equals("/data")){
            data();
            return;
        }
        if(callbackData.equals("/bag")){
            bag();
            return;
        }
        if(callbackData.equals("/retry")){
            retry();
            return;
        }
        if(callbackData.equals("/help")){
            help();
            return;
        }
    }

    public void start(Request request){
        send(request,
                """
                Добро пожаловать в нашу текстовую РПГ
                Ваша задача: выбраться из подземелья
                /inspect - осмотреть окружение
                /data - посмотреть информацию о персонаже
                /bag - открыть инвентарь
                /retry - начать заново
                /help - помощь
            """);
        player = new Creature("player",10,1);
        room = Room.builder("Room 1")
                .addItem(new Item("Sth", "Rubish"))
                .addItem(
                        Box.builder("Chest", "Old chest")
                                .addItem(new Equipment("Weapon", "Bad weapon", 0, 2))
                                .addItem(new Equipment("Шлем", "Металлический шлем", 10, 1))
                                .build()
                )
                .addEnemy(new Creature("bat",3,1))
                .build();
    }

    public void inspect(Request request){
        send(request,"Вы находитесь в " + room.getName() + ":", room.getItems());
    }

    public void data(Request request){
        send(request,"Name: " + player.getName() +
                "\nHp: " + player.getHp() +
                "\nAp: " + player.getAp() +
                "\nЭкипировано:", this.player.getEquipment()
        );
    }

    public void help(Request request){
        send(request,
                """
                Ваша задача: выбраться из подземелья
                /inspect - осмотреть окружение
                /data - посмотреть информацию о персонаже
                /bag - открыть инвентарь
                /retry - начать заново
                /help - помощь
                """);
    }

    public void retry(Request request){
        start(request);
    }

    public void bag(Request request){
        send(request,"В вашем инвентаре:", player.getInventory());
    }

    public void take(Item item){
        //TODO подбирать предметы из комнаты
    }

    public void loot(Request request, Box box){
        StringBuilder builder = new StringBuilder("Вы получили:\n");
        for(Item item: box.getItems()){
            builder.append(item.getShortText());
            builder.append("\n");
            player.getInventory().add(item);
        }
        box = new Box(box.getLongText(), "Пусто");
        send(request, builder.toString());
    }

    public void equip(Request request, Equipment equipment){
        equipment.equip(player);
        send(request, "Вы надели " + equipment.getShortText());
    }

    public void unequip(Request request, Equipment equipment){
        equipment.unequip(player);
        send(request, "Вы сняли " + equipment.getShortText() + " и положили в инвентарь");
    }

    public void attack(Request request, Creature creature)
    {
        send(request,"Вы атакуете "+creature.getName());
        player.attack(creature);
        send(request,creature.getName() + " получает" + player.getAp() + " урона");
        if(creature.getHp()==0)
        {
            send(request,creature.getName()+" повержен");
            room.deleteEnemy();
        }
        if(room.getEnemies().isEmpty()){
            combatFlag=false;
            send(request,"Все враги побеждены");
            return;
        }
        await(request);
    }
    public void await(Request request)
    {
        for (int i=0;i<room.getEnemies().size();i++)
        {
            send(request,room.getEnemies().get(i).getName()+ " атакует");
            room.getEnemies().get(i).attack(player);
            send(request,"Вы получаете" + room.getEnemies().get(i).getAp() + " урона");
            if(player.getHp()==0)
            {
                send(request,"Вы проиграли. Игра окончена");
                retry(request);
            }
        }
    }

    public void open(Request request, Door door){
        this.room = door.getRoom();
        if(!room.getEnemies().isEmpty()){
            combatFlag = true;
            send(request,"На вас напали.");
            await(request);
            send(request,"/attack - атаковать\n/await - пропустить ход");
        }
    }
}