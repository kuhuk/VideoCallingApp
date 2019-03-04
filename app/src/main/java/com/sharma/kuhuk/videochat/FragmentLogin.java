package com.sharma.kuhuk.videochat;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment {

    TextView tvSignUp;
    Button btnLLogin;
    private EditText etLUserEmail, etLpassword;
    private FirebaseAuth firebaseAuth;
    Boolean result = false;

    public FragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setUpUIViews(view);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
//            startActivity(new Intent(getActivity(), HomeActivity.class));
        }

        btnLLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() && verify(etLUserEmail.getText().toString(), etLpassword.getText().toString())) {
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.PREF_SP_FILE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.PREF_USER_EMAIL, etLUserEmail.getText().toString());
                    editor.apply();

                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_register, new FragmentRegister());
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setUpUIViews(View view) {
        tvSignUp = view.findViewById(R.id.tvSignUp);
        btnLLogin = view.findViewById(R.id.btnLLogin);
        etLUserEmail = view.findViewById(R.id.etLUserEmail);
        etLpassword = view.findViewById(R.id.etLpassword);
    }

    private Boolean verify(final String userEmail, String userPassword) {

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    result = true;
                } else {
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return result;
    }

    private Boolean validate () {
        Boolean result = false;

        String name = etLUserEmail.getText().toString();
        String password = etLpassword.getText().toString();

        if (name.isEmpty() || password.isEmpty())
            Toast.makeText(getContext(), "Please enter all the details.", Toast.LENGTH_SHORT).show();
        else
            result = true;
        return result;
    }
}
