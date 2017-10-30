package net.flow9.firebasebasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference bbsRef;
    DatabaseReference userRef;
    private EditText editId;
    private EditText editName;
    private RecyclerView userList;
    private EditText editTitle;
    private RecyclerView bbsList;

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

    private void initView() {
        editId = (EditText) findViewById(R.id.editId);
        editName = (EditText) findViewById(R.id.editName);
        editTitle = (EditText) findViewById(R.id.editTitle);

        userList = (RecyclerView) findViewById(R.id.userList);
        bbsList = (RecyclerView) findViewById(R.id.bbsList);
    }

    private void initListener(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> data = new ArrayList<>(); // 아답터에 입력할 데이터 정의

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    data.add(user);
                }

                data 를 아답터에 반영하고;
                아답터를 notify 한다.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}















