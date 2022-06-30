package Maze;

import java.io.FileWriter;
import java.io.IOException;

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

    public void setUserName(String userName) throws IOException{
        UserName = userName;
        try {
            FileWriter Writer = new FileWriter("../assets/users.txt");
            Writer.write(UserName + "#" + 0);
            Writer.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

    }

    public String getUserName() {
        return UserName;
    }
}
