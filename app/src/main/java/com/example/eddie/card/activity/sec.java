package com.example.eddie.card.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eddie.card.MyAdapter.UnitAdapter;
import com.example.eddie.card.PoJo.UnitList;
import com.example.eddie.card.MyDatabase.MyDatabaseHelper;
import com.example.eddie.card.PoJo.Title;
import com.example.eddie.card.PoJo.UnitNumber;
import com.example.eddie.card.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.eddie.card.R.id.input_name;
import static com.example.eddie.card.R.id.line1;
import static com.example.eddie.card.R.id.show;

public class sec extends AppCompatActivity {
    
    private EditText edie_text,unit,input_name;
    private TextView tv;
    private CheckBox box1,box2,box3,box4,box5,box6,box7,box8;

    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase db;

    private Spinner sp1,sp2;

    private ListView showUnit,listSavedCommunity;
    private UnitAdapter unitAdapter;
    private ArrayAdapter adapter;

    private LinearLayout up,down;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            int number = msg.getData().getInt("number");
            if (number <= 255) {
                setBox(number);
                tv.setText("机号为：" + number + "号");
            }else if (number == 256){
                unitAdapter.clear();
                unitAdapter.addAll(initInfo());
                unitAdapter.notifyDataSetChanged();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        myDatabaseHelper = new MyDatabaseHelper(this,"CardInfo.db",null,1);
        db = myDatabaseHelper.getWritableDatabase();

        initDatabses("成龙花园");

        init();

    }

    private void initDatabses(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun)
        {
            ContentValues values = new ContentValues();
            values.put("name",name);
            db.insert("community",null,values);
            Log.d("debug", "第一次运行");
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        } else
        {
            Log.d("debug", "不是第一次运行");
        }
    }

    @SuppressWarnings("unchecked")
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
        sp2 = (Spinner) findViewById(R.id.change);

        up = (LinearLayout) findViewById(R.id.up);
        down = (LinearLayout) findViewById(R.id.down);

        final List<String> changeList = new ArrayList<>();
        changeList.add("功能");changeList.add("小区管理");
        ArrayAdapter changeAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,changeList);
        sp2.setAdapter(changeAdapter);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (changeList.get(i).equals("功能")){
                    up.setVisibility(View.VISIBLE);
                    down.setVisibility(View.GONE);
                }

                if (changeList.get(i).equals("小区管理")){
                    up.setVisibility(View.GONE);
                    down.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listSavedCommunity = (ListView) findViewById(R.id.show_save_list);

        input_name = (EditText) findViewById(R.id.input_name);

        showUnit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.i("ccc",view.getId()+"");

                switch (view.getId()){
                    case R.id.i_menu_one:

                        Toast.makeText(sec.this, "fffff", Toast.LENGTH_SHORT).show();

                }
            }
        });

        final List<String> list = SearchCommunityInfo();
        adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,list);
        sp1.setAdapter(adapter);


        listSavedCommunity.setAdapter(adapter);

        listSavedCommunity.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                myShowDialog(list.get(i));
                return false;
            }
        });

        List<Object> adapterList = initInfo();
        unitAdapter = new UnitAdapter(this,R.layout.title_item,adapterList,handler);
        showUnit.setAdapter(unitAdapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitAdapter.clear();
                unitAdapter.addAll(initInfo());
                unitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TextView tv = (TextView) findViewById(R.id.content);
        TextView tv1 = (TextView) findViewById(R.id.content1);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShowDialog();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShowDialog();
            }
        });
    }

    private void myShowDialog(final String location) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告！");
        builder.setMessage("是否删除:"+location)

                .setNegativeButton("否", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setPositiveButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.delete("community","name like ?",new String[]{location});

                        adapter.clear();
                        adapter.addAll(SearchCommunityInfo());
                        adapter.notifyDataSetChanged();
                    }
                });

        builder.show();

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
            setBox(number);
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

    @SuppressWarnings("unchecked")
    public void save(View view) {
        String number = edie_text.getText().toString();
        String unitString =  unit.getText().toString();
        String name = sp1.getSelectedItem().toString();

        if (number.equals("")) {
            Toast.makeText(this, "机号不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (unitString.equals("")){
            Toast.makeText(this, "单元号不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(Integer.parseInt(number)>255){
            Toast.makeText(this, "数值不能超过255！", Toast.LENGTH_SHORT).show();
            return;
        }

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("unit", unitString);
            values.put("number", number);
            db.insert("unit", null, values);
            values.clear();

            unitAdapter.clear();
            unitAdapter.addAll(initInfo());
            unitAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    private List<String> SearchCommunityInfo() {

        Cursor cursor = db.query("community",null,null,null,null,null,null);

        List<String> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                list.add(name);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }


    private List<UnitNumber> getUnitInfo(){
        List<UnitNumber> list = new ArrayList<>();
        Cursor cursor1 = db.query("unit",null,"name like ?",new String[]{sp1.getSelectedItem().toString()},null,null,null);

        if (cursor1.moveToFirst()){
            do {
                int id = cursor1.getInt(cursor1.getColumnIndex("id"));
                String unit = cursor1.getString(cursor1.getColumnIndex("unit"));
                String num = cursor1.getString(cursor1.getColumnIndex("number"));

                UnitNumber unitNumber = new UnitNumber(id,unit,num);

                list.add(unitNumber);
            }while (cursor1.moveToNext());
        }

        cursor1.close();
     return list;

    }

    @SuppressWarnings("unchecked")
    private List<Object> initInfo(){
        List<UnitNumber> list1= getUnitInfo();

        Collections.sort(list1, new Comparator() {
                public int compare(Object a, Object b) {
                    if(isDigit(((UnitNumber)a).getName().split("#")[0])&&isDigit(((UnitNumber)b).getName().split("#")[0])){
                        int one = Integer.parseInt(((UnitNumber)a).getName().split("#")[0]);
                        int two = Integer.parseInt(((UnitNumber)b).getName().split("#")[0]);
                        return one- two ;
                    }else {
                        return -1;
                    }
                }
        });

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
                    o = new UnitNumber(-1,"","");
                    list.add(o);
                }
            }
            unitList = new UnitList(list);

            adapterList.add(unitList);
        }

        return adapterList;
    }

    private List<Object> initAdapterInfo() {
        List<Object> list = new ArrayList<>();

        list.add(new Title(sp1.getSelectedItem().toString()));

        return list;
    }

    private void setBox(int number){
        box1.setChecked(false);
        box2.setChecked(false);
        box3.setChecked(false);
        box4.setChecked(false);
        box5.setChecked(false);
        box6.setChecked(false);
        box7.setChecked(false);
        box8.setChecked(false);
        startLocation(number);
        unChocked();
    }

    public void add(View view) {

        ContentValues values = new ContentValues();
        values.put("name",input_name.getText().toString());
        db.insert("community",null,values);

        adapter.clear();
        adapter.addAll(SearchCommunityInfo());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }
}
