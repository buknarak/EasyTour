package com.kmutpnb.buk.easytour;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.RequestHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ShowDetaiUserActivity extends AppCompatActivity implements View.OnClickListener  {


    private String userString, passString, nameString, positionString,emailString,telString;
    private RadioGroup positionRadioGroup;
    private TextView userTextView, passTextView, nameTextView, posTextView,emailTextView ,telTextView;

 //photo
    ImageView imgView;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    static String strSDCardPathName = Environment.getExternalStorageDirectory() + "/temp_picture" + "/";
    static String strURLUpload = "http://swiftcodingthai.com/puk/uploadfile1.php";
 //  static String strURLUpload = "http://swiftcodingthai.com/puk/uploadfile5.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detai_user);

        //bindwidget
        bindWidget();


        //show textview
        showTextView();

        radioController();


        photo();


    }//on create

    private void photo() {

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // *** Create Folder
        createFolder();
// *** ImageView
        imgView = (ImageView) findViewById(R.id.ivImage);
// *** Take Photo
        final Button btnTakePhoto = (Button) findViewById(R.id.btnSelectPhoto);
// Perform action on click
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
// Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                    }
// Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });
        // *** Upload Photo
        final Button btnUpload = (Button) findViewById(R.id.btnupload);
// Perform action on click

        btnUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
// *** Upload file to Server
               uploadFiletoServer(mCurrentPhotoPath, strURLUpload);
             Log.d("test22", mCurrentPhotoPath);
                Log.d("test22", strURLUpload);

            }
        });

    }//method photo

    private File createImageFile() throws IOException {
// Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(strSDCardPathName);
        File image = File.createTempFile(imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
// Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        imgView.setImageBitmap(bitmap);

        }

        }

public static boolean uploadFiletoServer(String strSDPath, String strUrlServer) {

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int resCode = 0;

        String resMessage = "";

        String lineEnd = "\r\n";

        String twoHyphens = "--";

        String boundary = "*****";

        try {

        File file = new File(strSDPath);

        if (!file.exists()) {
        return false;
        }
        FileInputStream fileInputStream = new FileInputStream(new File(strSDPath));
        URL url = new URL(strUrlServer);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
        outputStream.writeBytes(
        "Content-Disposition: form-data; name=\"filUpload\";filename=\"" + strSDPath + "\"" + lineEnd);
        outputStream.writeBytes(lineEnd);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

// Read file
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
        outputStream.write(buffer, 0, bufferSize);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        outputStream.writeBytes(lineEnd);
        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

// Response Code and Message
        resCode = conn.getResponseCode();
        if (resCode == HttpURLConnection.HTTP_OK) {
        InputStream is = conn.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int read = 0;

        while ((read = is.read()) != -1) {
        bos.write(read);
        }
        byte[] result = bos.toByteArray();
        bos.close();
        resMessage = new String(result);
        }
        fileInputStream.close();
        outputStream.flush();
        outputStream.close();

        return true;
        } catch (Exception ex) {
// Exception handling
        return false;
        }

        }
public static void createFolder() {
        File folder = new File(strSDCardPathName);
        try {
// Create folder.
        if (!folder.exists()) {
        folder.mkdir();
        }
        } catch (Exception ex) {
        }
        }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
        }

    private void showTextView() {

        nameString = getIntent().getStringExtra("Name");
        userString = getIntent().getStringExtra("User");
        positionString = getIntent().getStringExtra("Status");
        passString = getIntent().getStringExtra("Pass");
        emailString = getIntent().getStringExtra("Email");
        telString = getIntent().getStringExtra("Tel");
        String status1 = getIntent().getStringExtra("Status1");

        Log.d("ShowSt", status1);

        if (status1.equals("1")) {
            ///  0 "สถานะ : นักท่องเที่ยว"
            //  Log.d("260459", " สถานะ "+ i);

            nameTextView.setText(nameString);
            userTextView.setText(userString);
            passTextView.setText(passString);
            emailTextView.setText(emailString);
            telTextView.setText(telString);
        } else {
            //  Log.d("260459", " สถานะ "+ i);
            nameTextView.setText(nameString);
            userTextView.setText(userString);
            TextView tvpasstext = (TextView) findViewById(R.id.tvpasstext);
            tvpasstext.setVisibility(View.GONE);
            passTextView.setText("");
            emailTextView.setText(emailString);
            telTextView.setText(telString);
            Log.d("ShowSt", positionString);
        }

//        nameTextView.setText(nameString);
//        userTextView.setText(userString);
//        passTextView.setText(passString);
//        emailTextView.setText(emailString);
//        telTextView.setText(telString);
          //     Log.d("ShowSt", positionString);


        if (positionString.equals("นักท่องเที่ยว")) {
                posTextView.setText("นักท่องเที่ยว");

//            positionRadioGroup.check(R.id.rdTour);
        } else {
            posTextView.setText(positionString);

         //  Log.d("ShowSt", positionString);
//            positionRadioGroup.check(R.id.rdAdmin);
        }

    }

    private void bindWidget() {

        nameTextView = (TextView) findViewById(R.id.tvNamee);
        userTextView = (TextView) findViewById(R.id.tvUserr);
        passTextView = (TextView) findViewById(R.id.tvPasss);
        posTextView = (TextView) findViewById(R.id.tvpos);
        emailTextView = (TextView) findViewById(R.id.tvemail);
        telTextView = (TextView) findViewById(R.id.tvtel);



        // positionRadioGroup = (RadioGroup) findViewById(R.id.ragPosition);



    }

    private void radioController() {

    }

    @Override
    public void onClick(View view) {


    }
};
