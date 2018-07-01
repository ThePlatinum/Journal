package com.platinum.journal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by PLATINUM
 * Date 7/1/2018
 * Time 12:10 AM
 * Package com.platinum.journal
 * Project Journal
 */

public class forgot_password_fragment extends Fragment {

    EditText recoverEmail;
    ImageButton recoverEmail_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.forgot_password_fragment,container,false);

        recoverEmail = (EditText)view.findViewById(R.id.email_recover_editText);
        recoverEmail_btn = (ImageButton)view.findViewById(R.id.recover_email_btn);

        recoverEmail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recoverEmailString = recoverEmail.getText().toString();
                if(!recoverEmailString.isEmpty()){
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.sendPasswordResetEmail(recoverEmailString)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext().getApplicationContext(),"Password reset link sent successfully",Toast.LENGTH_LONG).show();
                                    getExitTransition();
                                }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext().getApplicationContext(),"Could not send reset link, Please try again",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        return view;
    }
}
