package com.lzhenxing.javascaffold.entity;

import java.util.List;

/**
 *
 * ClassName: Manager <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/27
 */
public class Manager {

    private String name;

    private int age;

    private List<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
