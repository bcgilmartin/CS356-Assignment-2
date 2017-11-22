/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356a2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Blake
 */
public class UserGroupTree implements UserGroup, Item {

    private List<UserGroup> userGroupList;
    private List<Tweet> tweets;
    private String[] positiveWords = new String[]{"good", "great", "nice", "awesome"};
    private String lastUpdatedUser;
    
    public UserGroupTree() {
        userGroupList = new ArrayList<>();
        userGroupList.add(new Group("Root"));
        lastUpdatedUser = "No one updated yet!";
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
                Object[] following = user.getListFollowing().toArray();
                String[] timeUpdated = getTimeUpdated(following);
                return compileString(following, timeUpdated);
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
        lastUpdatedUser = tweet.getSenderID();
        for(UserGroup curr : userGroupList) {
            if(curr.getID().equals(tweet.getSenderID())) {
                tweets.add(tweet);
                User currUser = (User)curr;
                currUser.setTimeLastUpdated(System.currentTimeMillis());
                return;
            }
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

    public int getUnverified() {
        int unverified = 0;
        Set<String> IDs = new HashSet<>();
        for(UserGroup curr : userGroupList) {
            if(IDs.contains(curr.getID())) {
                unverified++;
                System.out.println("This ID is used multiple times: " + curr.getID());
                if(curr.getID().contains(" ")) {
                    System.out.println("This ID ALSO has a space in it: " + curr.getID());
                }
            } else if(curr.getID().contains(" ")) {
                unverified++;
                System.out.println("This ID has a space in it: " + curr.getID());
            }
            IDs.add(curr.getID());
        }
        return unverified;
    }

    private String[] getTimeUpdated(Object[] following) {
        String[] updatedTimes = new String[following.length];
        for(int i = 0; i < following.length; i++) {
            for(UserGroup users : userGroupList) {
                if(users.getID().equals(following[i]) && users instanceof User) {
                    User currUser = (User)users;
                    updatedTimes[i] = currUser.getTimeLastUpdated();
                }
            }
        }
        return updatedTimes;
    }

    private String compileString(Object[] following, String[] timeUpdated) {
        String followString = "";
        for(int i = 0; i < following.length; i++) {
            followString += following[i] + "   Last Updated: " + timeUpdated[i] + "\n";
        }
        return followString;
    }

    String getLastUpdatedTime(String userID) {
        for(UserGroup curr : userGroupList) {
            if(curr.getID().equals(userID)) {
                User currUser = (User)curr;
                return currUser.getTimeLastUpdated();
            }
        }
        return "";
    }

    void getLastUpdated() {
        System.out.println(lastUpdatedUser);
    }
    
}
