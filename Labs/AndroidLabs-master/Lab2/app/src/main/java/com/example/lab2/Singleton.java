package com.example.lab2;

public class Singleton {

    public int position;
    public String[] name;
    public String[] id;

    private static Singleton instance;

    private Singleton(){}

    public static Singleton getInstance(){ // #3
        if(instance == null){		//если объект еще не создан
            instance = new Singleton();	//создать новый объект
            instance.name = new String[200];
            instance.id = new String[200];
        }
        return instance;		// вернуть ранее созданный объект
    }
}
