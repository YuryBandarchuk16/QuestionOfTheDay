package com.bandarchuk.yury.questionoftheday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends Activity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
    }

    public void signInButtonOnClick(View view) {
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
        Button button = (Button)findViewById(R.id.buttonSignIn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                loadUserIntoMainActivity();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);
    }

    private void signInWithEmailAndPassword(String email, String password) {
        Log.d("KEK", "HERE!");
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("KEK", "signInWithEmail:success");
                    loadUserIntoMainActivity();
                } else {
                    Log.d("KEK", "signInWithEmail:failure", task.getException());
                }
            }
        });
    }

    private void loadUserIntoMainActivity() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        //User user = new User(firebaseUser.getUid());
        //CurrentSession.sharedInstance().setCurrentUser(user);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
