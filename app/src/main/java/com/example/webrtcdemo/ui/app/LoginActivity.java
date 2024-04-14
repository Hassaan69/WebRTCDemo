package com.example.webrtcdemo.ui.app;


import android.content.Intent;
import android.os.Bundle;


import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;


import android.widget.Toast;

import com.example.webrtcdemo.databinding.ActivityLoginBinding;
import com.example.webrtcdemo.repository.MainRepository;
import com.permissionx.guolindev.PermissionX;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private MainRepository mainRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mainRepository.getCurrentUsername() != null && !mainRepository.getCurrentUsername().isEmpty()){
            mainRepository.logout(() -> {
                binding.username.setText("");
            });
        }

    }

    private void init() {
        mainRepository = MainRepository.getInstance();
        binding.enter.setOnClickListener(v -> PermissionX.init(this)
                .permissions(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        //login to firebase here
                        String userName = binding.username.getText().toString();
                        if (!userName.isEmpty())
                            mainRepository.login(
                                binding.username.getText().toString(), getApplicationContext(), () -> {
                                    //if success then we want to move to call activity
                                    startActivity(new Intent(LoginActivity.this, CallActivity.class));
                                }
                        );
                        else
                            Toast.makeText(this, "Please Enter A Valid User Name", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        Toast.makeText(this, "Permissions are must for app to work", Toast.LENGTH_SHORT).show();
                    }
                }));
    }


}