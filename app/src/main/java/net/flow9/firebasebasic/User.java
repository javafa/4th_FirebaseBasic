package net.flow9.firebasebasic;

import java.util.List;

/**
 * Created by pc on 10/30/2017.
 */

public class User {

    public String user_id;
    public String username;
    public int age;
    public String email;

    // 내가 작성한 글 목록
    public List<Bbs> bbs;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, int age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
    }
}