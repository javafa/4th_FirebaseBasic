package net.flow9.firebasebasic;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

public class User {

    public String user_id;
    public String username;
    public int age;
    public String email;

    @Exclude // database field 에서 제외하고 싶을때 사용
    public boolean check = false;

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