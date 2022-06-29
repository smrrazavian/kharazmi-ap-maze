package Maze;

public class User {
    private String UserName;
    private int Score;

    public User(String UserName) {
        this.UserName = UserName;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getScore() {
        return Score;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }
}
