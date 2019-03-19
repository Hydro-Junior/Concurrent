package train.ObserverPattern;

/**
 * @Author: Mr.Xu
 * @Date: Created in 9:07 2019/3/19
 * @Description:特定观察者：二进制形式
 */
public class BinaryObserver extends Observer{
    public BinaryObserver(Subject subject){
        //注意构造函数中的两句话，即相互持有引用
        this.subject = subject;
        this.subject.add(this);
    }
    public void update(){
        System.out.println("Binary String:"
        +Integer.toBinaryString(subject.getState()));
    }
}
