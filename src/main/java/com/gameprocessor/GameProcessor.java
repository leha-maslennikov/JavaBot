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
     * @param request запрос, на который готовится response
     * @param text текст, для пользователя
     */
    private void send(Request request, String text){
        request.response = Response.builder()
                .userId(request.getUserId())
                .text(text)
                .build();
    }

    /**
     * Отправляет объекты пользователю, с текстом из getShortText
     * @param request запрос, на который готовится response
     * @param text текст, для пользователя
     * @param resources объекты для отправки, должны быть Sendable
     */
    private void send(Request request, String text, List<Resource> resources){
        var response = Response.builder()
                .userId(request.getUserId())
                .text(text);
        for(Resource resource: resources){
            if(resource.get() instanceof Sendable obj){
                response.addObject(obj.getShortText(), resource.id);
            }
        }
        request.response = response.build();
    }

    /**
     * Отправляет действия объекта пользователю
     * @param request запрос, на который готовится response
     * @param resource объект, чьи действия отправляются, должен быть Sendable
     */
    private void send(Request request, Resource resource){
        if(resource.get() instanceof Sendable obj){
            var response = Response.builder()
                    .userId(request.getUserId())
                    .text(obj.getLongText());
            for(String action: obj.getActions().getActions()){
                response.addObject(action, resource.id+":"+action);
            }
            request.response = response.build();
        }
    }

    public Response handleRequest(Request request){
        switch (request.getCallbackData()) {
            case "/start" -> start(request);
            case "/inspect" -> inspect(request);
            case "/data" -> data(request);
            case "/bag" -> bag(request);
            case "/retry" -> retry(request);
            case "/help" -> help(request);
            default -> {
                String[] args = request.getCallbackData().split(":");
                Resource resource = new Resource(args[0]+":"+args[1]+":"+args[2]);
                if(args.length < 4) {
                    send(request, resource);
                }
                switch (args[3]) {
                    case "loot" -> loot(request, resource);
                    case "equip" -> equip(request, resource);
                    case "unequip" -> unequip(request, resource);
                    case "open" -> open(request, resource);
                    default -> request.response = new Response();
                }
            }
        }
        return request.response;
    }

    private void createUser(Request request){
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
        Resource resource = ResourceManager.createResource(
                request.getUserId(),
                "UserData",
                new UserData(
                        request.getUserId(),
                        new Creature("player",10,1),
                        room
                )
        );
    }
    private void start(Request request){
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
        createUser(request);
    }

    private void inspect(Request request){
        UserData userData = (UserData) request.getUserData().get();
        Room room = (Room) userData.getRoom().get();
        send(request,"Вы находитесь в " + room.getName() + ":", room.getItems());
    }

    private void data(Request request){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        send(request,"Name: " + player.getName() +
                "\nHp: " + player.getHp() +
                "\nAp: " + player.getAp() +
                "\nЭкипировано:", player.getEquipment()
        );
    }

    private void help(Request request){
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

    private void retry(Request request){
        start(request);
    }

    private void bag(Request request){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        send(request,"В вашем инвентаре:", player.getInventory());
    }

    private void take(Item item){
        //TODO подбирать предметы из комнаты
    }

    private void loot(Request request, Resource resource){
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
        resource.update(new Box(box.getShortText(), "Пусто"));
        send(request, builder.toString());
    }

    private void equip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        player.getInventory().remove(resource);
        player.getEquipment().add(resource);
        player.setHp(player.getHp() + equipment.hp);
        player.setAp(player.getAp() + equipment.ap);
        userData.getPlayer().update(player);
        send(request, "Вы надели " + equipment.getShortText());
    }

    private void unequip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        player.getInventory().add(resource);
        player.getEquipment().remove(resource);
        player.setHp(player.getHp() - equipment.hp);
        player.setAp(player.getAp() - equipment.ap);
        userData.getPlayer().update(player);
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

    private void open(Request request, Resource resource){
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