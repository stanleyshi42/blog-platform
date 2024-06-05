package documents;

public class Comment {
    private int id;
    private int postId;
    private String text;
    private int likes;

    public Comment(int id, int postId, String text, int likes) {
        this.id = id;
        this.postId = postId;
        this.text = text;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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


}
