/**
 * @Author: Mr.Xu
 * @Date: Created in 20:29 2018/9/25
 * @Description: 3种方法实现单例，第一种直接new（热加载），第二种synchronized整个方法，实现了懒加载 这里是第三种：双重锁，减小所粒度
 * 第4种：SingleInner
 */
public class Singleton {
    private static Singleton instance;
    private Singleton(){}
    public static Singleton getInstance(){
        if(instance == null){//如果多个线程同时判断出instance为null，都将进入下面的语句，所以同步语句中的再次判断很有必要
            synchronized (Singleton.class){
                if(instance == null){//第二次判断
                    instance  = new Singleton();
                }
            }
        }
        return instance;
    }

}
