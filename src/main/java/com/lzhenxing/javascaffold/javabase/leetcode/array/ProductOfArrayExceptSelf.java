package com.lzhenxing.javascaffold.javabase.leetcode.array;

/**
 * Created by gouthamvidyapradhan on 04/05/2017.
 *
 * <p>Given an array of n integers where n > 1, nums, return an array output such that output[i] is
 * equal to the product of all the elements of nums except nums[i].
 *
 * <p>Solve it without division and in O(n).
 *
 * <p>For example, given [1,2,3,4], return [24,12,8,6].
 *
 * <p>Follow up: Could you solve it with constant space complexity? (Note: The output array does not
 * count as extra space for the purpose of space complexity analysis.)
 */
public class ProductOfArrayExceptSelf {
  public static void main(String[] args) {
    int[] nums = {1, 2, 3, 4};
    int[] result = new ProductOfArrayExceptSelf().productExceptSelf(nums);
    for (int r : result) System.out.print(r + " ");
  }

    /**
     * ��������ķ�����
     * @param nums
     * @return
     */
  public int[] productExceptSelf(int[] nums) {
    int[] result = new int[nums.length];
    //result[i] �Ǵӵ�һ����ǰ��һ�����ĳ˻�
    for (int i = 0, temp = 1, l = nums.length; i < l; i++) {
      result[i] = temp;
      temp *= nums[i];
    }
    //result[i] �Ǵ����һ����ǰ��һ�����ĳ˻�
    for (int i = nums.length - 1, temp = 1; i >= 0; i--) {
      result[i] = result[i] * temp;
      temp *= nums[i];
    }
    return result;
  }


    /**
     * ����һ��O(n*n)
     *
     * ��������(���ʹ�ó����������ܳ˻����ٳ���ÿ�����Ӧ��λ����)
     * @param nums
     * @return
     */
  public int[] test(int[] nums){
    int[] result = new int[nums.length];
    for(int i = 0 ; i < nums.length; i++){
        int temp = 1;
        for(int j = 0; j < nums.length; j++){
          if(i != j){
              temp *= nums[j];
          }
          result[i] = temp;
      }
    }
    return result;
  }
}
