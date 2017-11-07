/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Blake
 */
public class Twitter implements Subject {
    
    protected static Twitter instance;
    private List<Observer> observers;
    
    public Twitter() { }
    
    public static Twitter getInstance() {
        if (instance == null) {
                instance = new Twitter();
        }
        return instance;
    }
    
    private UserGroupTree userGroupTree;
    
    public void add(UserGroup userGroup) {
        if(userGroupTree == null) {
            userGroupTree = new UserGroupTree();
        }
        userGroupTree.add(userGroup);
        notifyObservers();
    }
    

    boolean contains(String name) {
        if(userGroupTree == null) {
            userGroupTree = new UserGroupTree();
        }
        return userGroupTree.contains(name);
    }

    
    
    @Override
    public void registerObserver(Observer o) {
        if(observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(o);
    }

    @Override
    public void unregisterObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        if(observers == null) {
            observers = new ArrayList<>();
        }
        for(Observer observer : observers) {
            observer.update(userGroupTree);
        }
    }

    boolean isUser(String openUserName) {
        return userGroupTree.isUser(openUserName);
    }

    
    public UserGroupTree getTree() {
        if(userGroupTree == null) {
            userGroupTree = new UserGroupTree();
        }
        return userGroupTree;
    }

    User getUser(String name) {
        if(userGroupTree.contains(name)) {
            return userGroupTree.getUser(name);
        }
        return null;
    }

    public void follow(String user, String userFollowed) {
        userGroupTree.follow(user, userFollowed);
        notifyObservers();
    }
    
    void tweet(String id, String tweetMsg) {
        userGroupTree.addTweet(new Tweet(id, tweetMsg));
        notifyObservers();
    }
    
}
