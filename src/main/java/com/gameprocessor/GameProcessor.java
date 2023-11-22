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
public class GameProcessor {

    /**
     * Отправляет текстовое сообщение пользователю
     * @param text текст, для пользователя
     */
    public void send(Request request, String text){
        request.response = Response.builder()
                .userId(request.getUserId())
                .text(text)
                .build();
    }

    /**
     * Отправляет объекты пользователю, с текстом из getShortText,
     * если пользователь выбрал объект, отправляется getLongText
     * и действия из getActions
     //* @param obj объекты, который будет отправлен пользователю
     */
    public void send(Request request, String text, List<Resource> resources){
        var response = Response.builder()
                .userId(request.getUserId())
                .text(text);
        for(Resource resource: resources){
            if(resource.get() instanceof Sendable obj){
                response.addObject(obj, resource.getId());
            }
        }
        request.response = response.build();
    }

    public Response handleRequest(Request request){
        switch (request.callbackData) {
            case "/start" -> start(request);
            case "/inspect" -> inspect(request);
            case "/data" -> data(request);
            case "/bag" -> bag(request);
            case "/retry" -> retry(request);
            case "/help" -> help(request);
            default -> {
                Resource resource = new Resource(request.callbackData);
                switch (request.action) {
                    case "loot" -> loot(request, resource);
                    case "equip" -> equip(request, resource);
                    case "unequip" -> unequip(request, resource);
                    case "open" -> open(request, resource);
                }
            }
        }
        return request.response;
    }

    public void createUser(Request request){

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
        Room room = Room.builder("Room 1")
                .userId(request.getUserId())
                .addItem(new Item("Sth", "Rubish"))
                .addItem(
                        Box.builder("Chest", "Old chest")
                                .userId(request.getUserId())
                                .addItem(new Equipment("Weapon", "Bad weapon", 0, 2))
                                .addItem(new Equipment("Шлем", "Металлический шлем", 10, 1))
                                .build()
                )
                .addEnemy(new Creature("bat",3,1))
                .build();
        ResourceManager.createResource(
                request.getUserId(),
                "UserData",
                new UserData(
                        request.getUserId(),
                        new Creature("player",10,1),
                        room
                )
        );
    }

    public void inspect(Request request){
        UserData userData = (UserData) request.getUserData().get();
        Room room = (Room) userData.getRoom().get();
        send(request,"Вы находитесь в " + room.getName() + ":", room.getItems());
    }

    public void data(Request request){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        send(request,"Name: " + player.getName() +
                "\nHp: " + player.getHp() +
                "\nAp: " + player.getAp() +
                "\nЭкипировано:", player.getEquipment()
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
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        send(request,"В вашем инвентаре:", player.getInventory());
    }

    public void take(Item item){
        //TODO подбирать предметы из комнаты
    }

    public void loot(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Box box = (Box) resource.get();
        StringBuilder builder = new StringBuilder("Вы получили:\n");
        for(Resource i: box.getItems()){
            Item item = (Item) i.get();
            builder.append(item.getShortText());
            builder.append("\n");
            player.getInventory().add(i);
        }
        box = new Box(box.getLongText(), "Пусто");
        send(request, builder.toString());
    }

    public void equip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        player.getInventory().remove(resource);
        player.getEquipment().add(resource);
        player.setHp(player.getHp() + equipment.hp);
        player.setAp(player.getAp() + equipment.ap);
        send(request, "Вы надели " + equipment.getShortText());
    }

    public void unequip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        player.getInventory().add(resource);
        player.getEquipment().remove(resource);
        player.setHp(player.getHp() - equipment.hp);
        player.setAp(player.getAp() - equipment.ap);
        send(request, "Вы сняли " + equipment.getShortText() + " и положили в инвентарь");
    }

//    public void attack(Request request, Creature creature)
//    {
//        send(request,"Вы атакуете "+creature.getName());
//        player.attack(creature);
//        send(request,creature.getName() + " получает" + player.getAp() + " урона");
//        if(creature.getHp()==0)
//        {
//            send(request,creature.getName()+" повержен");
//            room.deleteEnemy();
//        }
//        if(room.getEnemies().isEmpty()){
//            combatFlag=false;
//            send(request,"Все враги побеждены");
//            return;
//        }
//        await(request);
//    }
//    public void await(Request request)
//    {
//        for (int i=0;i<room.getEnemies().size();i++)
//        {
//            send(request,room.getEnemies().get(i).getName()+ " атакует");
//            room.getEnemies().get(i).attack(player);
//            send(request,"Вы получаете" + room.getEnemies().get(i).getAp() + " урона");
//            if(player.getHp()==0)
//            {
//                send(request,"Вы проиграли. Игра окончена");
//                retry(request);
//            }
//        }
//    }

    public void open(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Door door = (Door) resource.get();
        userData.room = door.getRoom();
//        if(!room.getEnemies().isEmpty()){
//            combatFlag = true;
//            send(request,"На вас напали.");
//            await(request);
//            send(request,"/attack - атаковать\n/await - пропустить ход");
//        }
    }
}