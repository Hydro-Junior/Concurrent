/**
 * @Author: Mr.Xu
 * @Date: Created in 20:36 2018/9/25
 * @Description:既不用加锁，也能实现懒加载的前提下实现单例模式，推荐使用
 * 使用嵌套类
 */
public class SingletonInner {
    private SingletonInner(){
        System.out.println("single");
    }
    private static class Inner{
        private static SingletonInner s = new SingletonInner();
    }
    public static SingletonInner getSingle(){
        return Inner.s;//调用时初始化Inner
    }
}
