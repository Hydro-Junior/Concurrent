package train.ByteDance20190316;

/**
 * @Author: Mr.Xu
 * @Date: Created in 10:09 2019/3/16
 * @Description:对昨天第三题的一个解法的猜测，分享下想法
 * （1）分数最低的那个礼品数必是1，且作为队列头尾构成环 比如有分数 5 5 5 5 4 3 2 6 应该从 2 排起--->2 6 5 5 5 5 4 3 (2)
 *  (2) 将最左值设为1，从左到右遍历，比前面分高的，礼品数比前面多1，比前面分低的或相同的，礼品数直接置1
 * （3）将最右值设为1，从右到左遍历，与（2）同理    （这样一来，增量关系会被保留）
 * （4）取（2）（3）两个数组每个位置的最大值，就是最终的礼品数，因为首尾是同一个，记得sum-1.
 *  举例1：5 5 5 5 4 3 2 6
 *  调整位置： 2 6 5 5 5 5 4 3（2）
 *  从左到右： 1 2 1 1 1 1 1 1  1
 *  从右到左： 1 2 1 1 1 4 3 2  1（请从右向左看）
 *  合并：     1 2 1 1 1 4 3 2（1） （最终礼品数）
 *
 *  举例2：2 7 7 7 6 5 5 7 2 1 4 8 9
 *  调整位置： 1  4  8  9  2  7  7  7  6  5  5  7  2 （1）
 *  从左到右： 1  2  3  4  1  2  1  1  1  1  1  2  1  1
 *  从右到左： 1  1  1  2  1  1  1  3  2  1  1  3  2  1
 *  合并：     1  2  3  4  1  2  1  3  2  1  1  3  2（1） 最终礼品数
 *  仅仅是猜测，目前尚未找到反例。
 */
import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();//样例个数
        while(q-- > 0){
            int n = sc.nextInt();
            int s[] = new int[n];//分数数组
            int loop[] = new int[n+1];
            int minIdx = 0, min = Integer.MAX_VALUE;
            for(int i = 0 ; i < n; i++){
                s[i] = sc.nextInt();
                if(s[i] < min){
                    min = s[i];
                    minIdx = i;
                }
            }
            int k = 0;
            for(int i = minIdx; i < n ;i++) loop[k++] = s[i];
            for(int i = 0; i <= minIdx; i++) loop[k++] = s[i];
            int[] cntLeft = new int[n+1]; cntLeft[0] = 1;
            int[] cntRight = new int[n+1]; cntRight[n] = 1;
            //从左侧循环
            for(int i = 1 ; i < n; i++){
               if(loop[i] > loop[i-1]) {
                   cntLeft[i] = cntLeft[i-1] + 1;
               }else{
                   cntLeft[i] = 1;
               }
            }
            //从右侧循环
            for(int i = n-1 ; i > 0; i--){
                if(loop[i] > loop[i+1]) {
                    cntRight[i] = cntRight[i+1] + 1;
                }else {
                    cntRight[i] = 1;
                }
            }
            int sum = 0;
            for(int i = 0 ; i <= n ; i++) {
                sum += Math.max(cntLeft[i],cntRight[i]);
            }
            System.out.println(sum - 1);
        }
    }

}
