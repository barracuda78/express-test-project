package ru.eforward.express_testing;


import ru.eforward.express_testing.model.school.Test;
import ru.eforward.express_testing.testingProcess.TestingUnit;
import ru.eforward.express_testing.utils.CloneMaker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Cat vaska = new Cat("Vaska","Gray",4);
        List<String> friends = new ArrayList<>();
        friends.add("Fedya");
        friends.add("Masha");
        vaska.friends = friends;

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream ous = new ObjectOutputStream(baos);
//        //сохраняем состояние кота Васьки в поток и закрываем его(поток)
//        ous.writeObject(vaska);
//        ous.close();
//        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//        ObjectInputStream ois = new ObjectInputStream(bais);
        //создаём кота для опытов и инициализируем его состояние Васькиным
        //Cat cloneVaska = (Cat)ois.readObject();
        Cat cloneVaska = CloneMaker.getClone(vaska);
        System.out.println(vaska);
        System.out.println(cloneVaska);
        System.out.println("*********************************************");
        cloneVaska.setColor("Black");
        cloneVaska.friends.set(0,"другой товарисч");
        //Убеждаемся что у кота Васьки теперь есть клон, над которым можно ставить опыты без ущерба Василию
        System.out.println(vaska);
        System.out.println(cloneVaska);




    }
}

class Cat implements Serializable {
    private String name;
    private String color;
    private int age;
    public List<String> friends;

    public Cat(String name, String color, int age) {
        this.name = name;
        this.color = color;
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", age=" + age +
                ", friends=" + friends +
                '}';
    }
}
