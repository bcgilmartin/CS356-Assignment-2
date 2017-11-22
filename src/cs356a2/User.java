/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Blake
 */
class User implements UserGroup {
    private String id;
    private List<String> followersID;
    private List<String> followingID;
    private List<String> tweetsNewsFeed;
    private long timeCreated;
    private long lastUpdate;
    
    public User(String ID) {
        id = ID;
        followingID = new ArrayList<>();
        followersID = new ArrayList<>();
        tweetsNewsFeed = new ArrayList<>();
        timeCreated = System.currentTimeMillis();
        lastUpdate = timeCreated;
    }


    @Override
    public String show(String tabs) {
        return (tabs + "-" + id + "\n");
    }

    @Override
    public String getID() {
        return id;
    }

    void setFollowedBy(String userFollowing) {
        followersID.add(userFollowing);
    }
    
    void setFollowing(String followingUser) {
        followingID.add(followingUser);
    }

    String getFollowing() {
        String following = "";
        for(String follow : followingID) {
            following += follow + "\n";
        }
        return following;
    }

    boolean isFollowing(String userName) {
        return followingID.contains(userName);
    }
    
    List<String> getListFollowing() {
        return followingID;
    }

    String getTimeCreated() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        Date resultdate = new Date(timeCreated);
        return sdf.format(resultdate);
    }
    
    String getTimeLastUpdated() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        Date resultdate = new Date(lastUpdate);
        return sdf.format(resultdate);
    }

    void setTimeLastUpdated(long lastTimeUpdated) {
        lastUpdate = lastTimeUpdated;
    }
    
}
