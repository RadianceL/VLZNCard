package com.example.eddie.card.MyAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eddie.card.MyDatabase.MyDatabaseHelper;
import com.example.eddie.card.PoJo.Title;
import com.example.eddie.card.PoJo.UnitList;
import com.example.eddie.card.PoJo.UnitNumber;
import com.example.eddie.card.R;

import java.util.Comparator;
import java.util.List;

public class UnitAdapter extends ArrayAdapter{

    private static final int TYPE_ITEM_DEFEAT = 0;

    private Context context;
    private int resource;
    private List<Object> list;
    private Handler handler;
    private Class[] MyClass;

    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase db;

    public UnitAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Object> objects, Handler handler) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.list = objects;
        this.handler = handler;

        MyClass = new Class[]{Title.class, UnitList.class};

        myDatabaseHelper = new MyDatabaseHelper(context,"CardInfo.db",null,1);
        db = myDatabaseHelper.getWritableDatabase();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public int getViewTypeCount() {
        return MyClass.length;
    }

    @Override
    public int getItemViewType(int position) {
        Object object=getItem(position);
        for(int i=0,size=MyClass.length;i<size;i++){
            if(object.getClass()== MyClass[i]){
                return i;
            }
        }
        return TYPE_ITEM_DEFEAT;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        int viewType=getItemViewType(position);
        if (viewType==0) {
            view = getStyle1View(position, view, parent);
        }else if (viewType==1) {
            view = getStyle2View(position, view, parent);
        }

        return view;
    }

    private View getStyle1View(int position, View view, ViewGroup parent) {
        final Styly1ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(resource,parent,false);
            holder = new Styly1ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (Styly1ViewHolder) view.getTag();
        }

        Title title = (Title) list.get(position);

        holder.tvIndex.setText(title.getTitle());

        return view;
    }

    private View getStyle2View(final int position, View view, ViewGroup parent) {
        final Styly2ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.number_info,parent,false);
            holder = new Styly2ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (Styly2ViewHolder) view.getTag();
        }

        UnitList unitInfo = (UnitList) list.get(position);
        List<UnitNumber> info = unitInfo.getUnitNumber();

        final UnitNumber nu =  info.get(0);

        if (nu!=null) {
            if (!nu.getNumber().equals("")) {
                final int number = Integer.parseInt(nu.getNumber());
                holder.cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putInt("number", number);
                        msg.setData(b);
                        handler.handleMessage(msg);
                    }
                });

                holder.cardView1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        showDialog(0,nu.getName(), String.valueOf(nu.getId()),position);

                        return false;
                    }
                });

            }
            holder.card1UnitItem.setText(nu.getName());
            holder.card1NumberItem.setText(nu.getNumber());
        }

        if (info.get(1) == null){
            holder.cardView2.setVisibility(View.INVISIBLE);
        }else {
            final UnitNumber nu1 =  info.get(1);
            holder.card2UnitItem.setText(nu1.getName());
            holder.card2NumberItem.setText(nu1.getNumber());

            if (!nu1.getNumber().equals("")) {
                final int number = Integer.parseInt(nu1.getNumber());
                holder.cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putInt("number", number);
                        msg.setData(b);
                        handler.handleMessage(msg);
                    }
                });

                holder.cardView2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        showDialog(1,nu1.getName(), String.valueOf(nu1.getId()),position);

                        return false;
                    }
                });

            }
        }

        if (info.get(2) == null){
            holder.cardView3.setVisibility(View.INVISIBLE);
        }else {
            final UnitNumber nu2 =  info.get(2);
            holder.card3UnitItem.setText(nu2.getName());
            holder.card3NumberItem.setText(nu2.getNumber());

            if (!nu2.getNumber().equals("")) {
                final int number = Integer.parseInt(nu2.getNumber());
                holder.cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putInt("number", number);
                        msg.setData(b);
                        handler.handleMessage(msg);
                    }
                });

                holder.cardView3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        showDialog(2,nu2.getName(), String.valueOf(nu2.getId()),position);

                        return false;
                    }
                });
            }
        }

        if (info.get(3) == null){
            holder.cardView4.setVisibility(View.INVISIBLE);
        }else {
            final UnitNumber nu3 = info.get(3);
            holder.card4UnitItem.setText(nu3.getName());
            holder.card4NumberItem.setText(nu3.getNumber());

            if (!nu3.getNumber().equals("")) {
                final int number = Integer.parseInt(nu3.getNumber());
                holder.cardView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putInt("number", number);
                        msg.setData(b);
                        handler.handleMessage(msg);
                    }
                });

                holder.cardView4.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        showDialog(3,nu3.getName(), String.valueOf(nu3.getId()),position);

                        return false;
                    }
                });
            }
        }

        if (info.get(4) == null){
            holder.cardView5.setVisibility(View.INVISIBLE);
        }else {
            final UnitNumber nu4 = info.get(4);
            holder.card5UnitItem.setText(nu4.getName());
            holder.card5NumberItem.setText(nu4.getNumber());

            if (!nu4.getNumber().equals("")) {
                final int number = Integer.parseInt(nu4.getNumber());
                holder.cardView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Message msg = new Message();
                        Bundle b = new Bundle();
                        b.putInt("number", number);
                        msg.setData(b);
                        handler.handleMessage(msg);
                    }
                });

                holder.cardView5.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        showDialog(4,nu4.getName(), String.valueOf(nu4.getId()),position);

                        return false;
                    }
                });
            }
        }

        return view;
    }


    class Styly1ViewHolder {
        TextView tvIndex;

        public Styly1ViewHolder(View root){
            tvIndex=(TextView)root.findViewById(R.id.title_item);
        }

    }

    class Styly2ViewHolder {

        CardView cardView1,cardView2,cardView3,cardView4,cardView5;

        TextView card1NumberItem,card1UnitItem,card2NumberItem,card2UnitItem,card5UnitItem;
        TextView card3NumberItem,card3UnitItem,card4NumberItem,card4UnitItem,card5NumberItem;


        public Styly2ViewHolder(View root){
            cardView1 = root.findViewById(R.id.i_menu_one);
            card1UnitItem=(TextView)cardView1.findViewById(R.id.unit_item);
            card1NumberItem=(TextView)cardView1.findViewById(R.id.number_item);

            cardView2 = root.findViewById(R.id.i_menu_two);
            card2UnitItem=(TextView)cardView2.findViewById(R.id.unit_item);
            card2NumberItem=(TextView)cardView2.findViewById(R.id.number_item);

            cardView3 = root.findViewById(R.id.i_menu_three);
            card3UnitItem=(TextView)cardView3.findViewById(R.id.unit_item);
            card3NumberItem=(TextView)cardView3.findViewById(R.id.number_item);

            cardView4 = root.findViewById(R.id.i_menu_four);
            card4UnitItem=(TextView)cardView4.findViewById(R.id.unit_item);
            card4NumberItem=(TextView)cardView4.findViewById(R.id.number_item);

            cardView5 = root.findViewById(R.id.i_menu_five);
            card5UnitItem=(TextView)cardView5.findViewById(R.id.unit_item);
            card5NumberItem=(TextView)cardView5.findViewById(R.id.number_item);
        }
    }


    private void showDialog(final int location, String deleteString, final String id, final int postion){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("警告！");
        builder.setMessage("是否删除:"+deleteString)

            .setNegativeButton("否", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })

            .setPositiveButton("是", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    db.delete("unit","id = ?",new String[]{id});
//                    FIXME: 2017/8/21
//                    更改为使用handler方法更新adapter
//                    UnitList d = (UnitList) list.get(postion);
//                    List<UnitNumber> unitNumber = d.getUnitNumber();
//                    d.getUnitNumber().remove(location);
//                    unitNumber.add(location,new UnitNumber(-1,"",""));
//                    UnitAdapter.super.notifyDataSetChanged();
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putInt("number", 256);
                    msg.setData(b);
                    handler.handleMessage(msg);

                }
            });

        builder.show();
    }

    private void sort(){

    }
}
