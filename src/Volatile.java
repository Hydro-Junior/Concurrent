import java.util.concurrent.TimeUnit;

/**
 * @Author: Mr.Xu
 * @Date: Created in 11:16 2018/9/23
 * @Description: volatile 表示内存可见性，看到这个关键字脑海里要浮现JMM（java 内存模型）的两个部分：主内存和线程各自内存（与CPU的缓冲区）
 * 如果要一个线程在另一个线程运行时修改某个属性值，需要用到volatile保证内存的可见性。
 * volatile的含义在于：修改某个变量会通知持有该变量的其他线程缓冲区，它们的值已经过期，需要重新到主存中获取。
 * volatile 相较于锁更为轻量，效率要高，但并不能保证原子操作。
 * volatile 并不能保证多个线程共同修改running变量时带来的不一致问题，也就是说volatile不能替代synchronized
 */
public class Volatile {
    /*volatile*/ boolean running = true;
    void m() {
        System.out.println("m start");
        while (running) { //不修改running，改方法将一直执行

        }
        System.out.println("m end!");
    }
    public static void main(String[] args){
        Volatile v = new Volatile();
        new Thread(v::m ,"v1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        v.running = false; //running不用volatile修饰将无法停止线程
    }
}
