package com.company;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//
//


/**
 * Моделирование поведения рыбы
**/
public class Animal extends Organism {
	/**
 	* Количество пищи, которое необходимо съедать рыбе в течение каждой недели
	**/
	private double needEachWeek;
	private double eps = 1e-6;
        /**
 	* Количество пищи, которое съела рыба в течение текущей недели. Значение обнуляется в начале каждой следующей недели
	**/
	private double eatenThisWeek;
	/**
	 * Вызывает конструктор родителя и инициализирует переменные питания
	 *
	 * @param    initRate
	 * @param    initSize
	 * @param    initNeed
	**/
	public Animal(double initRate, double initSize, double initNeed) {
		super(initRate, initSize);
		eatenThisWeek = 0;
		needEachWeek = initNeed;
	}
	
	/**
	 * Вызов функции из класса родителя. Проверяет достаточность съеденной пищи в текущей неделе. Если съеденной пищи недостаточно, то рыба  умирает.
	**/
	public void simulateWeek() {
		if (stillNeed() > eps) death();
	}
	
	public double getNeed() {
		return needEachWeek;
	}
	
	public void setNeed(double newNeed) {
		needEachWeek = newNeed;
	}
	public void setEaten(double eaten) {
		eatenThisWeek = eaten;
	}
	/**
	 * Возвращает количество пищи, которое осталось съесть рыбе в течение текущей недели
	**/
	public double stillNeed() {
		return needEachWeek - eatenThisWeek;
	}
        
	/**
	 * Добавляет к количеству пищи, съеденному в течении текущей недели, новое значение amount
	**/
	public void eat(double amount) {
		eatenThisWeek += amount;
	}
}
