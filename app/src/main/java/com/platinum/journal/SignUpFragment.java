package com.platinum.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.GoogleAuthProvider;


/**
 * Created by PLATINUM
 * Date 6/29/2018
 * Time 4:57 PM
 * Package com.platinum.journal
 * Project Journal
 */

public class SignUpFragment extends Fragment {

    public EditText mEmailView;
    public EditText choosePassword , confirmPassword;
    public ProgressBar mProgressView;
    public TextView signUpMessage;

    Button SignUp_btn , Google_sign_up_btn ;
    public final int RC_SIGN_IN = 10;
    GoogleApiClient googleApiClient;
    public FirebaseAuth mFirebaseAuth ;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup,container,false);

        mEmailView = (EditText)view.findViewById(R.id.signUp_email);
        choosePassword = (EditText)view.findViewById(R.id.choose_password);
        confirmPassword = (EditText)view.findViewById(R.id.confirm_password);
        mProgressView = (ProgressBar)view.findViewById(R.id.signUp_progress);
        signUpMessage = (TextView)view.findViewById(R.id.signUp_Message);
        SignUp_btn = (Button)view.findViewById(R.id.email_sign_up_button);
        Google_sign_up_btn = (Button)view.findViewById(R.id.sign_up_using_google);

        SignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpButtonPressed();
            }
        });

        Google_sign_up_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GoogleSignUpButtonPressed();
            }
        });


        mFirebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    private void SignUpButtonPressed() {
        String Email = mEmailView.getText().toString();
        String ChoosePassword = choosePassword.getText().toString();
        String ConfirmPassword = confirmPassword.getText().toString();

        if(Email.isEmpty() || ChoosePassword.isEmpty() || !ChoosePassword.equals(ConfirmPassword)){
            if (Email.isEmpty()) {
                mEmailView.setHint("Field can't be empty");
            }
            if (ChoosePassword.isEmpty()) {
                signUpMessage.setText("Enter Password");
            }
            if(!ChoosePassword.equals(ConfirmPassword)){
                signUpMessage.setText("Password Mismatch");
            }
        }
        else {
            mProgressView.setVisibility(View.VISIBLE);
            ActionSignUp(Email,ChoosePassword);
        }
    }

    public void ActionSignUp(String Email , String Password) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent();
                    intent.setClass(getActivity().getApplicationContext(),ListOfNotesActivity.class);
                    mProgressView.setVisibility(View.GONE);
                    getActivity().finish();
                    startActivity(intent);
                }
                else {
                    mProgressView.setVisibility(View.GONE);
                    String e = "";
                    try {
                        e = ((FirebaseAuthException) task.getException()).getErrorCode();
                    } catch (NullPointerException nullException) {
                        nullException.printStackTrace();
                    }
                    switch (e) {
                        case "ERROR_INVALID_EMAIL":
                            signUpMessage.setText("Please Check the Email and try again\n");
                            break;
                        default:
                            signUpMessage.setText("Network Connection Error\n Please Check your Connection and try again");
                    }
                }
            }
        });
    }

    public void GoogleSignUpButtonPressed() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
                firebaseAuthWithGoogle(googleSignInAccount);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent();
                            intent.setClass(getActivity().getApplicationContext(),ListOfNotesActivity.class);
                            getActivity().finish();
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
}
