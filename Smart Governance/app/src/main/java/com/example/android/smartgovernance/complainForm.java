package com.example.android.smartgovernance;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;



public class complainForm extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAnalytics firebaseAnalytics;
    UserInfo info;
    String uid;
    Spinner spinner;
    View view;
    String category1;
    TextView textView, textView1;
    private FirebaseAuth mAuth;
    EditText name;
    EditText area;
    EditText pno;
    EditText email;
    EditText complain;
    Button button;
    DatabaseReference databaseReference1,databaseReference3,databaseReference4, databaseReferencewater, databaseReferenceanimal, databaseReferencestreet, databaseReferencestroam,databaseReference2;
    Date date;
    SimpleDateFormat simpleDateFormat;
    String DateTostr;
    ProgressDialog mp;
    String Storage_Path = "All_Image_Uploads/";
    Button location;
    TextView textview;
    Button UploadButton;
    ImageView SelectImage;
    Uri FilePathUri;
   StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    int flag=0;
    int cid;

    private GoogleApiClient mGoogleApiClient;
    private final int PLACE_PICKER_REQUEST = 1;
    private String TAG = "place";


    public complainForm() {
        // Required empty public constructor
    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
        final String id = sharedPreferenceUtils.getString(getActivity(), "key");

        if(id == null){
            // new CustomToast().Show_Toast(getActivity(), view,
            //      "You are not logged in");
            Toast.makeText(getActivity(),"You are not logged in", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), NavigationActivity.class);
            startActivity(i);

        }

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_complain_form, container, false);
        spinner = view.findViewById(R.id.spinner);
        textView = view.findViewById(R.id.text_view);



        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(getActivity());
        Firebase.setAndroidContext(getActivity());
        mp = new ProgressDialog(getActivity());
        name =  view.findViewById(R.id.name);
        area = (EditText)view.findViewById(R.id.area);
        pno = (EditText) view.findViewById(R.id.pno);
        email = (EditText) view.findViewById(R.id.Email);
        complain = (EditText) view.findViewById(R.id.complain);
        button =(Button) view.findViewById(R.id.sub);
        //  cb = view.findViewById(R.id.cb);
        UploadButton = (Button) view.findViewById(R.id.btnUpload);
        SelectImage = (ImageView) view.findViewById(R.id.ShowImageView);
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm a");
        DateTostr = DateFormat.getDateTimeInstance().format(date);

        databaseReference = FirebaseDatabase.getInstance().getReference("complains");
        databaseReferencewater = FirebaseDatabase.getInstance().getReference("water");
        databaseReferenceanimal = FirebaseDatabase.getInstance().getReference("animal");
        databaseReferencestreet = FirebaseDatabase.getInstance().getReference("street");
        databaseReferencestroam = FirebaseDatabase.getInstance().getReference("drainage");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("complains").child("name");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user2 = mAuth.getCurrentUser();
        String id2 = user2.getUid().toString();
        firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

        //Set the minimum engagement time required before starting a session.
        firebaseAnalytics.setMinimumSessionDuration(2000);

        //Set the duration of inactivity that terminates the current session. The default value is 1800000 (30 minutes).
        firebaseAnalytics.setSessionTimeoutDuration(300000);


        databaseReference3= FirebaseDatabase.getInstance().getReference("signup").child(id2);
        databaseReference3.addValueEventListener(new com.google.firebase.database.ValueEventListener() {

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String em = dataSnapshot.child("Email").getValue(String.class);
                String n = dataSnapshot.child("Name").getValue(String.class);
                String p = dataSnapshot.child("Pno").getValue(String.class);
                name.setText(n);
                pno.setText(p);
                email.setText(em);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.category,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                category1 = (String) adapterView.getItemAtPosition(i);
                textView.setText("Category" + ": " + category1);
                cid =view.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        location = (Button) view.findViewById(R.id.location);
        //  textview = (TextView) findViewById(R.id.textview);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleApiClient = new GoogleApiClient
                        .Builder(getActivity())
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage(getActivity(), complainForm.this)
                        .build();


                //_________methode will be call when user click on "Pick Address By Place Picker"

                //___________create object of placepicker builder
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {

                    //__________start placepicker activity for result
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });







        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                FirebaseUser user = mAuth.getCurrentUser();
                String currentUserUid = user.getUid().toString();
                Log.d(currentUserUid, " ");

                try {

                    if(currentUserUid.equals(null)) {

                        new CustomToast().Show_Toast(getActivity(), view,
                                "Login first.");
                    }
                    else {

                        final String getEmailId = email.getText().toString();

                        final String getArea = area.getText().toString();

                        final String getcategory = textView.getText().toString();

                        final String getComplain = complain.getText().toString();
                        // Check patter for email id
                        Pattern p = Pattern.compile(Utils.regEx);

                        Matcher m = p.matcher(getEmailId);

                        // Check for both field is empty or not
                        if (getEmailId.equals("") || getEmailId.length() == 0
                                || getArea.equals("") || getArea.length() == 0||getComplain.equals("") || getComplain.length() == 0
                                || getcategory.equals("") || getcategory.length() == 0) {

                            new CustomToast().Show_Toast(getActivity(), view,
                                    "Enter all values.");

                        }
                        else {

                            // Checking whether FilePathUri Is empty or not.
                            if (FilePathUri != null) {

                                // Setting progressDialog Title.
                                progressDialog.setTitle("Image is Uploading...");

                                // Showing progressDialog.
                                progressDialog.show();

                                // Creating second StorageReference.
                                StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

                                // Adding addOnSuccessListener to second StorageReference.
                                storageReference2nd.putFile(FilePathUri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                ImageUploadInfo imageUploadInfo = new ImageUploadInfo(taskSnapshot.getDownloadUrl().toString());
                                                String namestr = name.getText().toString();
                                                final String areastr = area.getText().toString();
                                                String pnostr = pno.getText().toString();
                                                String emailstr = email.getText().toString();
                                                String compalinstr = complain.getText().toString();
                                                FirebaseUser user1 = mAuth.getCurrentUser();
                                                final String id1 = user1.getUid().toString();
                                                String id = databaseReference.push().getKey();

                                                SharedPreferenceUtils sharedPreferenceUtils1 = SharedPreferenceUtils.getInstance();
                                                sharedPreferenceUtils1.setString(getActivity(), "key1", id);


                                                if (category1.equals("WaterSupply")) {


                                                    databaseReference.child(id1).child(id).setValue(imageUploadInfo);
                                                    databaseReference.child(id1).child(id).child("email").setValue(emailstr);
                                                    databaseReference.child(id1).child(id).child("complain").setValue(compalinstr);
                                                    databaseReference.child(id1).child(id).child("category").setValue(category1);
                                                    databaseReference.child(id1).child(id).child("date").setValue(DateTostr);
                                                    databaseReference.child(id1).child(id).child("name").setValue(namestr);
                                                    databaseReference.child(id1).child(id).child("area").setValue(areastr);
                                                    databaseReference.child(id1).child(id).child("pno").setValue(pnostr);
                                                    databaseReference.child(id1).child(id).child("complainid").setValue(id);
                                                    databaseReference.child(id1).child(id).child("status").setValue("Submitted");


                                                    databaseReferencewater.child(id).setValue(imageUploadInfo);
                                                    databaseReferencewater.child(id).child("uid").setValue(id1);
                                                    databaseReferencewater.child(id).child("pno").setValue(pnostr);
                                                    databaseReferencewater.child(id).child("email").setValue(emailstr);
                                                    databaseReferencewater.child(id).child("complain").setValue(compalinstr);
                                                    databaseReferencewater.child(id).child("date").setValue(DateTostr);
                                                    databaseReferencewater.child(id).child("name").setValue(namestr);
                                                    databaseReferencewater.child(id).child("area").setValue(areastr);
                                                    databaseReferencewater.child(id).child("complainid").setValue(id);
                                                    databaseReferencewater.child(id).child("status").setValue("Submitted");

                                                } else if (category1.equals("StreetLight")) {

                                                    databaseReference.child(id1).child(id).setValue(imageUploadInfo);
                                                    databaseReference.child(id1).child(id).child("email").setValue(emailstr);
                                                    databaseReference.child(id1).child(id).child("complain").setValue(compalinstr);
                                                    databaseReference.child(id1).child(id).child("category").setValue(category1);
                                                    databaseReference.child(id1).child(id).child("date").setValue(DateTostr);
                                                    databaseReference.child(id1).child(id).child("name").setValue(namestr);
                                                    databaseReference.child(id1).child(id).child("area").setValue(areastr);
                                                    databaseReference.child(id1).child(id).child("pno").setValue(pnostr);
                                                    databaseReference.child(id1).child(id).child("complainid").setValue(id);
                                                    databaseReference.child(id1).child(id).child("status").setValue("Submitted");



                                                    databaseReferencestreet.child(id).setValue(imageUploadInfo);
                                                    databaseReferencestreet.child(id).child("uid").setValue(id1);
                                                    databaseReferencestreet.child(id).child("pno").setValue(pnostr);
                                                    databaseReferencestreet.child(id).child("email").setValue(emailstr);
                                                    databaseReferencestreet.child(id).child("complain").setValue(compalinstr);
                                                    databaseReferencestreet.child(id).child("date").setValue(DateTostr);
                                                    databaseReferencestreet.child(id).child("name").setValue(namestr);
                                                    databaseReferencestreet.child(id).child("area").setValue(areastr);
                                                    databaseReferencestreet.child(id).child("complainid").setValue(id);
                                                    databaseReferencestreet.child(id).child("status").setValue("Submitted");
                                                } else if (category1.equals("StrayAnimal")) {
                                                    databaseReference.child(id1).child(id).setValue(imageUploadInfo);
                                                    databaseReference.child(id1).child(id).child("email").setValue(emailstr);
                                                    databaseReference.child(id1).child(id).child("complain").setValue(compalinstr);
                                                    databaseReference.child(id1).child(id).child("category").setValue(category1);
                                                    databaseReference.child(id1).child(id).child("date").setValue(DateTostr);
                                                    databaseReference.child(id1).child(id).child("name").setValue(namestr);
                                                    databaseReference.child(id1).child(id).child("area").setValue(areastr);
                                                    databaseReference.child(id1).child(id).child("pno").setValue(pnostr);
                                                    databaseReference.child(id1).child(id).child("complainid").setValue(id);
                                                    databaseReference.child(id1).child(id).child("status").setValue("Submitted");


                                                    databaseReferenceanimal.child(id).setValue(imageUploadInfo);
                                                    databaseReferenceanimal.child(id).child("uid").setValue(id1);
                                                    databaseReferenceanimal.child(id).child("pno").setValue(pnostr);
                                                    databaseReferenceanimal.child(id).child("email").setValue(emailstr);
                                                    databaseReferenceanimal.child(id).child("complain").setValue(compalinstr);
                                                    databaseReferenceanimal.child(id).child("date").setValue(DateTostr);
                                                    databaseReferenceanimal.child(id).child("name").setValue(namestr);
                                                    databaseReferenceanimal.child(id).child("area").setValue(areastr);
                                                    databaseReferenceanimal.child(id).child("complainid").setValue(id);
                                                    databaseReferenceanimal.child(id).child("status").setValue("Submitted");

                                                } else if (category1.equals("Drainage")) {
                                                    databaseReference.child(id1).child(id).setValue(imageUploadInfo);
                                                    databaseReference.child(id1).child(id).child("email").setValue(emailstr);
                                                    databaseReference.child(id1).child(id).child("complain").setValue(compalinstr);
                                                    databaseReference.child(id1).child(id).child("category").setValue(category1);
                                                    databaseReference.child(id1).child(id).child("date").setValue(DateTostr);
                                                    databaseReference.child(id1).child(id).child("name").setValue(namestr);
                                                    databaseReference.child(id1).child(id).child("area").setValue(areastr);
                                                    databaseReference.child(id1).child(id).child("pno").setValue(pnostr);
                                                    databaseReference.child(id1).child(id).child("complainid").setValue(id);
                                                    databaseReference.child(id1).child(id).child("status").setValue("Submitted");


                                                    databaseReferencestroam.child(id).setValue(imageUploadInfo);
                                                    databaseReferencestroam.child(id).child("uid").setValue(id1);
                                                    databaseReferencestroam.child(id).child("pno").setValue(pnostr);
                                                    databaseReferencestroam.child(id).child("email").setValue(emailstr);
                                                    databaseReferencestroam.child(id).child("complain").setValue(compalinstr);
                                                    databaseReferencestroam.child(id).child("date").setValue(DateTostr);
                                                    databaseReferencestroam.child(id).child("name").setValue(namestr);
                                                    databaseReferencestroam.child(id).child("area").setValue(areastr);
                                                    databaseReferencestroam.child(id).child("complainid").setValue(id);
                                                    databaseReferencestroam.child(id).child("status").setValue("Submitted");

                                                }


                                                progressDialog.dismiss();
                                                new CustomToast().Show_Toast(getActivity(), view,
                                                        "Complain issued");


                                            //    name.setText("");
                                              //  pno.setText("");
                                                area.setText("");
                                                //email.setText("");
                                                complain.setText("");
                                                textView.setText("");
                                                spinner.setSelection(0);
                                                SelectImage.setImageBitmap(null);
                                                UploadButton.setText("Upload image");

                                            }
                                        })
                                        // If something goes wrong .
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {

                                                // Hiding the progressDialog.
                                                progressDialog.dismiss();

                                                // Showing exception error message.
                                                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        })

                                        // On progress change upload time.
                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                                // Setting progressDialog Title.
                                                progressDialog.setTitle("Image is Uploading...");

                                            }
                                        });
                            } else {

                                // ImageUploadInfo imageUploadInfo = new ImageUploadInfo(taskSnapshot.getDownloadUrl().toString());
                                String namestr = name.getText().toString();
                                String areastr = area.getText().toString();
                                String pnostr = pno.getText().toString();
                                String emailstr = email.getText().toString();
                                String compalinstr = complain.getText().toString();
                                FirebaseUser user2 = mAuth.getCurrentUser();
                                String id1 = user2.getUid().toString();
                                String id = databaseReference.push().getKey();

                                SharedPreferenceUtils sharedPreferenceUtils1 = SharedPreferenceUtils.getInstance();
                                sharedPreferenceUtils1.setString(getActivity(), "key1", id);

                                // databaseReference.child(id1).setValue(id1);


                                if (category1.equals("WaterSupply")) {
                                    databaseReference.child(id1).child(id).setValue("Not Uploaded");

                                    databaseReference.child(id1).child(id).child("email").setValue(emailstr);
                                    databaseReference.child(id1).child(id).child("complain").setValue(compalinstr);
                                    databaseReference.child(id1).child(id).child("category").setValue(category1);
                                    databaseReference.child(id1).child(id).child("date").setValue(DateTostr);
                                    databaseReference.child(id1).child(id).child("name").setValue(namestr);
                                    databaseReference.child(id1).child(id).child("area").setValue(areastr);
                                    databaseReference.child(id1).child(id).child("pno").setValue(pnostr);
                                    databaseReference.child(id1).child(id).child("complainid").setValue(id);
                                    databaseReference.child(id1).child(id).child("status").setValue("Submitted");

                                    databaseReferencewater.child(id).setValue("Not Uploaded");
                                    databaseReferencewater.child(id).child("uid").setValue(uid);
                                    databaseReferencewater.child(id).child("email").setValue(emailstr);
                                    databaseReferencewater.child(id).child("complain").setValue(compalinstr);
                                    databaseReferencewater.child(id).child("date").setValue(DateTostr);
                                    databaseReferencewater.child(id).child("name").setValue(namestr);
                                    databaseReferencewater.child(id).child("area").setValue(areastr);
                                    databaseReferencewater.child(id).child("pno").setValue(pnostr);
                                    databaseReferencewater.child(id).child("complainid").setValue(id);
                                    databaseReferencewater.child(id).child("status").setValue("Submitted");

                                } else if (category1.equals("StreetLight")) {
                                    databaseReference.child(id1).child(id).setValue("Not Uploaded");
                                    databaseReference.child(id1).child(id).child("email").setValue(emailstr);
                                    databaseReference.child(id1).child(id).child("complain").setValue(compalinstr);
                                    databaseReference.child(id1).child(id).child("category").setValue(category1);
                                    databaseReference.child(id1).child(id).child("date").setValue(DateTostr);
                                    databaseReference.child(id1).child(id).child("name").setValue(namestr);
                                    databaseReference.child(id1).child(id).child("area").setValue(areastr);
                                    databaseReference.child(id1).child(id).child("pno").setValue(pnostr);
                                    databaseReference.child(id1).child(id).child("complainid").setValue(id);
                                    databaseReference.child(id1).child(id).child("status").setValue("Submitted");

                                    databaseReferencestreet.child(id).setValue("Not Uploaded");
                                    databaseReferencestreet.child(id).child("uid").setValue(uid);
                                    databaseReferencestreet.child(id).child("pno").setValue(pnostr);
                                    databaseReferencestreet.child(id).child("email").setValue(emailstr);
                                    databaseReferencestreet.child(id).child("complain").setValue(compalinstr);
                                    databaseReferencestreet.child(id).child("date").setValue(DateTostr);
                                    databaseReferencestreet.child(id).child("name").setValue(namestr);
                                    databaseReferencestreet.child(id).child("area").setValue(areastr);
                                    databaseReferencestreet.child(id).child("complainid").setValue(id);
                                    databaseReferencestreet.child(id).child("status").setValue("Submitted");

                                } else if (category1.equals("StrayAnimal")) {
                                    databaseReference.child(id1).child(id).setValue("Not Uploaded");
                                    databaseReference.child(id1).child(id).child("email").setValue(emailstr);
                                    databaseReference.child(id1).child(id).child("complain").setValue(compalinstr);
                                    databaseReference.child(id1).child(id).child("category").setValue(category1);
                                    databaseReference.child(id1).child(id).child("date").setValue(DateTostr);
                                    databaseReference.child(id1).child(id).child("name").setValue(namestr);
                                    databaseReference.child(id1).child(id).child("area").setValue(areastr);
                                    databaseReference.child(id1).child(id).child("pno").setValue(pnostr);
                                    databaseReference.child(id1).child(id).child("complainid").setValue(id);
                                    databaseReference.child(id1).child(id).child("status").setValue("Submitted");

                                    databaseReferenceanimal.child(id).setValue("Not Uploaded");
                                    databaseReferenceanimal.child(id).child("uid").setValue(uid);
                                    databaseReferenceanimal.child(id).child("pno").setValue(pnostr);
                                    databaseReferenceanimal.child(id).child("email").setValue(emailstr);
                                    databaseReferenceanimal.child(id).child("complain").setValue(compalinstr);
                                    databaseReferenceanimal.child(id).child("date").setValue(DateTostr);
                                    databaseReferenceanimal.child(id).child("name").setValue(namestr);
                                    databaseReferenceanimal.child(id).child("area").setValue(areastr);
                                    databaseReferenceanimal.child(id).child("complainid").setValue(id);
                                    databaseReferenceanimal.child(id).child("status").setValue("Submitted");

                                } else if (category1.equals("Drainage")) {
                                    databaseReference.child(id1).child(id).setValue("Not Uploaded");
                                    databaseReference.child(id1).child(id).child("email").setValue(emailstr);
                                    databaseReference.child(id1).child(id).child("complain").setValue(compalinstr);
                                    databaseReference.child(id1).child(id).child("category").setValue(category1);
                                    databaseReference.child(id1).child(id).child("date").setValue(DateTostr);
                                    databaseReference.child(id1).child(id).child("name").setValue(namestr);
                                    databaseReference.child(id1).child(id).child("area").setValue(areastr);
                                    databaseReference.child(id1).child(id).child("pno").setValue(pnostr);
                                    databaseReference.child(id1).child(id).child("complainid").setValue(id);
                                    databaseReference.child(id1).child(id).child("status").setValue("Submitted");


                                    databaseReferencestroam.child(id).setValue("Not Uploaded");
                                    databaseReferencestroam.child(id).child("uid").setValue(uid);
                                    databaseReferencestroam.child(id).child("pno").setValue(pnostr);
                                    databaseReferencestroam.child(id).child("email").setValue(emailstr);
                                    databaseReferencestroam.child(id).child("complain").setValue(compalinstr);
                                    databaseReferencestroam.child(id).child("date").setValue(DateTostr);
                                    databaseReferencestroam.child(id).child("name").setValue(namestr);
                                    databaseReferencestroam.child(id).child("area").setValue(areastr);
                                    databaseReferencestroam.child(id).child("complainid").setValue(id);
                                    databaseReferencestroam.child(id).child("status").setValue("Submitted");

                                }

                                new CustomToast().Show_Toast(getActivity(), view,
                                        "Complain issued");


                               // name.setText("");
                              //  pno.setText("");
                                area.setText("");
                              //  email.setText("");
                                complain.setText("");
                                textView.setText("");
                                spinner.setSelection(0);
                                SelectImage.setImageBitmap(null);
                                UploadButton.setText("Upload image");


                            }
                        }


                        //Sets the Favourite Book property.
                        firebaseAnalytics.setUserProperty("CATEGORY",textView.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(cid));
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,textView.getText().toString());
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        //Sets the Favourite Author property.
                        //firebaseAnalytics.setUserProperty("",favouriteAuthor.getText().toString());


                    }
                } catch (Exception ae) {
                    System.out.println(ae);
                }
            }
        });



        return view;


    }





    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
if (resultCode == RESULT_OK ) {
    if (requestCode == Image_Request_Code && data != null && data.getData() != null) {
            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);
                // After selecting image change choose button above text.
                UploadButton.setText("Image Selected");

            } catch (IOException e) {

                e.printStackTrace();
            }

    }
     if ( requestCode== PLACE_PICKER_REQUEST)
     {
         //______create place object from the received intent.
         Place place = PlacePicker.getPlace(getActivity(),data);
         //______get place name from place object
         area.setText(hereLocation(place.getLatLng().latitude, place.getLatLng().longitude));
     }

}

    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public String hereLocation(double lat, double lon) {
        String addr = "";
        Geocoder geocorder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addrlist;
        try {
            addrlist = geocorder.getFromLocation(lat, lon, 1);
            if (addrlist.size() > 0) {
                String add = addrlist.get(0).getAddressLine(0);
                String postalcode = addrlist.get(0).getPostalCode();
                addr = add;
                //+ ",  " + postalcode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addr;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Connection failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Context context;
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity() );
            mGoogleApiClient.disconnect();
        }
    }


}


