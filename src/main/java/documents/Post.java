package documents;

import java.util.ArrayList;

public class Post {
    private String id;
    private String username;
    private String text;
    private int likes;
    private ArrayList<String> tags;
    private ArrayList<String> comments;

    public Post(String username, String text, int likes, ArrayList<String> tags, ArrayList<String> comments) {
        this.username = username;
        this.text = text;
        this.likes = likes;
        this.tags = tags;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "username='" + username + '\'' +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                ", tags=" + tags +
                ", comments=" + comments +
                '}';
    }
}
