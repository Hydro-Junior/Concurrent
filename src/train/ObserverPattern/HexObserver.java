package train.ObserverPattern;

/**
 * @Author: Mr.Xu
 * @Date: Created in 9:11 2019/3/19
 * @Description:特定观察者：十六进制形式
 */
public class HexObserver extends Observer {
    HexObserver(Subject subject){
        this.subject = subject;
        subject.add(this);
    }
    @Override
    public void update() {
        System.out.println("Hex String:" + Integer.toHexString(subject.getState()).toUpperCase());
    }
}
