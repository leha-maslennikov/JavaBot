package com.gameprocessor;

import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.entities.creatures.Dwarf;
import com.gameprocessor.entities.creatures.Human;
import com.gameprocessor.entities.items.*;
import com.gameprocessor.resourcemanager.Resource;
import com.gameprocessor.resourcemanager.ResourceManager;
import com.gameprocessor.entities.*;
import com.gameprocessor.user.Request;
import com.gameprocessor.user.Response;
import com.gameprocessor.user.UserData;

import java.util.LinkedList;
import java.util.List;

/**
 * Неактуально TODO
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
    public static final Creature[] races = new Creature[]{new Dwarf(), new Human()};

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
     * Отправляет объекты пользователю, с текстом из getName
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
                response.addObject(obj.getName(), "/use " + resource.getId());
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
                    .text(obj.getDescription());
            for(String action: obj.getActions().getActions()){
                response.addObject(action, "/" + action + " " + resource.getId());
            }
            request.response = response.build();
        }
    }

    public Response handleRequest(Request request) {
        try {
            GameCommand command = new GameCommand(request.getCallbackData());

            switch (command.getCommand()) {
                case "/start" -> start(request);
                case "/inspect" -> inspect(request);
                case "/data" -> data(request);
                case "/bag" -> bag(request);
                case "/retry", "/Y" -> retry(request);
                case "/help" -> help(request);
                case "/await" -> await(request);
                case "/attack" -> attack(request);
                case "/N" -> request.response = Response.builder().userId(request.getUserId()).text("Действие отменено").build();
                default -> {
                    String[] args = request.getCallbackData().split(":");
                    if(args.length < 3) return Response.builder().build();
                    Resource resource = new Resource(args[0] + ":" + args[1] + ":" + args[2]);
                    if (resource.get() instanceof Creature creature) {
                        attack(request, resource);
                        return request.response;
                    }
                    if (args.length < 4) {
                        send(request, resource);
                        return request.response;
                    }
                    switch (args[3]) {
                        case "loot" -> loot(request, resource);
                        case "equip" -> equip(request, resource);
                        case "unequip" -> unequip(request, resource);
                        case "open" -> open(request, resource);
                        default -> request.response = Response.builder().build();
                    }
                }
            }
            return request.response;
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.builder().build();
        }
    }
    private Room generateFloor(int numberOfRooms, List<List<Item>> items, List<List<Creature>> enemies, Request request) {
        List<Room.RoomBuilder> rooms = new LinkedList<>();
        List<List<Item>> randomItems= new LinkedList<>();
        List<List<Creature>> randomEnemies=new LinkedList<>();
        List<List<Integer>>doorsId=new LinkedList<>();
        while(!items.isEmpty())
        {
            int randomNumber=((int)(Math.random()*(items.size())));
            randomItems.add(items.get(randomNumber));
            items.remove(randomNumber);
        }
        while(!enemies.isEmpty())
        {
            int randomNumber=((int)(Math.random()*(enemies.size())));
            randomEnemies.add(enemies.get(randomNumber));
            enemies.remove(randomNumber);
        }

        doorsId.add(new LinkedList<>());
        for (int i=1;i<numberOfRooms;i++)
        {
            doorsId.add(new LinkedList<>());
            int randomRoom=((int)(Math.random()*i));
            doorsId.get(i).add(randomRoom);
            //doorsId.get(randomRoom).add(i);
        }

        for(int i=0;i<numberOfRooms;i++)
        {
            rooms.add(
                    Room.builder("Room "+(i+1))
                    .userId(request.getUserId())
                    .addItems(randomItems.get(i))
                    .addEnemies(randomEnemies.get(i))
            );
        }

        int k = 1;
        for(int i = numberOfRooms-1; i >= 0; i--) {
            Room room = rooms.get(i).build();
            for(int j: doorsId.get(i)) {
                rooms.get(j).addItem(
                        new Door("Door " + k++, "Door to " + room.getName(), room, request.getUserId())
                );
            }
        }

        return rooms.get(0).build();
    }
    private void createUser(Request request){
        List<List<Item>>items=new LinkedList<>();
        List<List<Creature>>enemies=new LinkedList<>();

        for(int i=0;i<5;i++)
        {
            items.add(new LinkedList<>());
            enemies.add(new LinkedList<>());

        }
        items.get(0).add( Box.builder("Chest", "Old chest")
                .userId(request.getUserId())
                .addItem(new Equipment("Weapon", "Bad weapon", 0, 2))
                .build());
        items.get(1).add( Box.builder("Chest", "Old chest")
                .userId(request.getUserId())
                .addItem(new Equipment("Шлем", "Металлический шлем", 5, 1))
                .build());
        items.get(2).add(Box.builder("Chest", "Old chest")
                .userId(request.getUserId())
                .addItem(new Equipment("armor", "Iron armor", 10, 1))
                .build());
        enemies.get(0).add(new Creature("bat",3,1));
        enemies.get(1).add((new Creature("spider", 3, 1)));
        enemies.get(2).add(new Creature("slime",5,1));

        Room room=generateFloor(5,items,enemies,request);

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
    private void start(Request request) {
        if(ResourceManager.hasUser(request.getUserId())) {
            var response = Response.builder()
                    .userId(request.getUserId())
                    .text("Игра запущена. Хотите начать заново?");
            response.addObject("Да", "/retry");
            response.addObject("Нет", "/N");
            request.response = response.build();
            return;
        }
        send(request,
                """
                Добро пожаловать в нашу текстовую РПГ
                Ваша задача: выбраться из подземелья
                /create - создание персонажа
                """);
        //create(request);
        //createUser(request);
    }

    private void create(Request request) {
        if(ResourceManager.hasUser(request.getUserId())) {
            start(request);
            return;
        }
        var response = Response.builder().userId(request.getUserId());
        StringBuilder text = new StringBuilder("Выберете расу");
        for(Creature i: races) {
            text.append("\n").append(i.getInfo());
            response.addObject(i.getClass().getSimpleName(), "/create "+i.getName());
        }
        request.response = response.text(text.toString()).build();
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

    private void retry(Request request) {
        ResourceManager.deleteUser(request.userId);
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
            builder.append(item.getName());
            builder.append("\n");
            player.getInventory().add(i);
        }
        resource.update(Box.builder(box.getName(), "Пусто").build());
        userData.getPlayer().update(player);
        send(request, builder.toString());
    }

    private void equip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        if(equipment.equip(player, resource)) {
            player.getInventory().remove(resource);
            send(request, "Вы надели " + equipment.getName());
            userData.getPlayer().update(player);
        }
        else send(request, "У вас не получилось надеть " + equipment.getName());
        resource.update(equipment);
    }

    private void unequip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        if(equipment.unequip(player, resource)) {
            player.getInventory().add(resource);
            send(request, "Вы сняли " + equipment.getName() + " и положили в инвентарь");
            userData.getPlayer().update(player);
        }
        else {
            send(request, "У вас не получилось снять " + equipment.getName());
        }
        resource.update(equipment);
    }

    private void attack(Request request) {
        UserData userData = (UserData) request.getUserData().get();
        Room room = (Room) userData.getRoom().get();
        var response = Response.builder().userId(request.getUserId()).text("Выберете врага для атаки:");
        for(Resource resource: room.getEnemies()){
            Creature enemy = (Creature) resource.get();
            response.addObject(enemy.getName()+" "+enemy.getHp()+"hp", resource.id);
        }
        request.response = response.build();
    }
    private void attack(Request request, Resource resource)
    {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Room room = (Room) userData.getRoom().get();
        Creature creature = (Creature) resource.get();
        StringBuilder builder = new StringBuilder("Вы наносите ").append(creature.getName()).append(" ")
                .append(player.attack(creature)).append(" урона");
        if(creature.getHp()==0)
        {
            builder.append("\n").append(creature.getName()).append(" повержен");
            room.getEnemies().remove(resource);
            resource.delete();
        }
        if(room.getEnemies().isEmpty()){
            userData.combatFlag=false;
            builder.append("\nВсе враги побеждены");
        }
        userData.getRoom().update(room);
        request.getUserData().update(userData);
        if(creature.getHp()>0) resource.update(creature);
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
            builder.append(enemy.getName()).append(" наносит ").append(enemy.attack(player)).append(" урона\n");
            if(player.getHp()==0)
            {
                builder.append("Вы проиграли. Игра окончена.\n/retry - чтобы начать заново");
                break;
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
        userData.getRoom().update(room);
        StringBuilder builder = new StringBuilder("Вы вошли в ").append(room.getName());
        if(!room.getEnemies().isEmpty()) {
            userData.combatFlag = true;
            request.getUserData().update(userData);
            await(request);
            builder.append("\nНа вас напали.\n").append(request.response.text)
                    .append("/attack - атаковать\n/await - пропустить ход");
        }
        request.getUserData().update(userData);
        send(request, builder.toString());
    }
}