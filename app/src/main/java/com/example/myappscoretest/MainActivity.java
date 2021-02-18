package com.example.myappscoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText kor;
    EditText eng;
    EditText math;

    TextView resultView;

    DatabaseHelper dbHelper;

    //데이타베이스 변수 생성
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        kor = findViewById(R.id.kor);
        eng = findViewById(R.id.eng);
        math = findViewById(R.id.math);

        resultView = findViewById(R.id.resultView);

        //데이타베이스 생성여부 확인
        if(database == null){
            //데이타베이스, 테이블 생성
            dbHelper = new DatabaseHelper(this);
            Log.i("MyTAG", dbHelper+"생성됨");
        }

        //입력버튼을 클릭했을 때
        Button insertBtn = findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(v -> {

            //데이타베이스 생성여부 확인
            if(database == null) {
                Log.i("MyTAG", "디비가 없다!");

                //데이타베이스, 테이블 생성
                dbHelper = new DatabaseHelper(this);
                Log.i("MyTAG", dbHelper+"생성됨");
                database = dbHelper.getWritableDatabase();
                Log.i("MyTAG", database+"생성됨");

                //입력받은 값으로 insert 실행
                String nameStr = name.getText().toString();
                int korScore = Integer.parseInt(kor.getText().toString());
                int engScore = Integer.parseInt(eng.getText().toString());
                int mathScore = Integer.parseInt(math.getText().toString());

                    String sql = "insert into score(name, kor, eng, math) "
                            +" values('"+nameStr+"', "+korScore+", "+engScore+", "+mathScore+")";

                    database.execSQL(sql);

                    Log.i("MyTAG", "데이타를 추가함");

                Toast.makeText(getApplicationContext(), "입력완료", Toast.LENGTH_SHORT).show();
            }else{

                //데이터베이스가 생성되어 있다면 입력받은 값 insert실행
                String nameStr = name.getText().toString();
                int korScore = Integer.parseInt(kor.getText().toString());
                int engScore = Integer.parseInt(eng.getText().toString());
                int mathScore = Integer.parseInt(math.getText().toString());

                String sql = "insert into score(name, kor, eng, math) "
                        +" values('"+nameStr+"', "+korScore+", "+engScore+", "+mathScore+")";

                database.execSQL(sql);

                Log.i("MyTAG", "데이타를 추가함");
                Toast.makeText(getApplicationContext(), "입력완료", Toast.LENGTH_SHORT).show();
            }
        });

        //조회버튼 클릭했을 때
        Button selectBtn = findViewById(R.id.selectBtn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //resultSet 대신 Cursor를 사용
                String sql = "select _id, name, kor, eng, math from score";
                database = dbHelper.getWritableDatabase();
                Cursor cursor = database.rawQuery(sql, null);
                int recordCount = cursor.getCount();
                Log.i("MyTAG", "레코드 개수: "+recordCount);
                String resultStr = "";
                resultView.setText(null);

                while (cursor.moveToNext()) {

                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    int kor = cursor.getInt(2);
                    int eng = cursor.getInt(3);
                    int math = cursor.getInt(4);

                    resultStr += "====================" + "\n"
                            +"학생 아이디 #" + id +"\n"
                            +"이름: "+name+"\n"
                            +"국어점수: "+kor+"\n"
                            +"영어점수: "+eng+"\n"
                            +"수학점수: "+math+"\n";

                    resultView.setText(resultStr);

//                    resultView.append("학생 아이디 #" + id +"\n"
//                            +"이름: "+name+"\n"
//                            +"국어점수: "+kor+"\n"
//                            +"영어점수: "+eng+"\n"
//                            +"수학점수: "+math+"\n");
                }
                cursor.close();
            }
        });
    }
}