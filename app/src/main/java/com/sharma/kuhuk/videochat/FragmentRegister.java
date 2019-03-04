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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRegister extends Fragment {

    private EditText userName, userEmail, userPassword;
//    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private FirebaseAuth firebaseAuth;

    public FragmentRegister() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        setupUIViews(view);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //upload data to Firebase Auth
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                Database.onRegister(userName.getText().toString(), userEmail.getText().toString());
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.PREF_SP_FILE_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constants.PREF_USER_EMAIL, userEmail.getText().toString());
                                editor.apply();
                                startActivity(new Intent(getActivity(), HomeActivity.class)); //start here
                            } else {
                                Toast.makeText(getContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_register, new FragmentLogin());
                ft.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    private void setupUIViews(View view) {
        userName = view.findViewById(R.id.etName);
        userEmail = view.findViewById(R.id.etEmail);
        userPassword = view.findViewById(R.id.etPassword);
        tvLogin = view.findViewById(R.id.tvLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
    }

    private Boolean validate() {
        Boolean result = false;

        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty())
            Toast.makeText(getContext(), "Please enter all the details.", Toast.LENGTH_SHORT).show();
        else
            result = true;
        return result;
    }
}
