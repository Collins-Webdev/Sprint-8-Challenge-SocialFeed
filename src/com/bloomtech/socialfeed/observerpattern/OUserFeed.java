package com.bloomtech.socialfeed.observerpattern;
import com.bloomtech.socialfeed.App;
import com.bloomtech.socialfeed.models.Post;
import com.bloomtech.socialfeed.models.User;
import java.util.ArrayList;
import java.util.List;

public class OUserFeed implements Observer {
    private User user;
    private List<Post> feed;

    public OUserFeed(User user) {
        this.user = user;
        this.feed = new ArrayList<>();
        App.sourceFeed.attach(this);
        update();
    }

    @Override
    public void update() {
        feed.clear();
        for (Post post : App.sourceFeed.getPosts()) {
            if (user.getFollowing().contains(post.getUsername())) {
                feed.add(post);
            }
        }
    }

    public User getUser() {
        return user;
    }

    public List<Post> getFeed() {
        return feed;
    }
}