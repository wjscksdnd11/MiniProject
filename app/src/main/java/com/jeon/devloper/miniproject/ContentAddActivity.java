package com.jeon.devloper.miniproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jeon.devloper.miniproject.Data.ContentData;
import com.jeon.devloper.miniproject.Data.NetworkResult;
import com.jeon.devloper.miniproject.manager.NetworkManager;
import com.jeon.devloper.miniproject.manager.NetworkRequest;
import com.jeon.devloper.miniproject.request.UploadRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentAddActivity extends AppCompatActivity {

    @BindView(R.id.edit_message)
    EditText messageView;
    @BindView(R.id.image_picture)
    ImageView pictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_add);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            String path = savedInstanceState.getString(FIELD_SAVE_FILE);
            if (!TextUtils.isEmpty(path)) {
                savedFile = new File(path);
            }
            path = savedInstanceState.getString(FIELD_UPLOAD_FILE);
            if (!TextUtils.isEmpty(path)) {
                uploadFile = new File(path);
                Glide.with(this)
                        .load(uploadFile)
                        .into(pictureView);
            }
        }
    }

    private static final int RC_GET_IMAGE = 1;
    private static final int RC_CATPURE_IMAGE = 2;

    private static final int INDEX_GALLERY = 0;
    private static final int INDEX_CAMERA = 1;
    @OnClick(R.id.btn_get_image)
    public void onGetImageClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setItems(R.array.select_image, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case INDEX_GALLERY :
                        getGalleryImage();
                        break;
                    case INDEX_CAMERA :
                        getCaptureImage();
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void getGalleryImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, RC_GET_IMAGE);
    }

    private void getCaptureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getSaveFile());
        startActivityForResult(intent, RC_CATPURE_IMAGE);
    }

    private Uri getSaveFile() {
        File dir = getExternalFilesDir("capture");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        savedFile = new File(dir, "my_image_" + System.currentTimeMillis() + ".jpeg");
        return Uri.fromFile(savedFile);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (savedFile != null) {
            outState.putString(FIELD_SAVE_FILE, savedFile.getAbsolutePath());
        }
        if (uploadFile != null) {
            outState.putString(FIELD_UPLOAD_FILE, uploadFile.getAbsolutePath());
        }
    }

    private static final String FIELD_SAVE_FILE = "savedfile";
    private static final String FIELD_UPLOAD_FILE = "uploadfile";

    File savedFile = null;
    File uploadFile = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    uploadFile = new File(path);
                    Glide.with(this)
                            .load(uploadFile)
                            .into(pictureView);
                }
            }
        } else if (requestCode == RC_CATPURE_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uploadFile = savedFile;
                Glide.with(this)
                        .load(uploadFile)
                        .into(pictureView);
            }
        }
    }

    @OnClick(R.id.btn_upload)
    public void onUpload() {
        String content = messageView.getText().toString();
        if (!TextUtils.isEmpty(content) && uploadFile != null) {
            UploadRequest request = new UploadRequest(this, content, uploadFile);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<ContentData>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<ContentData>> request, NetworkResult<ContentData> result) {
                    Toast.makeText(ContentAddActivity.this, "success", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<ContentData>> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(ContentAddActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }
}
