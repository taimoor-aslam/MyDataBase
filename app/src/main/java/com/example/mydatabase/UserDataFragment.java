package com.example.mydatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class UserDataFragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    SqlDataBase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_user_data_fragment,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        db=new SqlDataBase(getContext());
        try {
            ArrayList<MyListData> mylist = db.show();
            //ArrayList<MyListData> mylist = new ArrayList<>();
            MyListAdapter myListAdapter = new MyListAdapter(mylist);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(myListAdapter);
        }catch (Exception e)
        {
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        return view;

    }
}
