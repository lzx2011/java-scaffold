package interview.alibaba;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * ClassName: ArrayTest <br/>
 * Function: <br/>
 * question:
 *  数组中有 5 个数,
 *  0 可以重复, 其他数字不重复, 0 可以被任意数字取代
 *  判断 5 个数字是否连续
 *  比如 [0, 0, 3, 2, 5] , 数组中 0 可以被替换成 1 和 4 ,那么这个数组就是连续的了
 * @author gary.liu
 * @date 2017/6/13
 */
public class ArrayTest {

    public static boolean isSeq(List list){

        boolean flag = true;
        int length = list.size();
        list.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return (int)o1 - (int) o2;
            }
        });
        System.out.println(list);

        if((int)list.get(length - 2) == 0 || (int)list.get(length-1) == 0){
            return flag;
        }
        int zeroNum = 0;
        int notZero = 0;
        for(int i = 0; i < list.size(); i++){
            if((int)list.get(i) != 0){
                zeroNum = notZero = i;
                break;
            }
        }
        System.out.println(zeroNum );
        if(zeroNum > 0){
            if((int)list.get(length-1) - (int)list.get(notZero) > (length - notZero)){
                return false;
            }

        }else{
            if(((int)list.get(length-1) - (int)list.get(0)) > 4){
                return false;
            }
        }

        return flag;

    }


    public static void main(String[] args){

        //List list = Arrays.asList(0, 0, 3, 2, 5);
        //List list = Arrays.asList(1, 4, 5, 6, 7);
        List list = Arrays.asList(1, 2, 5, 6, 7);
        System.out.println(isSeq(list));

    }
}
