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
import com.example.assignment1.databinding.FragmentLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    float v=0;
    //
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseFirestore database;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false);
       //login Activity
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");

        binding.email.setTranslationX(800);
        binding.password.setTranslationX(800);
        binding.forgetpass.setTranslationX(800);
        binding.login.setTranslationX(800);

        binding.email.setAlpha(v);
        binding.password.setAlpha(v);
        binding.forgetpass.setAlpha(v);
        binding.login.setAlpha(v);

        binding.email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        binding.password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.forgetpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        binding.login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(),gso);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.email.getText().toString().isEmpty()){
                    binding.email.setError("Enter your email");
                    return;
                }
                if (binding.password.getText().toString().isEmpty()){
                    binding.password.setError("Enter your password");
                    return;
                }
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(binding.email.getText().toString(),binding.password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
                                    Users user=new Users();
                                    user.setEmail(binding.email.getText().toString());
                                    user.setPassword(binding.password.getText().toString());
                                    user.setUserId(users.getUid());
                                    database.collection("User")
                                            .document(user.getUserId())
                                            .set(user, SetOptions.merge());

                                    Intent intent=new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}