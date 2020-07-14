package com.example.mydatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

public class ShowUserData extends Fragment {
    View view;
    CircularImageView circularImageView;
    TextView showdatatxt;
    SqlDataBase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_show_user_data,container,false);
        circularImageView=(CircularImageView) view.findViewById(R.id.myimage1);
        showdatatxt=(TextView) view.findViewById(R.id.showdatatxt);
        db=new SqlDataBase(getActivity());
        try {
            loadCircularImage(circularImageView);
            Bundle bundle=getArguments();
            String email=bundle.getString("email");
           Bitmap photo=db.getUserImage(email);
           String result=db.getRecord(email);
           circularImageView.setImageBitmap(photo);
           showdatatxt.setText(result);
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        return view;

    }
    /** CircularImageView code we have used third party library for it**/
    public void loadCircularImage(CircularImageView circularImageView)
    {
        // Set Color
        circularImageView.setCircleColor(Color.WHITE);
// or with gradient
        circularImageView.setCircleColorStart(Color.BLACK);
        circularImageView.setCircleColorEnd(Color.RED);
        circularImageView.setCircleColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);

// Set Border
        circularImageView.setBorderWidth(10f);
        circularImageView.setBorderColor(Color.BLACK);
// or with gradient
        circularImageView.setBorderColorStart(Color.BLACK);
        circularImageView.setBorderColorEnd(Color.RED);
        circularImageView.setBorderColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);

// Add Shadow with default param
        circularImageView.setShadowEnable(true);
// or with custom param
        circularImageView.setShadowRadius(7f);
        circularImageView.setShadowColor(Color.RED);
        circularImageView.setShadowGravity(CircularImageView.ShadowGravity.CENTER);
    }
    /** Circular Image code ends here **/
}
