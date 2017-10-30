package net.flow9.firebasebasic;

public class Bbs {
    public String id;
    public String title;
    public String content;
    public String date;
    public String user_id;

    public Bbs(){
        // 파이어베이스에서 parsing 할때 한번 호출한다.
    }

    public Bbs(String title, String content, String date, String user_id){
        this.title = title;
        this.content = content;
        this.date = date;
        this.user_id = user_id;
    }
}
