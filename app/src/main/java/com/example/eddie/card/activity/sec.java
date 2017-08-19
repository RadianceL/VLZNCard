package com.example.eddie.card.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eddie.card.MyAdapter.UnitAdapter;
import com.example.eddie.card.MyAdapter.UnitList;
import com.example.eddie.card.MyDatabase.MyDatabaseHelper;
import com.example.eddie.card.PoJo.Title;
import com.example.eddie.card.PoJo.UnitNumber;
import com.example.eddie.card.PoJo.card;
import com.example.eddie.card.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.eddie.card.R.id.show;
import static com.example.eddie.card.R.id.unit;
import static com.example.eddie.card.R.id.withText;

public class sec extends AppCompatActivity {
    
    private EditText edie_text,unit;
    private TextView tv;
    private CheckBox box1,box2,box3,box4,box5,box6,box7,box8;
    private static final int[] numbers= {1,2,4,8,16,32,64,128};


    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase db;

    private Spinner sp1;

    private ListView showUnit;
    private UnitAdapter unitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        myDatabaseHelper = new MyDatabaseHelper(this,"CardInfo.db",null,1);
        db = myDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name","成龙花园");
        db.insert("community",null,values);

        init();

    }

    private void init() {
        edie_text = (EditText) findViewById(R.id.number);
        box1 = (CheckBox) findViewById(R.id.box1);
        box2 = (CheckBox) findViewById(R.id.box2);
        box3 = (CheckBox) findViewById(R.id.box3);
        box4 = (CheckBox) findViewById(R.id.box4);
        box5 = (CheckBox) findViewById(R.id.box5);
        box6 = (CheckBox) findViewById(R.id.box6);
        box7 = (CheckBox) findViewById(R.id.box7);
        box8 = (CheckBox) findViewById(R.id.box8);

        tv= (TextView) findViewById(show);

        unit = (EditText) findViewById(R.id.unit);
        showUnit = (ListView) findViewById(R.id.showUnit);
        sp1 = (Spinner) findViewById(R.id.community);


        List<Object> adapterList = initInfo();
        unitAdapter = new UnitAdapter(this,R.layout.title_item,adapterList);
        showUnit.setAdapter(unitAdapter);


        List<String> list = SearchCommunityInfo();
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,list);
        sp1.setAdapter(adapter);

        TextView tv = (TextView) findViewById(R.id.content);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShowDialog();
            }
        });
    }

    private void mShowDialog() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("沈阳维力智能设备有限公司");
        build.setMessage("联系人：李为民\n电话：13386836511");
        build.show();
    }

    public void up(View view) {

        box1.setChecked(false);
        box2.setChecked(false);
        box3.setChecked(false);
        box4.setChecked(false);
        box5.setChecked(false);
        box6.setChecked(false);
        box7.setChecked(false);
        box8.setChecked(false);


        String s = edie_text.getText().toString();

        if (s.equals("")){
            Toast.makeText(this, "非法数值：空", Toast.LENGTH_SHORT).show();
            return;
        }
        int number = Integer.parseInt(s);
        if (number<=255){
            startLocation(number);
            unChocked();
        }else {
            Toast.makeText(this, "输入数值超过界限", Toast.LENGTH_SHORT).show();
            edie_text.setText("");
        }

    }

    public void down(View view) {

        int num = 0;

        if (!box1.isChecked()){
            num+=1;
        }

        if (!box2.isChecked()){
            num+=2;
        }

        if (!box3.isChecked()){
            num+=4;
        }

        if (!box4.isChecked()){
            num+=8;
        }

        if (!box5.isChecked()){
            num+=16;
        }

        if (!box6.isChecked()){
            num+=32;
        }

        if (!box7.isChecked()){
            num+=64;
        }

        if (!box8.isChecked()){
            num+=128;
        }

        tv.setText("机号为："+num+"号");

    }

    private void startLocation(int num){
        if ((num-128)>=0) {
            box8.setChecked(true);

            if ((num-128)==0)
                return;

            num64(num-128);

        }else {
            num64(num);
        }

    }

    private void num64(int num){
        if ((num-64)>=0){
            box7.setChecked(true);

            if ((num-64)==0)
                return;

            num32(num-64);
        }else {
            num32(num);
        }
    }

    private void num32(int num){
        if ((num-32)>=0){
            box6.setChecked(true);

            if ((num-32)==0)
                return;

            num16(num-32);
        }else {
            num16(num);
        }

    }

    private void num16(int num){
        if ((num-16)>=0){
            box5.setChecked(true);

            if ((num-16)==0)
                return;

            num8(num-16);
        }else {
            num8(num);
        }

    }

    private void num8(int num){
        if ((num-8)>=0){
            box4.setChecked(true);

            if ((num-8)==0)
                return;

            num4(num-8);
        }else {
            num4(num);
        }

    }

    private void num4(int num){
        if ((num-4)>=0){
            box3.setChecked(true);

            if ((num-4)==0)
                return;

            num2(num-4);
        }else {
            num2(num);
        }
    }

    private void num2(int num){
        if ((num-2)>=0){
            box2.setChecked(true);

            if ((num-2)==0)
                return;

            num1(num-2);
        }else {
            num1(num);
        }
    }

    private void num1(int num){
        Log.i("num1:",num+"");
        if ((num-1)>=0){
            box1.setChecked(true);
        }
    }

    private void unChocked(){

        if (box1.isChecked()){
            box1.setChecked(false);
        }else {
            box1.setChecked(true);
        }

        if (box2.isChecked()){
            box2.setChecked(false);
        }else {
            box2.setChecked(true);
        }

        if (box3.isChecked()){
            box3.setChecked(false);
        }else {
            box3.setChecked(true);
        }

        if (box4.isChecked()){
            box4.setChecked(false);
        }else {
            box4.setChecked(true);
        }

        if (box5.isChecked()){
            box5.setChecked(false);
        }else {
            box5.setChecked(true);
        }

        if (box6.isChecked()){
            box6.setChecked(false);
        }else {
            box6.setChecked(true);
        }

        if (box7.isChecked()){
            box7.setChecked(false);
        }else {
            box7.setChecked(true);
        }

        if (box8.isChecked()){
            box8.setChecked(false);
        }else {
            box8.setChecked(true);
        }
    }

    public void save(View view) {

        String number = edie_text.getText().toString();
        String unitString =  unit.getText().toString();
        String name = sp1.getSelectedItem().toString();

        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("unit",unitString);
        values.put("number",number);

        db.insert("unit",null,values);

        values.clear();


        unitAdapter.clear();
        unitAdapter.addAll(initInfo());
        unitAdapter.notifyDataSetChanged();
    }

    private List<String> SearchCommunityInfo() {

        Cursor cursor = db.query("community",null,null,null,null,null,null);

        List<String> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(name);
        }
        return list;
    }


    //未添加限定条件
    private List<Object> initAdapterInfo(){

        List<Object> list = new ArrayList<>();

        Cursor cursor = db.query("community",null,null,null,null,null,null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Title title = new Title(name);
            list.add(title);
        }
        return list;
    }

    private List<UnitNumber> getUnitInfo(){
        List<UnitNumber> list = new ArrayList<>();

        Cursor cursor1 = db.query("unit",null,null,null,null,null,null);

        if (cursor1.moveToFirst()){
            do {
                String unit = cursor1.getString(cursor1.getColumnIndex("unit"));
                String num = cursor1.getString(cursor1.getColumnIndex("number"));

                UnitNumber unitNumber = new UnitNumber(unit,num);

                list.add(unitNumber);

            }while (cursor1.moveToNext());
        }

     return list;

    }

    private List<Object> initInfo(){
        List<UnitNumber> list1= getUnitInfo();
        List<Object> adapterList = initAdapterInfo();
        int x = 0;
        UnitList unitList;

        double number = list1.size()/5.0;
        double ceil = Math.ceil(number);

        for (int i = 0; i < ceil;i++){
            List<UnitNumber> list = new ArrayList<>();
            for (int y = 0; y < 5;y++) {
                UnitNumber o;
                if (x<list1.size()) {
                    o = list1.get(x);
                    x += 1;
                    list.add(o);
                }else {
                    o = new UnitNumber("","");
                    list.add(o);
                }
            }
            unitList = new UnitList(list);

            adapterList.add(unitList);
        }

        return adapterList;
    }
}
