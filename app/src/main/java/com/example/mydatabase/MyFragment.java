package com.example.mydatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.ECField;

public class MyFragment extends Fragment {
EditText email,passcode;
TextView signup,txt;
Button login_btn;
View view;
SqlDataBase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_my_fragment,container,false);
        email=(EditText) view.findViewById(R.id.emaailedt);
        passcode=(EditText) view.findViewById(R.id.passcodeedt);
        signup=(TextView) view.findViewById(R.id.singuptxt);
        login_btn=(Button) view.findViewById(R.id.loginbtn);
        txt=(TextView) view.findViewById(R.id.showttxt1);
        db=new SqlDataBase(getContext());
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SignUpFragment());
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String Email=email.getText().toString();
                    String pass=passcode.getText().toString();
                   if(db.verify(Email,pass))
                    {
                        Toast.makeText(getActivity(),"Login Successfull",Toast.LENGTH_SHORT).show();
                        loadFragment(new UserDataFragment());
                        //  txt.setText(db.show());
                    }
                    else
                    {
                        email.setError("wrong email");
                        passcode.setError("wrong password");
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    public void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }
}

