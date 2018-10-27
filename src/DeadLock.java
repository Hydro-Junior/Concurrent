
/**
 * @Author: Mr.Xu
 * @Date: Created in 10:25 2018/9/23
 * @Description: 死锁模拟
 */
public class DeadLock implements Runnable {
    public static final Object AAA = new Object(); //静态final类作为共享资源
    public static final Object BBB = new Object();
    boolean flag;
    public DeadLock(boolean flag) {
        this.flag = flag;
    }
    public static void main(String[] args) {
        DeadLock A = new DeadLock(true);
        DeadLock B = new DeadLock(false);
        while (true){
            new Thread(A).start();
            new Thread(B).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void run() {
        if(flag)
            synchronized (AAA) { //嵌套式锁定对象
                System.out.println("if  lock  AAA");
                //此处可以添加休眠
                synchronized (BBB) {
                    System.out.println("if   lock  BBB");
                }
            }
        else{
            synchronized (BBB) {
                System.out.println("else   lock  BBB");
                //此处可以添加休眠
                synchronized (AAA) {
                    System.out.println("else   lock  AAA");
                }
            }
        }
    }
}