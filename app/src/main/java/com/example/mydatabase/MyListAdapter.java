package com.example.mydatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    //private MyListData[] listData;
    private ArrayList<MyListData> data;
    public MyListAdapter(ArrayList<MyListData> listData)
    {
        this.data=listData;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.list_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyListAdapter.ViewHolder viewHolder, int i) {
        final MyListData myListData=data.get(i);
        viewHolder.textView.setText(data.get(i).getmName());
        viewHolder.imageView.setImageBitmap(data.get(i).getmPhoto());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"click on item: "+myListData.getmName(),
                        Toast.LENGTH_SHORT).show();
//                Intent it=new Intent(v.getContext(),MyFragment.class);
//               // it.putExtra("email",myListData.getmEmail());
//                v.getContext().startActivity(it);
                Bundle bundle=new Bundle();
                bundle.putString("email",myListData.getmEmail());
                ShowUserData showUserData=new ShowUserData();
                showUserData.setArguments(bundle);
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().
                        beginTransaction().replace(R.id.framelayout,showUserData).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return  data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.relativeLayout=(RelativeLayout) itemView.findViewById(R.id.relativelayout);
            this.textView=(TextView) itemView.findViewById(R.id.txtview);
            this.imageView=(ImageView) itemView.findViewById(R.id.imageview);
        }
    }
}
