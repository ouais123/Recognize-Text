package wifi.codewl.recognizetext.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Objects;

import wifi.codewl.recognizetext.Controller.AdapterImage;
import wifi.codewl.recognizetext.Model.Image;
import wifi.codewl.recognizetext.Path.Status;
import wifi.codewl.recognizetext.R;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CAMERA_CODE = 16;
    private static final int IMAGE_PICK_GALLERY_CODE = 41;
    private static final int REQUEST_PERMISSION = 12;

    public static final String FILE = "file";
    public static final String LANGUAGE = "language";

    private static Animation rotateOpen;
    private static Animation rotateClose;
    private static Animation translateOpen;
    private static Animation translateClose;


    private Uri imageUri;

    private RecyclerView recyclerView;
    public static SwipeRefreshLayout refreshLayout;

    private AdapterImage adapterImage;
    private LinkedList<Image> list;

    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkPermission1() || checkPermission2() || checkPermission3())
            requestPermission();


        refreshLayout = findViewById(R.id.swipeRefresh_main);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary,getTheme()));
        refreshLayout.setEnabled(false);

        list = new LinkedList<>(Status.list);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(list.size());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterImage = new AdapterImage(this,list);
        recyclerView.setAdapter(adapterImage);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapterImage.removeAt(position);
                Status.list.remove(position);
            }
        }).attachToRecyclerView(recyclerView);




        final FloatingActionButton fab = findViewById(R.id.fab);
        final FloatingActionButton fab2 = findViewById(R.id.fab2);
        final FloatingActionButton fab3 = findViewById(R.id.fab3);

        rotateOpen = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_open_animation);
        rotateOpen.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onAnimationEnd(Animation arg0) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_clear_24,getTheme()));
            }
        });

        rotateClose = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_close_animation);
        rotateClose.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onAnimationEnd(Animation arg0) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_photo_alternate_24,getTheme()));
            }
        });

        translateOpen = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_buttom_animation);
        translateClose = AnimationUtils.loadAnimation(MainActivity.this, R.anim.to_bottom_animation);

        final boolean[] status = {false};
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {

                if(status[0]){
                    fab2.setVisibility(View.INVISIBLE);
                    fab3.setVisibility(View.INVISIBLE);
                    fab.startAnimation(rotateClose);
                    fab2.startAnimation(translateClose);
                    fab3.startAnimation(translateClose);
                    status[0] =false;
                } else {
                    fab2.setVisibility(View.VISIBLE);
                    fab3.setVisibility(View.VISIBLE);
                    fab.startAnimation(rotateOpen);
                    fab2.startAnimation(translateOpen);
                    fab3.startAnimation(translateOpen);
                    status[0] = true;
                }
            }
        });



        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Gallery");
                getImageGallery();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Camera");
                getImageCamera();
            }
        });



        /*ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File folder = contextWrapper.getDir("tessdata", Context.MODE_PRIVATE);*/

        File folder = getExternalFilesDir("tessdata");
        Status.folder = getExternalFilesDir("tessdata");
        Status.data = getExternalFilesDir(null);
        sharedPreferences = getSharedPreferences(FILE,0);
        Status.language = sharedPreferences.getString(LANGUAGE,"eng");

        File file1 = new File(Status.folder,"ara.cube.bigrams");
        File file2 = new File(Status.folder,"ara.cube.fold");
        File file3 = new File(Status.folder,"ara.cube.lm");
        File file4 = new File(Status.folder,"ara.cube.nn");
        File file5 = new File(Status.folder,"ara.cube.params");
        File file6 = new File(Status.folder,"ara.cube.size");
        File file7 = new File(Status.folder,"ara.cube.word-freq");
        File file8 = new File(Status.folder,"ara.traineddata");
        File file9 = new File(Status.folder,"eng.traineddata");

        if(!file1.exists() || !file2.exists() || !file3.exists() || !file4.exists() ||
                !file5.exists() || !file6.exists() || !file7.exists() || !file8.exists() || !file9.exists()){
            copyAssets(folder);
        }


    }

    private void copyAssets(File folder) {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null)
            for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(folder, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private void getImageCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image To Text");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void getImageGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkPermission1(){
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermission2(){
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermission3(){
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION){
            if(checkPermission1() || checkPermission2() || checkPermission3()) {
                finish();
            }
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
            if(requestCode == IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                ImageView mPreviewIv = new ImageView(this);
                mPreviewIv.setImageURI(resultUri);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                final Bitmap bitmap = bitmapDrawable.getBitmap();
                adapterImage.addAt(new Image(bitmap,""));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.Translation:
                startActivity(new Intent(this,TranslationActivity.class));
                return true;
            case R.id.AboutAs:
                startActivity(new Intent(this,AboutAsActivity.class));
                return true;
            case R.id.GetHelp:
                startActivity(new Intent(this,HelpActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}