package com.example.assignment1.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment1.MainActivity;
import com.example.assignment1.R;
import com.example.assignment1.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;


public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    FirebaseFirestore database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        //Signup Activity
        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account");
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.email.getText().toString().isEmpty()){
                    binding.email.setError("Enter your email");
                    return;
                }
                if(binding.password.getText().toString().isEmpty()){
                    binding.password.setError("Enter your password");
                    return;
                }
                if(binding.confirmpassword.getText().toString().isEmpty()){
                    binding.confirmpassword.setError("Enter your password");
                    return;
                }
                if (binding.confirmpassword.getText().toString().equals(binding.password.getText().toString())){
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword
                            (binding.email.getText().toString(),binding.password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        FirebaseUser users=auth.getCurrentUser();
                                        Users user=new Users();
                                        user.setEmail(binding.email.getText().toString());
                                        user.setPassword(binding.password.getText().toString());
                                        user.setUserId(users.getUid());
                                        database.collection("User")
                                                .document(user.getUserId())
                                                .set(user, SetOptions.merge());
                                        Toast.makeText(getContext(),"Account Created",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getContext(), MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                else {
                    binding.confirmpassword.setError("Password is not same");
                }
            }
        });
        return binding.getRoot();
    }
}