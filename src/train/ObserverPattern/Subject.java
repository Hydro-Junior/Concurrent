package train.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.Xu
 * @Date: Created in 8:58 2019/3/19
 * @Description:观察者模式的主题
 * 观察者模式设计思想，主题持有所有观察者的引用，当主题发生改变时，调用每个观察者特定的update方法，每个观察者就有各自的反应。
 * 当然，每个观察者都实现同一个抽象类Observer。
 */
public class Subject {
    List<Observer> observers;
    private int state; //每个观察者所关心的状态
    Subject(){
        observers = new ArrayList<>();
        state = 0;
    }
    public int getState(){
        return state;
    }
    public void add(Observer observer){
        observers.add(observer);
    }
    public void setState(int a){
        this.state = a;
        notifyAllObservers();
    }
    private void notifyAllObservers(){
        for(Observer ob : observers){
            ob.update();
        }
    }
}
