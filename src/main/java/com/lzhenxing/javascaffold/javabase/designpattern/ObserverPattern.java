package com.lzhenxing.javascaffold.javabase.designpattern;

import java.util.Observable;
import java.util.Observer;

/**
 * ClassName: ObserverPattern <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/3
 */
public class ObserverPattern {

    public static void main(String[] args){

        ConcreteObservable observable = new ConcreteObservable();
        ConcreteObserver1 concreteObserver1 = new ConcreteObserver1();
        ConcreteObserver2 concreteObserver2 = new ConcreteObserver2();
        observable.addObserver(concreteObserver1);
        observable.addObserver(concreteObserver2);

        observable.notifyObservers("状态发生变化,新状态是 ABC");

    }

}

/**
 * 具体被观察者
 */
class ConcreteObservable extends Observable{

    public ConcreteObservable(){
        super();
        setChanged();  //改变被观察者对象的状态,才会触发回调
    }

}

/**
 * 具体观察者
 */
class ConcreteObserver1 implements Observer{

    @Override
    public void update(Observable observable, Object object){
        System.out.println("观察者ConcreteObserver1 状态: " + object.toString());
    }
}

class ConcreteObserver2 implements Observer{

    @Override
    public void update(Observable observable, Object object){
        System.out.println("观察者ConcreteObserver2 状态: " + object.toString());
    }
}