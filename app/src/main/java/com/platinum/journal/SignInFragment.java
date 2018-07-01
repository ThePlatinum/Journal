package com.platinum.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.util.concurrent.Executor;

/**
 * Created by PLATINUM
 * Date 6/29/2018
 * Time 5:04 PM
 * Package com.platinum.journal
 * Project Journal
 */

public class SignInFragment extends Fragment {

    public EditText mEmailView;
    public EditText mPasswordView;
    public ProgressBar mProgressView;
    public TextView loginMessage;

    Button SignIn_btn , Google_sign_in_btn , Forgot_Password_btn;

    public final int RC_SIGN_IN = 10;
    GoogleApiClient googleApiClient;
    FirebaseAuth mFirebaseAuth;

    public SignInFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin,container,false);
        mEmailView = (EditText)view.findViewById(R.id.signin_email);
        mPasswordView = (EditText)view.findViewById(R.id.signin_password);
        mProgressView = (ProgressBar)view.findViewById(R.id.signin_progress);
        loginMessage = (TextView)view.findViewById(R.id.loginMessage);
        SignIn_btn = (Button)view.findViewById(R.id.email_sign_in_button);
        Forgot_Password_btn = (Button)view.findViewById(R.id.forgotPassword);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Google_sign_in_btn = (Button)view.findViewById(R.id.sign_in_using_google);

        Google_sign_in_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GoogleSignInButtonPressed();
            }
        });

        Forgot_Password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_password_fragment forgot_password = new forgot_password_fragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(forgot_password,"Recover Password");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        SignIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInButtonPressed();
            }
        });

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

    public void SignInButtonPressed() {
        String Email = mEmailView.getText().toString();
        String Password = mPasswordView.getText().toString();

        if (Email.isEmpty() || Password.isEmpty()){
            if (Email.isEmpty()) {
                mEmailView.setHint(R.string.email_empty);
            }
            if (Password.isEmpty()) {
                mPasswordView.setHint("Field can't be empty");
            }
        }
        else {
            mProgressView.setVisibility(View.VISIBLE);
            ActionLogin(Email,Password);
        }
    }

    public void ActionLogin(String Email , String Password){
        mFirebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent();
                            intent.setClass(getActivity().getApplicationContext(),ListOfNotesActivity.class);
                            startActivity(intent);
                        }
                        else{
                            mProgressView.setVisibility(View.GONE);
                            String e = "";
                            try{
                                e = ((FirebaseAuthException)task.getException()).getErrorCode();
                            }
                            catch (NullPointerException nullException){
                                nullException.printStackTrace();
                            }
                            switch (e){
                                case "ERROR_INVALID_EMAIL":
                                    loginMessage.setText("Please Check the Email and try again\n");
                                    break;
                                case "ERROR_WRONG_PASSWORD":
                                    loginMessage.setText("Wrong password, Please check and try again\n");
                                    break;
                                default:
                                    loginMessage.setText("Network Connection Error\n Please Check your Connection and try again");
                            }
                        }
                    }
                });
    }

    ProgressBar progressBar;
    public void GoogleSignInButtonPressed() {
        progressBar = new ProgressBar(getActivity().getApplicationContext(),null,android.R.attr.progressBarStyle);
        progressBar.setVisibility(View.VISIBLE);
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
                            progressBar.setVisibility(View.GONE);
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
