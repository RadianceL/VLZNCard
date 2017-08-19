package com.example.eddie.card.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eddie.card.MyDatabase.MyDatabaseHelper;
import com.example.eddie.card.PoJo.card;
import com.example.eddie.card.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class first extends AppCompatActivity {

    private List<String> list;
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase writableDatabase;
    private EditText edit_query1,edit_query2,edit_array1,edit_array2;
    private AutoCompleteTextView autoTextView;
    private TextView name,array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        final LinearLayout query = (LinearLayout) findViewById(R.id.query);
        final LinearLayout already_exists = (LinearLayout) findViewById(R.id.already_exists);
        final LinearLayout new_array = (LinearLayout) findViewById(R.id.new_array);

        myDatabaseHelper = new MyDatabaseHelper(this,"CardInfo.db",null,1);
        writableDatabase = myDatabaseHelper.getWritableDatabase();

        edit_query1 = (EditText) findViewById(R.id.edit_query1);
        edit_query2 = (EditText) findViewById(R.id.edit_query2);
        edit_query2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        edit_query2.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] c = {'a','b','c','d','d','f'
                        ,'1','2','3','4','5','6','7','8','9','0'
                        ,'A','B','C','D','E','F'
                };
                return c;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }
        });
        edit_array1 = (EditText) findViewById(R.id.edit_array1);
        edit_array2 = (EditText) findViewById(R.id.edit_array2);

        name = (TextView) findViewById(R.id.name_item);
        array = (TextView) findViewById(R.id.array_item);

        autoTextView = (AutoCompleteTextView) findViewById(R.id.name);
        List<String> strings = SearchSQLiteDatabases();
        ArrayAdapter nameAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,strings);
        autoTextView.setAdapter(nameAdapter);

        CardView cardView = (CardView) findViewById(R.id.card_item);
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(first.this);
                builder.setTitle("警告！")

                        .setMessage("是否确认删除-"+name.getText().toString()+"?")

                        .setPositiveButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                writableDatabase.delete("info","array = ?",new String[]{array.getText().toString()});

                                name.setText("");

                                array.setText("");

                            }
                        })

                        .setNegativeButton("否", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setNeutralButton("更改数据", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText edit = new EditText(first.this);
                                edit.setHint("16进制数组");
                                edit.setKeyListener(new NumberKeyListener() {
                                    @Override
                                    protected char[] getAcceptedChars() {
                                        char[] c = {'a','b','c','d','d','f'
                                                ,'1','2','3','4','5','6','7','8','9','0'
                                                ,'A','B','C','D','E','F'
                                        };
                                        return c;
                                    }

                                    @Override
                                    public int getInputType() {
                                        return InputType.TYPE_CLASS_TEXT;
                                    }
                                });
                                edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});

                                //新数据
                                AlertDialog.Builder builder = new AlertDialog.Builder(first.this)
                                        .setTitle("更改:"+name.getText().toString())
                                        .setMessage("更改16进制数组")
                                        .setView(edit)
                                        .setPositiveButton("是", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String info = edit.getText().toString();
                                                if (info.length()==12) {
                                                    ContentValues values = new ContentValues();
                                                    Toast.makeText(first.this, info, Toast.LENGTH_SHORT).show();
                                                    values.put("array", info);
                                                    writableDatabase.update("info", values, "name = ?", new String[]{name.getText().toString()});
                                                    values.clear();

                                                    array.setText(info);

                                                }else {
                                                    Toast.makeText(first.this, "数组长度不符合要求！", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        })
                                        .setNegativeButton("否", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                builder.show();

                            }
                        });
                builder.show();
                return false;
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.set);
        list = new ArrayList<>();
        list.add("查询");list.add("添加已存在");list.add("计算新数组");
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).equals("查询")){
                    query.setVisibility(View.VISIBLE);
                    already_exists.setVisibility(View.GONE);
                    new_array.setVisibility(View.GONE);
                }

                if (list.get(i).equals("添加已存在")){
                    query.setVisibility(View.GONE);
                    already_exists.setVisibility(View.VISIBLE);
                    new_array.setVisibility(View.GONE);
                }

                if (list.get(i).equals("计算新数组")){
                    query.setVisibility(View.GONE);
                    already_exists.setVisibility(View.GONE);
                    new_array.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    private static String randomHexString(int len)  {
        try {

            StringBuffer result = new StringBuffer();

            for(int i=0;i<len;i++) {

                result.append(Integer.toHexString(new Random().nextInt(16)));

            }

            return result.toString().toUpperCase();

        } catch (Exception e) {

            e.printStackTrace();

        }
        return null;

    }

    public void add(View view) {

        String s1 = edit_query1.getText().toString();
        String s2 = edit_query2.getText().toString();

        List<String> list = SearchArray();

        if (!s1.equals("")&&!s2.equals("")){
            if (!list.contains(s2)){
                if (s2.length()==12){
                    AddSQL(s1, s2);
                    Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
                    edit_query1.setText("");
                    edit_query2.setText("");
                }else {
                    Toast.makeText(this, "数组不符合要求长度12位！", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "已重复！", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "名称或数组不能为空！", Toast.LENGTH_SHORT).show();
        }



    }

    private void AddSQL(String s1, String s2) {

        ContentValues values = new ContentValues();

        try {

            values.put("name", s1);
            values.put("array", s2);
            writableDatabase.insert("info", null, values);
            values.clear();

        }catch (Exception e){

            e.printStackTrace();

        }finally {

            values.clear();

        }
    }

    private List<String> SearchSQLiteDatabases(){
        List<String> list = new ArrayList<>();

        Cursor cursor = writableDatabase.query("info", null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {

                String name = cursor.getString(cursor.getColumnIndex("name"));

                list.add(name);

            }while (cursor.moveToNext());
        }


        return list;
    }

    public void save(View view) {
        String s1 = edit_array1.getText().toString();
        String s2 = edit_array2.getText().toString();

        List<String> list = SearchArray();

        if (!s1.equals("")&&!list.contains(s2)){
            AddSQL(s1,s2);
            Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
            edit_array1.setText("");
            edit_array2.setText("");
        }else {
            Toast.makeText(this, "名称不能为空！", Toast.LENGTH_SHORT).show();
        }


    }

    public void new_array_button(View view) {
        String s = randomHexString(12);
        edit_array2.setText(s);
    }

    private List<String> SearchArray(){
        List<String> list = new ArrayList<>();

        Cursor cursor = writableDatabase.query("info", null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {

                String array = cursor.getString(cursor.getColumnIndex("array"));

                list.add(array);

            }while (cursor.moveToNext());
        }


        return list;
    }

    public void query(View view) {

        card card = SearchCardInfo();

        if (card!=null){

            name.setText(card.getName());
            array.setText(card.getArray());

        }else {
            Toast.makeText(this, "没有指定数据！", Toast.LENGTH_SHORT).show();
        }

    }

    private card SearchCardInfo() {

        String s = autoTextView.getText().toString();

        Cursor cursor = writableDatabase.query("info",new String[]{"name,array"},"name like ?",new String[]{s},null,null,null);

        if (cursor.moveToFirst()) {
            String array = cursor.getString(cursor.getColumnIndex("array"));
            String name = cursor.getString(cursor.getColumnIndex("name"));

            card card = new card(name, array);

            return card;
        }

        return null;

    }
}
