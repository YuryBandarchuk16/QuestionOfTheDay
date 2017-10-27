package com.bandarchuk.yury.questionoftheday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends Activity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
    }

    public void presentSignInActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
    }

    public void signUpButtonOnClick(View view) {
        String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
        signUpWithEmailAndPassword(username, email, password);
    }

    private void signUpWithEmailAndPassword(final String username, final String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("KEK", "createUserWithEmail:success");
                            //User.addNewUserDocument(auth.getCurrentUser().getUid(), username, email);
                            loadUserIntoMainActivity();
                        } else {
                            Log.d("KEK", "createUserWithEmail:failure", task.getException());
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
