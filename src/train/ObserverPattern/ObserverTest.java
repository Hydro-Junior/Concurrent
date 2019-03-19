package train.ObserverPattern;

import org.junit.Test;

/**
 * @Author: Mr.Xu
 * @Date: Created in 9:13 2019/3/19
 * @Description:
 */
public class ObserverTest {
    @Test
    public void testObserver(){
        Subject subject = new Subject();
        Observer B = new BinaryObserver(subject);
        Observer H = new HexObserver(subject);
        subject.setState(500);//更改状态，即调用通知
    }
}
