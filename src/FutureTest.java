import java.util.concurrent.*;

/**
 * @Author: Mr.Xu
 * @Date: Created in 10:54 2018/9/26
 * @Description:
 * Future是submit()的返回值
 * Future模式下，往往用到Callable接口，实现call方法
 * 相比于Runnable，Callable接口有返回值且等抛出异常
 */
public class FutureTest {
    public static void main(String[] args) {
        //FutureTask中指定call方法返回的类型
        FutureTask<Integer> task = new FutureTask<>(()->
        {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });//new Callable(){Integer call();}
        new Thread(task).start();
        try {
            System.out.println(task.get()); //阻塞
            ExecutorService service = Executors.newFixedThreadPool(5);
            Future<Integer> f = service.submit(()->{
                TimeUnit.MILLISECONDS.sleep(500);
                return 1;
            });
            System.out.println(f.isDone());
            System.out.println(f.get());
            System.out.println(f.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
