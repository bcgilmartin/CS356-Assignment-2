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
class User implements UserGroup {
    private String id;
    private List<String> followersID;
    private List<String> followingID;
    private List<String> tweetsNewsFeed;
    
    public User(String ID) {
        id = ID;
        followingID = new ArrayList<>();
        followersID = new ArrayList<>();
        tweetsNewsFeed = new ArrayList<>();
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
}
