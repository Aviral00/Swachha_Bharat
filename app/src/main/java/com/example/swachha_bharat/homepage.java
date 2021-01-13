package com.example.swachha_bharat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.app.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

public class homepage extends AppCompatActivity {
    View fetchStatus;//New
    EditText cno;
    View complaint, status, mainScreen, form;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    boolean upW = false, downW = false;
    EditText loc, oth;
    ImageView curLoc, issueImage;
    Button upload, log;
    Location gps_loc;
    Location network_loc;
    Location final_loc;
    double longitude;
    double latitude;
    String userAddress;
    Spinner spinner;
    String issue = "None";
    String email_2;
    String image_string;
    /*String email_2 = getIntent().getExtras().getString("email_2");*/
    String[] iList = {"Improper Garbage Disposal", "Unsafe Pothole", "Unsanitary Public Toilet", "Others"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        complaint = findViewById(R.id.complain);
        status = findViewById(R.id.status);
        mainScreen = findViewById(R.id.mainScreen);
        form = findViewById(R.id.form);
        loc = findViewById(R.id.location);
        oth = findViewById(R.id.other);
        curLoc = findViewById(R.id.gps);
        issueImage = findViewById(R.id.issueImage);
        upload = findViewById(R.id.upload);
        spinner = (Spinner) findViewById(R.id.issues);
        cno=findViewById(R.id.complaintnumber);
        fetchStatus = findViewById(R.id.fetchStatus);//New
        log=findViewById(R.id.logButton);
        Intent intent = this.getIntent();
        if(intent !=null)
            email_2 = intent.getStringExtra("email_2");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                if ((iList.length - 1) == position) {
                    oth.setVisibility(View.VISIBLE);
                } else {

                    oth.setVisibility(View.GONE);
                    issue = iList[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, iList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);


        upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });


    }


    //Animation to slide down complain register
    public void slideDown(final View view1, View view2, View view3, View view4) {
        if (upW == false) {
            log.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            Animation animate1 = new ScaleAnimation(
                    view1.getWidth(),                 // fromXDelta
                    view1.getWidth(),                 // toXDelta
                    view1.getHeight(),// fromYDelta
                    view3.getHeight());                // toYDelta
            animate1.setDuration(500);
            animate1.setFillAfter(true);
            view1.startAnimation(animate1);
            TranslateAnimation animate2 = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    0,// fromYDelta
                    view1.getHeight());                // toYDelta
            animate2.setDuration(500);
            animate2.setFillAfter(true);
            view2.startAnimation(animate2);
            upW = true;
            downW = true;
        }
    }

    public void mainBack(View view) {
        Intent intent=getIntent();
        finish();
        startActivity(intent);

    }

    public void logOut(View view){
        Intent intent = new Intent(homepage.this, signin.class);
        startActivity(intent);
    }

    public void slideUp(final View view1, View view2, View view3, View view4) {
        if (downW == false) {
            log.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);

            Animation animate1 = new ScaleAnimation(
                    view2.getWidth(),                 // fromXDelta
                    view2.getWidth(),
                    -view2.getHeight(),// toXDelta
                    -view3.getHeight()// fromYDelta
            );                // toYDelta
            animate1.setDuration(500);
            animate1.setFillAfter(true);

            TranslateAnimation animate2 = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,
                    0,// toXDelta
                    -view1.getHeight()// fromYDelta
            );                // toYDelta
            animate2.setDuration(500);
            animate2.setFillAfter(true);
            view2.startAnimation(animate1);
            view1.startAnimation(animate2);
            downW = true;
            upW=true;
            view4.setVisibility(View.VISIBLE);


        }
    }

    public void checkStatus(View view){
        slideUp(complaint,status,mainScreen,fetchStatus);
    }

    public void check(View view)
    {

        String cid=cno.getText().toString();
        cno.setText("");
        background_status bk = new background_status(this);
        bk.execute(cid);

    }

    public void onComplainClick(View view) {
        slideDown(complaint, status, mainScreen, form);

    }


    //To get geoLocation
    public void onLocationClick(View view) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        try {

            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gps_loc != null) {
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        } else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        } else {
            latitude = 0.0;
            longitude = 0.0;
        }


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        try {

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                userAddress = addresses.get(0).getAddressLine(0);
                loc.setText(userAddress);
            } else {
                userAddress = "Unknown";
                loc.setText(userAddress);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            issueImage.setImageBitmap(photo);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG,10, baos);
            byte [] b=baos.toByteArray();
            image_string = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    public void register(View view)
    {
        /*mainBack(view);*/
        String iLoc=loc.getText().toString();
        if(issue=="Other"){
            issue+="-";
            issue+=oth.getText().toString();
        }

        background_homepage back = new background_homepage(this);
        back.execute(iLoc, issue, email_2,image_string);
    }

}