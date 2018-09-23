package com.lzhenxing.javascaffold.javabase.collections.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.lzhenxing.javascaffold.entity.User;
import org.junit.Test;

import static java.util.Comparator.comparingLong;

import static java.util.stream.Collectors.collectingAndThen;

import static java.util.stream.Collectors.toCollection;

/**
 * ClassName: StreamPractice <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/7
 */
public class StreamPractice {

    /**
     * 集合求和
     */
    public static void sum() {
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            list.add(50);
        }
        int total = list.stream().mapToInt(i -> i).sum();
        System.out.println(total);
        System.out.println(list.toString());
    }

    /**
     * filter empty string from List
     * https://stackoverflow.com/questions/40605998/java-8-filter-empty-string-from-list-not-working?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     */
    @Test
    public void removeEmpty(){
        List<String> list = Lists.newArrayList();
        list.add("3");
        list.add("");
        list.add("5");
        //This will also not modify the original list. It will create a filtered copy of the original list. So you need
        list = list.stream().filter(item-> !item.isEmpty()).collect(Collectors.toList());

        //If you want to modify the original list, you should use
        list.removeIf(item -> item.isEmpty());
        list.removeIf(String::isEmpty);
    }

    public static void distinctByKey(){
        User user = new User();
        user.setName("liu");
        user.setAge(10);
        User user1 = new User();
        user1.setName("gary");
        user1.setAge(10);
        List<User> users = Lists.newArrayList();
        users.add(user);
        users.add(user1);
        System.out.println(users.toString());

        List<User> unique = users.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(User::getAge))), ArrayList::new));

        System.out.println(unique.toString());

    }

    public static void main(String[] args) {
        //sum();
        distinctByKey();
    }
}
