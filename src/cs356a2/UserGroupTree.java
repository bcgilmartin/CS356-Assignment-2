/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Blake
 */
public class UserGroupTree implements UserGroup, Item {

    private List<UserGroup> userGroupList;
    private List<Tweet> tweets;
    private String[] positiveWords = new String[]{"good", "great", "nice", "awesome"};
    
    public UserGroupTree() {
        userGroupList = new ArrayList<>();
        userGroupList.add(new Group("Root"));
    }
    
    @Override
    public String show(String tabs) {
        String treeText = "";
        for(UserGroup userGroup : userGroupList) {
            treeText += userGroup.show(tabs);
            if(userGroup instanceof Group) {
                tabs = tabs + "     ";
            }
        }
        return treeText;
    }
    
    public void add(UserGroup userGroup) {
        userGroupList.add(userGroup);
    }

    boolean contains(String name) {
        for(UserGroup userGroup : userGroupList) {
            if(userGroup.getID().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    //Not needed
    @Override
    public String getID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean isUser(String openUserName) {
        for(UserGroup userGroup : userGroupList) {
            if(userGroup.getID().equals(openUserName)) {
                if(userGroup instanceof User) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    int showUserTotal() {
        int totalUsers = 0;
        for(UserGroup userGroup : userGroupList) {
            if(userGroup instanceof User) {
                totalUsers++;
            }
        }
        return totalUsers;
    }

    int showGroupTotal() {
        int totalGroups = 0;
        for(UserGroup userGroup : userGroupList) {
            if(userGroup instanceof Group) {
                totalGroups++;
            }
        }
        return totalGroups;
    }

    @Override
    public int accept(Visitor visitor) {
        return visitor.visit(this);
    }
    
    public User getUser(String name) {
        for(UserGroup userGroup : userGroupList) {
            if(userGroup.getID().equals(name) && userGroup instanceof User) {
                return (User) userGroup;
            }
        }
        return null;
    }

    void follow(String userID, String userFollowedID) {
        User userFollowing = null, userFollowed = null;
        for(UserGroup userGroup : userGroupList) {
            if(userGroup.getID().equals(userID)) {
                userFollowing = (User) userGroup; 
                userFollowing.setFollowing(userFollowedID);
                if(userFollowed != null) {
                    return;
                }
            } else if(userGroup.getID().equals(userFollowedID)) {
                userFollowed = (User) userGroup;
                userFollowed.setFollowedBy(userID);
                if(userFollowing != null) {
                    return;
                }
            }
        }
    }

    String showFollowed(String userID) {
        for(UserGroup userGroup : userGroupList) {
            if(userGroup.getID().equals(userID)) {
                User user = (User)userGroup;
                return user.getFollowing();
            }
        }
        return "";
    }

    String getNewsFeed(String userID) {
        List<String> userFollowing;
        String newsFeedString = "";
        if(tweets == null) {
            tweets = new ArrayList();
        }
        for(UserGroup userGroup : userGroupList) {
            if(userGroup.getID().equals(userID)) {
                User user = (User)userGroup;
                userFollowing = user.getListFollowing();
                for(Tweet tweet : tweets) {
                    if(userFollowing.contains(tweet.getSenderID()) || tweet.getSenderID().equals(userID)) {
                        newsFeedString += "___________________________________________\n";
                        newsFeedString += tweet.getSenderID() + ": " + tweet.getTweetMsg() + "\n";
                    }
                }
                newsFeedString += "___________________________________________\n";
                return newsFeedString;
            }
        }
        return null;
    }

    void addTweet(Tweet tweet) {
        if(tweets == null) {
            tweets = new ArrayList<>();
        }
        tweets.add(tweet);
    }

    int showTweetTotal() {
        if(tweets == null) {
            tweets = new ArrayList<>();
        }
        return tweets.size();
    }

    public int showPositivePercentage() {
        if(tweets == null) {
            tweets = new ArrayList<>();
        }
        int positive = 0;
        String message;
        for(Tweet tweet : tweets) {
            message = tweet.getTweetMsg().toLowerCase();
            if(Arrays.stream(positiveWords).parallel().anyMatch(message::contains)) {
                positive++;
            }
        }
        return (int)(((float)positive/(float)tweets.size())*100);
    }
    
}
