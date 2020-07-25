package com.wazir.warehousing.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.ModelObject.SupportObject;
import com.wazir.warehousing.R;

public class SupportActivity extends AppCompatActivity {
    TextInputLayout titleTIL, descTIL;
    ImageView imagePreview;
    CardView imageSelect, post, removeImage;
    private static final int PICK_IMAGE_REQUEST = 123;
    Uri mImageUri;
    FirebaseStorage storage;
    ProgressBar pb;
    String TAG = "SupportActivitylog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Log.d(TAG, "onCreate: started");
        initUi();
        onClickEvents();
        Log.d(TAG, "onCreate: ended");
    }

    private void initUi() {
        Log.d(TAG, "initUi: started");
        titleTIL = findViewById(R.id.textInputLayout);
        descTIL = findViewById(R.id.textInputLayout2);
        imagePreview = findViewById(R.id.imageView5);
        imageSelect = findViewById(R.id.cardView9);
        post = findViewById(R.id.report_id);
        pb = findViewById(R.id.progressBar);
        storage = FirebaseStorage.getInstance();
        removeImage = findViewById(R.id.id_remove_image);
        Log.d(TAG, "initUi: ended");
    }

    private void onClickEvents() {
        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePreview.setVisibility(View.INVISIBLE);
                mImageUri = null;
                removeImage.setVisibility(View.INVISIBLE);
            }
        });
    }

    void uploadImage() {
        Log.d(TAG, "uploadImage: started");
        if (mImageUri != null) {
            final String name = "uploads/" + System.currentTimeMillis() + "." + getFileExtension(mImageUri);
            storage.getReference().child(name).putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            pb.setProgress(0);
                                        }
                                    }, 5000);
                            Toast.makeText(SupportActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SupportActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            pb.setProgress((int) progress);
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            StorageReference reference = FirebaseStorage.getInstance().getReference(name);
                            SupportObject object = new SupportObject(
                                    titleTIL.getEditText().getText().toString(),
                                    descTIL.getEditText().getText().toString(),
                                    reference.getDownloadUrl().toString(),
                                    FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),
                                    SharedPrefsManager.getInstance(SupportActivity.this).getUserName());
                            FirebaseFirestore.getInstance().collection(SupportActivity.this.getResources().getString(R.string.rootName))
                                    .document(SharedPrefsManager.getInstance(SupportActivity.this).getWarehouseId())
                                    .collection("SUPPORT")
                                    .document()
                                    .set(object)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SupportActivity.this, "Request Uploaded", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "upload session: ended");
                                                finish();
                                            }
                                        }
                                    });

                        }
                    });

        } else {
            Toast.makeText(this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Glide.with(this).load(mImageUri).into(imagePreview);
            imagePreview.setVisibility(View.VISIBLE);
            removeImage.setVisibility(View.VISIBLE);
        }
    }
}