package com.com.smartjoseph.smart_journal;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.com.smartjoseph.smart_journal.view.NoteEditActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class Login extends AppCompatActivity
//        implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener
{


//    private static final String TAG = "GoogleActivity";
//    private static final int RC_SIGN_IN = 9001;
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    private GoogleApiClient mGoogleApiClient;
    Button sign_out;
    SignInButton signin;
    TextView tvname;
    TextView firstThought;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstThought = (TextView) findViewById(R.id.smart_thought);

        final String myFirst_thought = getResources().getString(R.string.first_thought);
        firstThought.setText(myFirst_thought);

        final String[]smartThoughtArray = {"Everyday may not be a good day\nbut there is good in every day",
                "If you are always trying to be normal,\nyou will never know how amazing you can be.\nMaya Angelou ",
                "The truly rich are those who enjoy what they have.\nYiddish Proverb",
        "Empty pockets never held anyone back.\nOnly empty heads and empty hearts can do that.\nNorman Vincent Peale",
        "A problem is a chance for you to do your best.\nDuke Ellington",
        " I aspire to inspire before I expire.\nPravinee Hurbungs",
        "When you come to the end of your rope,\ntie a knot and hang on.\nFranklin Roosevelt",
        "The only way to do great work is to love what you do.\nIf you haven't found it yet, keep looking.\nSteve Jobs"};


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            @Override
            public void run() {
                firstThought.setText(smartThoughtArray[i]);
                i++;
                if (i > smartThoughtArray.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 6000);
            }
        };
        handler.postDelayed(runnable, 6000);
    }

//        signin = (SignInButton) findViewById(R.id.sign_in_button);
//
//
//        sign_out= (Button) findViewById(R.id.sign_out);
//        sign_out.setVisibility(View.GONE);
//        tvname = (TextView) findViewById(R.id.name);
//
//        sign_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//
//                int i = v.getId();
//                if(i==R.id.sign_in_button){
//
//
//                }
//                signOut();
//            }
//        });
//
////		findViewById(R.id.sign_in_button).setOnClickListener(MainActivity.this);
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(Login.this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        mAuth = FirebaseAuth.getInstance();
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//
//        if (i==R.id.sign_in_button){
//
//            signIn();
//
//        }
//    }
//
//    public void signIn(){
//
//        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(intent, RC_SIGN_IN);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==RC_SIGN_IN){
//
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()){
//
//                GoogleSignInAccount account = result.getSignInAccount();
//
//                firebaseAuthWithGoogle(account);
//            }
//
//        }
//    }
//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        Toast.makeText(Login.this,""+credential.getProvider(),Toast.LENGTH_LONG).show();
//
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//                        String name = getdata();
//
//                        if (task.isSuccessful()){
//                            sign_out.setVisibility(View.VISIBLE);
//                            signin.setVisibility(View.GONE);
//                            tvname.setText("Welcome "+name);
//
//                        }else {
//                            Toast.makeText(Login.this,"Something went wrong",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
//
//    public void signOut() {
//        // Firebase sign out
//        mAuth.signOut();
//
//
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(@NonNull Status status) {
//                        //  Intent intent = new Intent(MainActivity.this,MainActivity.class);
//                        //  startActivity(intent);
//                        Toast.makeText(Login.this, "Logout", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//        sign_out.setVisibility(View.GONE);
//        tvname.setText(null);
//        signin.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//
//    public String getdata(){
//        String name = null;
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Name, email address, and profile photo Url
//            name = user.getEmail();
//            //  String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getToken() instead.
//            String uid = user.getUid();
//        }
//
//        return name;
//    }

    public void goToEditNoteAct(View v) {

        Intent intent = new Intent(Login.this,NoteEditActivity.class);
        startActivity(intent);
    }

}
