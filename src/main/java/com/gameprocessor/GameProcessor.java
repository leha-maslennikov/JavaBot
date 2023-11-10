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
    private Creature bat;
    private Room room;

    /**
     * Отправляет текстовое сообщение пользователю
     * @param text текст, для пользователя
     */
    public abstract void send(String text);

    /**
     * Отправляет объекты пользователю, с текстом из getShortText,
     * если пользователь выбрал объект, отправляется getLongText
     * и действия из getActions
     * @param obj объекты, который будет отправлен пользователю
     */
    public abstract void send(String text, List<? extends Sendable> obj);

    /**
     * Не использовать
     */
    public abstract void callback(String callbackData);

    public void start(){
        send("""
                Добро пожаловать в нашу текстовую РПГ
                Ваша задача: выбраться из подземелья
                /inspect - осмотреть окружение
                /data - посмотреть информацию о персонаже
                /bag - открыть инвентарь
                /retry - начать заново
                /help - помощь
                """);
        player = new Creature("player",10,1);
        bat = new Creature("bat",3,1);
        room = Room.builder("Room 1")
                .addItem(new Item("Sth", "Rubish"))
                .addItem(
                        Box.builder("Chest", "Old chest")
                                .addItem(new Equipment("Weapon", "Bad weapon", 0, 2))
                                .addItem(new Equipment("Шлем", "Металлический шлем", 10, 1))
                                .build()
                )
                .addEnemy(bat)
                .build();
    }

    public void inspect(){
        send("Вы находитесь в " + room.getName() + ":", room.getItems());
    }

    public void data(){
        send("Name: " + player.getName() +
                "\nHp: " + player.getHp() +
                "\nAp: " + player.getAp()
        );
    }

    public void help(){
        send("""
                Ваша задача: выбраться из подземелья
                /inspect - осмотреть окружение
                /data - посмотреть информацию о персонаже
                /bag - открыть инвентарь
                /retry - начать заново
                /help - помощь
                """);
    }

    public void retry(){
        start();
    }

    public void bag(){
        send("В вашем инвентаре:", player.getInventory());
    }

    public void take(Item item){
        //TODO подбирать предметы из комнаты
    }

    public void loot(Box box){
        StringBuilder builder = new StringBuilder("Вы получили:\n");
        for(Item item: box.getItems()){
            builder.append(item.getShortText());
            builder.append("\n");
            player.getInventory().add(item);
        }
        box = new Box(box.getLongText(), "Пусто");
        send(builder.toString());
    }

    public void equip(Equipment equipment){
        equipment.equip(player);
        send("Вы надели " + equipment.getShortText());
    }

    public void unequip(Equipment equipment){
        equipment.unequip(player);
        send("Вы сняли " + equipment.getShortText() + " и положили в инвентарь");
    }

    public void attack(Creature creature)
    {
        send("Вы атакуете "+creature.getName()+"\n");
        player.attack(creature);
        send(creature.getName() + " получает" + player.getAp() + " урона");

        if(creature.getHp()==0)
        {
            send(creature.getName()+" повержен");
            room.deleteEnemy();
        }

        for (int i=0;i<room.getEnemies().size();i++)
        {
            send(room.getEnemies().get(i).getName()+ " атакует" +"\n");
            room.getEnemies().get(i).attack(player);
            send("Вы получаете" + room.getEnemies().get(i).getAp() + " урона");
            if(player.getHp()==0)
            {
                send("Вы проиграли. Игра окончена");
                retry();
            }
        }



    }
    public void await()
    {
        send("Вы ничего не делаете");
        for (int i=0;i<room.getEnemies().size();i++)
        {
            send(room.getEnemies().get(i).getName()+ " атакует" +"\n");
            room.getEnemies().get(i).attack(player);
            send("Вы получаете" + room.getEnemies().get(i).getAp() + " урона");
            if(player.getHp()==0)
            {
                send("Вы проиграли. Игра окончена");
                retry();
            }
        }
    }
}