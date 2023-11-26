package com.gameprocessor;

import java.util.LinkedList;
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
    private static void send(Request request, String text){
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
            if(resource.get() instanceof Sendable obj) {
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
            case "/await" -> await(request);
            case "/attack" -> attack(request);
            default -> {
                String[] args = request.getCallbackData().split(":");
                Resource resource = new Resource(args[0]+":"+args[1]+":"+args[2]);
                if(args.length < 4) {
                    send(request, resource);
                    return request.response;
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
    private List<Room> generateFloor(int numberOfRooms,  List<List<Item>> items, List<List<Creature>> enemies){
        List<Room> rooms = new LinkedList<>();
        List<List<Item>> randomItems= new LinkedList<>();
        List<List<Creature>> randomEnemies=new LinkedList<>();
        for(int i=0;i<items.size();i++)
        {
            int randomNumber=((int)(Math.random()*(items.size()-i)));
            randomItems.add(items.get(randomNumber));
            items.remove(randomNumber);
        }
        for(int i=0;i<enemies.size();i++)
        {
            int randomNumber=((int)(Math.random()*(enemies.size()-i)));
            randomEnemies.add(enemies.get(randomNumber));
            enemies.remove(randomNumber);
        }
        rooms.add(Room.builder("Room "+1)
                .addItems(randomItems.get(1)).build());
        for(int i=1;i<numberOfRooms;i++)
        {
            rooms.add(Room.builder("Room "+i)
                    .addItems(randomItems.get(i))
                    .addEnemies(randomEnemies.get(i)).build());

        }
        return rooms;
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
        if(equipment.equip(player, resource)) {
            player.getInventory().remove(resource);
            send(request, "Вы надели " + equipment.getShortText());
            userData.getPlayer().update(player);
        }
        else send(request, "У вас не получилось надеть " + equipment.getShortText());
    }

    private void unequip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        if(equipment.unequip(player, resource)) {
            player.getInventory().add(resource);
            send(request, "Вы сняли " + equipment.getShortText() + " и положили в инвентарь");
            userData.getPlayer().update(player);
        }
        else {
            send(request, "У вас не получилось снять " + equipment.getShortText());
        }
    }

    private void attack(Request request) {
        UserData userData = (UserData) request.getUserData().get();
        Room room = (Room) userData.getRoom().get();
        var response = Response.builder().userId(request.getUserId()).text("Выберете врага для атаки:");
        for(Resource resource: room.getEnemies()){
            Creature enemy = (Creature) resource.get();
            response.addObject(enemy.getName()+" "+enemy.getHp(), resource.id);
        }
    }
    private void attack(Request request, Resource resource)
    {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Room room = (Room) userData.getRoom().get();
        Boolean combatFlag = (Boolean) userData.getCombatFlag().get();
        Creature creature = (Creature) resource.get();
        StringBuilder builder = new StringBuilder("Вы наносите ").append(creature.getName()).append(" ")
                .append(player.attack(creature)).append(" урона");
        if(creature.getHp()==0)
        {
            builder.append("\n").append(creature.getName()).append(" повержен");
            room.getEnemies().remove(resource);
        }
        if(room.getEnemies().isEmpty()){
            combatFlag=false;
            builder.append("\nВсе враги побеждены");
        }
        userData.getPlayer().update(player);
        userData.getRoom().update(room);
        userData.getCombatFlag().update(combatFlag);
        resource.update(creature);
        await(request);
        send(request, builder.append("\n").append(request.response.text).toString());
    }
    private void await(Request request)
    {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Room room = (Room) userData.getRoom().get();
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<room.getEnemies().size();i++)
        {
            Creature enemy = (Creature) room.getEnemies().get(i).get();
            builder.append(enemy.getName()).append(" наносит ").append(enemy.attack(player)).append(" урона");
            if(player.getHp()==0)
            {
                builder.append("\nВы проиграли. Игра окончена.\n/retry - чтобы начать заново");
            }
        }
        userData.getPlayer().update(player);
        send(request, builder.toString());
    }

    private void open(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Door door = (Door) resource.get();
        userData.room = door.getRoom();
        Room room = (Room) door.getRoom().get();
        StringBuilder builder = new StringBuilder("Вы вошли в ").append(room.getName());
        if(!room.getEnemies().isEmpty()) {
            userData.getCombatFlag().update(true);
            await(request);
            builder.append("\nНа вас напали.\n").append(request.response.text)
                    .append("\n/attack - атаковать\n/await - пропустить ход");
        }
        request.getUserData().update(userData);
        send(request, builder.toString());
    }
}