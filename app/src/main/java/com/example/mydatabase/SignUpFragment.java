package com.example.mydatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class SignUpFragment extends Fragment {

    SqlDataBase db;
    View view;
    EditText nameedt,ageedt,emailedt,passwordedt;
    AutoCompleteTextView hobbiesedt;
    RadioGroup radioGroup;
    Button donebtn;
    TextView showtxt;
    String radiobtnTxt;
    CircularImageView circularImageView;
    String [] myhobbiesList=new String[]
            {"Gardening","Reading","Running","Listen music","Cricket",
             "Footbal","Movies"};

    private Bitmap photo;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_PICTURE = 1999;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_GALLERY_PERMISSION_CODE=200;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    view=inflater.inflate(R.layout.activity_sign_up_fragment,container,false);

    nameedt=(EditText) view.findViewById(R.id.nameedt);
    ageedt=(EditText) view.findViewById(R.id.ageedt);
    emailedt=(EditText) view.findViewById(R.id.Emailedt);
    passwordedt=(EditText) view.findViewById(R.id.passwordedt);
    hobbiesedt=(AutoCompleteTextView) view.findViewById(R.id.hobbiesedt);
    radioGroup=(RadioGroup) view.findViewById(R.id.radiobtngroup);
    donebtn=(Button) view.findViewById(R.id.contibtn);
    showtxt=(TextView) view.findViewById(R.id.showtxt);
    circularImageView = (CircularImageView) view.findViewById(R.id.myimage);
        ArrayAdapter<String> myadap=new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item,myhobbiesList);
        hobbiesedt.setAdapter(myadap);
        hobbiesedt.setThreshold(1);
        hobbiesedt.setTextColor(Color.RED);


    /** design circular image **/
    loadCircularImage(circularImageView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton) group.findViewById(checkedId);
                radiobtnTxt=radioButton.getText().toString();
            }
        });

    db=new SqlDataBase(getContext());

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=true;
                try {
                    String name=nameedt.getText().toString();
                    String age=ageedt.getText().toString();
                    String email=emailedt.getText().toString();
                    String pasword=passwordedt.getText().toString();
                    String hobbies=hobbiesedt.getText().toString();
                   // Bitmap image=((BitmapDrawable)circularImageView.getDrawable()).getBitmap();
                    db.insert(name,age,email,pasword,hobbies,radiobtnTxt,photo);
                } catch (Exception e) {
                    flag=false;
                    String error=e.toString();
                   // nameedt.setText(e.toString());
                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                }
                finally {
                    if(flag)
                    {
                        try {
                          //  String result = db.show();
                           // showtxt.setText(result);
                            Toast.makeText(getActivity(), "Data inserted Successfully!", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();


            }
        });
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

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to upload your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                    getActivity(),
                                    PERMISSIONS_STORAGE,
                                    MY_GALLERY_PERMISSION_CODE
                            );
                        } else {

                            Intent intent = new Intent();
                            intent.setType("image/*");
                            // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent,"Select Picture"), GALLERY_PICTURE);


                        }

                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_CAMERA_PERMISSION_CODE);
                        } else {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            }
                        }

                    }
                });
        myAlertDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }

        } else if(requestCode==MY_GALLERY_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "gallery read permission granted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), GALLERY_PICTURE);
            } else {
                Toast.makeText(getActivity(), "gallery read permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");

            circularImageView.setImageBitmap(photo);
        } else if (requestCode == GALLERY_PICTURE && resultCode == Activity.RESULT_OK) {


            // The following code snipet is used when Intent for single image selection is set

            Uri imageUri = data.getData();
            Log.d(TAG,"file uri: "+imageUri.toString());
            try {
                photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            circularImageView.setImageBitmap(photo);


        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "PHOTO is null");
           // getActivity().finish();
        }
    }


}


