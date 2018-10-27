import java.util.concurrent.TimeUnit;

/**
 * @Author: Mr.Xu
 * @Date: Created in 19:10 2018/9/25
 * @Description: 线程的局部变量，保证数据安全，相当于公共对象在当前线程的拷贝了。
 *  ThreadLocal 相当于使用空间换取时间，而synchronized相当于时间换空间
 *  比如Hibernate中的session就存在于ThreadLocal中，避免synchronized的使用
 *
 *  ThreadLocal 实现机理，一个ThreadLocalMap与线程一一对应，set()方法就是往map中放对象map.set(this,value),
 *  get()方法也是先取得当前线程的ThreadLocalMap对象，再通过将自己作为key取得内部的实际数据。
 *  ThreadLocal有可能导致内存泄漏（线程不退出，Thread类不会做清理工作，尤其是固定大小的线程池这种情况），
 *  因此，如果希望即时回收这个对象，最好使用ThreadLocal.remove()这个方法。
 *
 *  (ThreadLocalMap 的实现使用了弱引用，Java虚拟机在垃圾回收时，如果发现弱引用，就会立即回收。
 *  ThreadLocalMap内部由一系列Entry构成，每个Entry都是WeakReference<ThreadLocal>)
 */
public class UseThreadLocal {
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());//这里输出为null，因为Thread2中的new Person只属于Thread2
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }

    static class Person{
        String name = "Jack";
    }
}
