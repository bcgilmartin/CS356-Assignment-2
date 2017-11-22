/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Blake
 */
public class Group implements UserGroup {
    private String id;
    private List<User> users;
    private long timeCreated;

    public Group(String id) {
        this.id = id;
        users = new ArrayList<>();
        timeCreated = System.currentTimeMillis();
    }
    
    public void addUser(User user) {
        if(!users.contains(user)) {
            users.add(user);
        }
    }
    
    @Override
    public String show(String tabs) {
        return (tabs + "(Group)" + id + "\n");
    }

    @Override
    public String getID() {
        return id;
    }
    
    
}
