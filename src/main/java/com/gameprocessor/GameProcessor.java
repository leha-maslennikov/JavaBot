package com.gameprocessor;

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

    public Response handleRequest(Request request) {
        try {
            switch (request.getCallbackData()) {
                case "/start" -> start(request);
                case "/inspect" -> inspect(request);
                case "/data" -> data(request);
                case "/bag" -> bag(request);
                case "/retry" -> retry(request);
                case "/help" -> help(request);
                case "/await" -> await(request);
                case "/attack" -> attack(request);
                case "/skills" -> skills(request);
                default -> {
                    String[] args = request.getCallbackData().split(":");
                    if(args.length < 3) return new Response();
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
                        case "use" -> use(request,resource);
                        case "raiseHp" -> raiseHp(request);
                        case "raiseAp" -> raiseAp(request);
                        case "forceAttack" -> forceAttack(request);
                        case "areaAttack" -> areaAttack(request);
                        default -> request.response = new Response();
                    }
                }
            }
            return request.response;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Response();
        }
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
                                .addItem(new Consumable("Зелье здоровья","Пополняет здововье на 10",10))
                                .build()
                )
                .addItem(
                        new Door(
                                "Door",
                                "Door to Room 2",
                                Room.builder("Room 2")
                                        .userId(request.getUserId())
                                        .addEnemy(new Creature("bat",3,1,5))
                                        .addEnemy(new Creature("spider", 3, 1,5))
                                        .build(),
                                request.getUserId()
                        )
                )
                .build();
        Resource resource = ResourceManager.createResource(
                request.getUserId(),
                "UserData",
                new UserData(
                        request.getUserId(),
                        new Creature("player",10,1,0),
                        room,
                        new LevelManager(),
                        new SkillManager()
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
        LevelManager levelManager =  (LevelManager) userData.getLevelManager().get();
        send(request,"Name: " + player.getName() +
                "\nHp: " + player.getHp()  + "/" + player.getMaxHp() +
                "\nAp: " + player.getAp() +
                "\nExp: " + levelManager.getExp() +
                "\nLevel: " + levelManager.getLevel() +
                "\nNext level: " + levelManager.getNextLevel() +
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
            builder.append(item.getShortText());
            builder.append("\n");
            player.getInventory().add(i);
        }
        resource.update(new Box(box.getShortText(), "Пусто"));
        userData.getPlayer().update(player);
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
        resource.update(equipment);
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
        LevelManager levelManager = (LevelManager) userData.getLevelManager().get();
        StringBuilder builder = new StringBuilder("Вы наносите ").append(creature.getName()).append(" ")
                .append(player.attack(creature)).append(" урона");
        player.setBonusAp(0);
        if(creature.getHp()==0)
        {
            builder.append("\n").append(creature.getName()).append(" повержен");
            levelManager.setExp(levelManager.getExp() + creature.getExp());
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
        if (levelManager.getExp()>=levelManager.getNextLevel())
        {
            levelManager.setNextLevel(levelManager.getNextLevel() + levelManager.getLevel() * 10);
            levelManager.setLevel(levelManager.getLevel() + 1);
            send(request,userData.levelManager);
        }
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
    private void raiseHp(Request request) {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        player.setMaxHp(player.getMaxHp() + 5);
        player.setHp(player.getHp() + 5);
        userData.getPlayer().update(player);
        request.getUserData().update(userData);
        send(request,"Здоровье увеличено на 5");
    }
    private void raiseAp(Request request) {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        player.setAp(player.getAp() + 1);
        userData.getPlayer().update(player);
        request.getUserData().update(userData);
        send(request,"Урон увеличен на 1");
    }
    private void use(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Consumable consumable = (Consumable) resource.get();
        consumable.useConsumable(player);
        player.getInventory().remove(resource);
        userData.getPlayer().update(player);
        resource.update(consumable);
        send(request, "Вы использовали " + consumable.name);
    }
    private void skills(Request request)
    {
        UserData userData = (UserData) request.getUserData().get();
        send(request,userData.skillManager);
    }
    private void forceAttack(Request request)
    {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        player.setBonusAp(player.getAp());
        userData.getPlayer().update(player);
        attack(request);
    }
    private void areaAttack(Request request)
    {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Room room = (Room) userData.getRoom().get();
        LevelManager levelManager = (LevelManager) userData.getLevelManager().get();
        StringBuilder resBuilder = new StringBuilder();
        for(int i = 0; i < room.getEnemies().size(); i++ )
        {
            Resource resource = room.getEnemies().get(i);
            Creature creature = (Creature) resource.get();
            StringBuilder builder = new StringBuilder("Вы наносите ").append(creature.getName()).append(" ")
                    .append(player.attack(creature)).append(" урона\n");
            player.setBonusAp(0);
            if(creature.getHp()==0)
            {
                builder.append(creature.getName()).append(" повержен\n");
                i--;
                levelManager.setExp(levelManager.getExp() + creature.getExp());
                room.getEnemies().remove(resource);
                resource.delete();
            }
            userData.getRoom().update(room);
            request.getUserData().update(userData);
            if(creature.getHp()>0) resource.update(creature);
            resBuilder.append(builder);



        }
        await(request);
        if(room.getEnemies().isEmpty()){
            userData.combatFlag=false;
            resBuilder.append("\nВсе враги побеждены");
        }
        send(request,resBuilder.append("\n").append(request.response.text).toString());
        if (levelManager.getExp()>=levelManager.getNextLevel())
        {
            levelManager.setNextLevel(levelManager.getNextLevel() + levelManager.getLevel() * 10);
            levelManager.setLevel(levelManager.getLevel() + 1);
            send(request,userData.levelManager);
        }
    }
}
