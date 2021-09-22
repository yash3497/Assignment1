package com.example.assignment1.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.assignment1.databinding.ActivityForgetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.email.getText().toString().isEmpty()){
                    binding.email.setError("Enter your email");
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(binding.email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Check your Email reset password link is sucessfully send",Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            },2000);
                        }else{
                            Toast.makeText(getApplicationContext(),task.getResult().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}