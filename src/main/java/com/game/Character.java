package com.game;


public class Character extends BaseRace
{

    /**
     * Инвентарь персонажа
     */
   public Item[] inventory=new Item[6];
    /**
     * Носимое оружие
     */
   public Weapon weapon = new Weapon();


    /**
     * Конструктор персонажа
     * @param health - здоровье персонажа
     * @param damage - наносимый урон
     */
    public Character(int health, int damage) {
        super(health, damage);
        for (int i =0;i<inventory.length;i++)
        {
            inventory[i]=new Item();
        }
    }

    /**
     * Экипирует оружие из инвентаря
     * @param a - Номер ячейки в инвентаре
     * @return - Взвращает, получилось ли экипировать
     */
    public boolean Equip(int a)
    {
        if(!this.inventory[a-1].wear())
        {
            return false;
        }
        Weapon tempWeapon=new Weapon();
        tempWeapon.name=inventory[a-1].name;
        tempWeapon.damage=inventory[a-1].damage;
        inventory[a-1].isUsed=true;
        this.weapon=tempWeapon;
        weapon.isUsed=false;
        return true;
    }


    /**
     * @return - Показывает инвентарь
     */
    public String ShowInventory()
    {
        String str="";
        for(int i=0;i<inventory.length;i++)
        {
            if(inventory[i].isUsed)
            {
                str+=(i+1)+". Пусто"+"\n";
            }
            else
            {
                str+=(i+1)+". "+inventory[i].name+"\n";
            }

        }
        return str;
    }

    /**
     * @return - Показывает информацию о персонаже
     */
    public String Character_data()
    {
        String str="";
        str+="Здоровье = "+health+"\n";
        str+="Урон = "+(damage+ weapon.damage)+"\n"+"Оружие:";
        if(!weapon.isUsed)
        {
            str+=weapon.name+"\n";
        }
        else
        {
            str+="Нет"+"\n";
        }
        return str;

    }



}
