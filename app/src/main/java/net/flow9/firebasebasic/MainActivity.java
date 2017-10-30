package net.flow9.firebasebasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
    implements UserAdapter.Callback {

    FirebaseDatabase database;
    DatabaseReference bbsRef;
    DatabaseReference userRef;
    private EditText editId;
    private EditText editName;
    private RecyclerView userList;
    private EditText editTitle;
    private RecyclerView bbsList;

    private UserAdapter userAdapter;
    private BbsAdapter bbsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");
        userRef = database.getReference("user");

        initView();
        initListener();
    }

    // 사용자 등록
    public void signup(View view) {
        String id = editId.getText().toString();
        String name = editName.getText().toString();

        User user = new User(name, 17, "none");
        userRef.child(id).setValue(user);
    }

    public String user_id = "";

    // 게시판 글 등록
    public void post(View view){
        if(user_id == null || "".equals(user_id)){
            Toast.makeText(this, "User를 선택하세요~",Toast.LENGTH_SHORT).show();
        }else {
            String title = editTitle.getText().toString();
            Bbs bbs = new Bbs(title, "내용", "2017-10-30", user_id);

            // 노드를 생성
            String bbsKey = bbsRef.push().getKey();
            // 게시판에 데이터를 입력
            bbsRef.child(bbsKey).setValue(bbs);
            // 사용자 아이디에 게시글 추가
            userRef.child(user_id).child("bbs").child(bbsKey).setValue(bbs);
        }
    }

    private void initView() {
        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        editTitle = findViewById(R.id.editTitle);

        userList = findViewById(R.id.userList);
        bbsList = findViewById(R.id.bbsList);

        userAdapter = new UserAdapter(this);
        userList.setAdapter(userAdapter);
        userList.setLayoutManager(new LinearLayoutManager(this));

        bbsAdapter = new BbsAdapter();
        bbsList.setAdapter(bbsAdapter);
        bbsList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener(){

        // user 노드에 리스너 달기
        userRef.addValueEventListener(new ValueEventListener() {
            // user 노드의 값이 변경되면 발생
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FireBase","=========cnt="+dataSnapshot.getChildrenCount());
                List<User> data = new ArrayList<>(); // 아답터에 입력할 데이터 정의

                // 변경사항이 스냅샷 형태로 넘어오면
                // 해당 스냅샷의 하위 노드를 배열로 꺼내서 사용한다.
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.getKey();
                    Log.d("FireBase","user="+id);

                    // 하위 노드 아래에 리스트 형태의 또 다른 노드가 있으면
                    // 부가적인 처리가 필요하다
                    if(snapshot.hasChild("bbs")){
                        User user = new User();
                        Map map = (HashMap) snapshot.getValue();
                        String name = (String) map.get("username");
                        String email = (String) map.get("email");

                        user.username = name;
                        user.email = email;
                        user.user_id = id;

                        // 하위노드의 스냅샷 꺼내기
                        DataSnapshot bbss = snapshot.child("bbs");
                        user.bbs = new ArrayList();
                        // 하위 노드에 리스트가 존재하면 해당 리스트를
                        // 배열로 꺼내서 위의 방법처럼 사용한다
                        for(DataSnapshot item : bbss.getChildren()) {
                            Bbs bbs = item.getValue(Bbs.class);
                            Log.d("FireBase","Bbs in User===="+bbs.title);
                            user.bbs.add(bbs);
                        }
                        data.add(user);
                    // 하위노드 아래에 키:값 세트만 존재하면 클래스로 바로 컨버팅 할 수 있다.
                    }else{
                        User user = snapshot.getValue(User.class);
                        user.user_id = id;
                        data.add(user);
                    }
                }
                // data 를 아답터에 반영하고 아답터를 notify 한다.
                userAdapter.setDataAndRefresh(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bbsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Bbs> data = new ArrayList<>(); // 아답터에 입력할 데이터 정의

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Bbs bbs = snapshot.getValue(Bbs.class);
                    bbs.id = snapshot.getKey();
                    Log.d("FireBase","bbs="+bbs.id);
                    data.add(bbs);
                }
                // data 를 아답터에 반영하고 아답터를 notify 한다.
                bbsAdapter.setDataAndRefresh(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setUserid(String userid) {
        this.user_id = userid;
    }
}















