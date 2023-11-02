package com.game;

public class RoomManager {
    /**
     * Номер текущей кмнаты
     */
    public int RoomNumber = 1;

    /**
     * Массив расположения дверей в подземелье
     */
    public Door[][] doors = new Door[2][];
    /**
     * Массив расположения сундуков в подземелье
     */
    public Chest[][] chests=new Chest[2][];
    /**
     * Массив расположения врагов в подземелье
     */
    public Enemy[] enemies=new Enemy[2];


    /**
     * Конструктор, генерирующий обЪекты и врагов
     */
    public RoomManager()
    {

        this.chests[0]=new Chest[2];
        this.chests[1]=new Chest[0];

        for(int i =0;i<this.chests.length;i++)
        {
            for(int j=0;j<this.chests[i].length;j++)
            {
                this.chests[i][j]=new Chest();
            }

        }
        this.doors[0]=new Door[1];
        this.doors[1]=new Door[1];
        for(int i =0;i<this.doors.length;i++)
        {
            for(int j=0;j<this.doors[i].length;j++)
            {
                this.doors[i][j]=new Door();
            }

        }
        this.doors[0][0].nextRoom=2;
        this.doors[1][0].nextRoom=1;
        for (int i=0;i<enemies.length;i++)
        {
            enemies[i]=new Enemy(0,0,"");
        }
        enemies[1]=new Enemy(30,10,"Летучая мышь");




    }

    /**
     * @return - Выводит предметы, с которыми можно взаимодействовать
     */
    public String Interact()
    {
        int kol=1;
        String str="";
        for (int i=0;i<chests[RoomNumber-1].length;i++)
        {
            str+=(kol)+". Сундук"+"\n";
            kol++;
        }
        for(int i=0;i<doors[RoomNumber-1].length;i++)
        {
            if(doors[RoomNumber-1][i].isOpen)
            {
                str+=(kol)+". Дверь (комната "+doors[RoomNumber-1][i].nextRoom+")\n";
                kol++;
            }
           else
            {
                str+=(kol)+". Дверь (комната ???)\n";
                kol++;
            }
        }
        return str;
    }

}
