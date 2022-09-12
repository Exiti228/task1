package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Main {
    static int MANY_WEEDS = 2000; // Количество растений в пруду
    static double WEED_SIZE = 15; // Начальный размер растения
    static double WEED_RATE = 2.5; // Скорость роста растения
    static int INIT_FISH = 300; // Начальный размер популяции рыб
    static double FISH_SIZE = 50; // Размер рыб
    static double FRACTION = 0.5; // Рыба каждую неделю должна съедать объем равный FRACTION * FISH_SIZE
    static int AVERAGE_NIBBLES = 30; //Среднее количество растений, частично съедаемых рыбами в течение недели равно
    //произведению AVERAGE_NIBBLES на размер популяции рыб
    static double BIRTH_RATE = 0.05; // Общее количество новых рыб равно произведению размера популяции на константу BIRTH_RATE

    static ArrayList<Plant> weeds = new ArrayList(MANY_WEEDS);
    static ArrayList<Herbivore> fish = new ArrayList(INIT_FISH);
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int manyWeeks;   // Количество недель для моделирования
        int i;

        System.out.println("How many weeks shall I simulate? ");
        manyWeeks = in.nextInt();

        //Создание популяции травоядных рыб
        for (i = 0;i < INIT_FISH; i++)
            fish.add(new Herbivore (0, FISH_SIZE, FISH_SIZE * FRACTION));

        //Создание популяции растений
        for (i=0;i<MANY_WEEDS;i++)
            weeds.add(new Plant(WEED_RATE, WEED_SIZE));

        //Моделирование жизни в пруду
        for (i = 0; i < manyWeeks; i++){
            pondWeek();
            System.out.println((i+1)+"\t"+fish.size() + "\t"+(int)totalMass(weeds) );
        }
    }
    static void pondWeek(){

        int manyIterations = AVERAGE_NIBBLES * fish.size( );
        int[][] fishAndPlant = new int[manyIterations][2];
        for (int i = 0; i < manyIterations; ++i)
        {
            //Случайный выбор рыбы и растения для питания
            //Code here...
            fishAndPlant[i][0] = (int)(random() * fish.size());
            fishAndPlant[i][1] = (int)(random() * weeds.size());
        }

        //Моделирование одной недели для растений
        for (Plant weed : weeds)
            weed.simulateWeek();

        //Моделирование одной недели для рыб с удалением из популяции умерших рыб
        for (int i = 0; i < manyIterations; ++i) {
            fish.get(fishAndPlant[i][0]).nibble(weeds.get(fishAndPlant[i][1]));
        }
        for (var f : fish){
            f.simulateWeek();
            //System.out.println(f.stillNeed());
        }
        fish = fish.stream().peek(Animal::simulateWeek).filter(Herbivore::isAlive).peek(x -> x.setEaten(0)).collect(Collectors.toCollection(ArrayList::new));
      //  System.out.println(fish.size());

        //Добавление в популяцию новых рыб
        int newFishPopulation = (int)(BIRTH_RATE * fish.size());

        for (int i = 0; i < newFishPopulation; i++) {
            fish.add(new Herbivore (0, FISH_SIZE, FISH_SIZE * FRACTION));
        }
       // System.out.println(fish.size());

    }

    /**
     * Постусловие: Возвращает массу всех растений в списке weeds.
     **/
    static double totalMass(ArrayList<Plant> weeds){
        return weeds.stream().map(Organism::getSize).reduce(0d, Double::sum);
    }
}
