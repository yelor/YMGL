/*
 * 2014 Chengdu JunChen Technology
 */

package com.jskj.asset.client.bean.entity;

import java.util.List;

/**
 *
 * @author 305027939
 */
public class UsertbFindEntity extends Usertb{
    
    private int count;
    
    private  List<Usertb> users;

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the users
     */
    public List<Usertb> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<Usertb> users) {
        this.users = users;
    }
    
}
