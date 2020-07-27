package com.wazir.warehousing;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.Management.ManagerMainActivity;
import com.wazir.warehousing.ModelObject.UserInfoType;
import com.wazir.warehousing.Worker.WorkerMainActivity;

import java.util.concurrent.TimeUnit;

import static com.wazir.warehousing.GloabalFunctions.Constants.USER_MANAGER;
import static com.wazir.warehousing.GloabalFunctions.Constants.USER_WORKER;

public class LoginSignupActivity extends AppCompatActivity {

    // android views
    CardView sendOtpCommand, verifyOtpCommand;
    TextInputLayout contactNumber, otpGenerated;
    LottieAnimationView loading;
    TextView timer;

    // JAVA objects
    String verId, tempNumber;
    UserInfoType userInfoType;
    String TAG = "LoginSignupActivity";

    // Firebase Stuff
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        initUi();
        clickListeners();
    }

    void initUi() {
        sendOtpCommand = findViewById(R.id.cardView3);
        verifyOtpCommand = findViewById(R.id.cardView4);
        contactNumber = findViewById(R.id.phone_number_id);
        otpGenerated = findViewById(R.id.otp_number_id);
        loading = findViewById(R.id.loading_anim_view);
        timer = findViewById(R.id.textView27);
        loading.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    void startCountdown(int time) {
        timer.setVisibility(View.VISIBLE);
        sendOtpCommand.setVisibility(View.GONE);
        verifyOtpCommand.setVisibility(View.VISIBLE);
        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("0:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setVisibility(View.INVISIBLE);
                sendOtpCommand.setVisibility(View.VISIBLE);
                verifyOtpCommand.setVisibility(View.GONE);
            }
        }.start();
    }

    void clickListeners() {
        sendOtpCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempNumber = contactNumber.getEditText().getText().toString();
                sendOtpCommand.setEnabled(false);
                loading.setVisibility(View.VISIBLE);
                startCountdown(59000);
                if (tempNumber.length() == 10) {
                    checkUserExists("+91" + tempNumber);
                } else {
                    Toast.makeText(LoginSignupActivity.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                }
            }
        });

        verifyOtpCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!otpGenerated.getEditText().getText().toString().equals("")) {
                    siWiOt(otpGenerated.getEditText().getText().toString(), null);
                    loading.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    void checkUserExists(final String userNumber) {
        db.collection("USERS")
                .document(userNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists() && task.isSuccessful()) {
                            userInfoType = task.getResult().toObject(UserInfoType.class);
                            sendOtsi(userNumber);
                        } else {
                            Toast.makeText(LoginSignupActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void sendOtsi(String number) {
        Log.d(TAG, "sendOtsi: started");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                LoginSignupActivity.this,
                mCallbacks
        );
        Log.d(TAG, "sendOtsi: Finished");
    }

    void siWiOt(String otp, PhoneAuthCredential cred) {
        final PhoneAuthCredential credential;
        if (cred == null && otp != null) {
            credential = PhoneAuthProvider.getCredential(verId, otp);
        } else {
            credential = cred;
        }
        SharedPrefsManager.getInstance(LoginSignupActivity.this).storeWarehouseId(userInfoType.getWarehouseId());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginSignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (userInfoType.getUserType().equals(USER_WORKER)) {
                                startActivity(new Intent(LoginSignupActivity.this, WorkerMainActivity.class));
                                SharedPrefsManager.getInstance(LoginSignupActivity.this).setUserType(USER_WORKER);
                                SharedPrefsManager.getInstance(LoginSignupActivity.this).storeWarehouseId(userInfoType.getWarehouseId());
                                SharedPrefsManager.getInstance(LoginSignupActivity.this).saveUserName(userInfoType.getUserName());
                                finish();
                            } else if (userInfoType.getUserType().equals(USER_MANAGER)) {
                                startActivity(new Intent(LoginSignupActivity.this, ManagerMainActivity.class));
                                SharedPrefsManager.getInstance(LoginSignupActivity.this).setUserType(USER_MANAGER);
                                SharedPrefsManager.getInstance(LoginSignupActivity.this).storeWarehouseId(userInfoType.getWarehouseId());
                                SharedPrefsManager.getInstance(LoginSignupActivity.this).saveUserName(userInfoType.getUserName());
                                finish();
                            }
                        }
                    }
                });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            siWiOt(null, credential);
            loading.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            loading.setVisibility(View.INVISIBLE);
            sendOtpCommand.setEnabled(true);
            Toast.makeText(LoginSignupActivity.this, "Failed To Verify User", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            verId = verificationId;
            Toast.makeText(LoginSignupActivity.this, "OTP sent", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            sendOtpCommand.setEnabled(false);
        }
    };
}