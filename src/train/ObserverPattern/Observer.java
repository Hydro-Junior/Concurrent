package train.ObserverPattern;

/**
 * @Author: Mr.Xu
 * @Date: Created in 9:05 2019/3/19
 * @Description:
 */
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
