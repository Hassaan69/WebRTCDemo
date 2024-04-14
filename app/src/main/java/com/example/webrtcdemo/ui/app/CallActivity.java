package com.example.webrtcdemo.ui.app;

import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.webrtcdemo.R;
import com.example.webrtcdemo.data.model.DataModelType;
import com.example.webrtcdemo.databinding.ActivityCallBinding;
import com.example.webrtcdemo.repository.MainRepository;

public class CallActivity extends AppCompatActivity implements MainRepository.Listener {

    private ActivityCallBinding binding;
    private MainRepository repository;
    private Boolean isCameraMuted = false;
    private Boolean isMicrophoneMuted = false;
    private UserListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.callLayout.getVisibility() == View.VISIBLE)
                    repository.endCall();
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        repository.subscribeForUserStatusEvent(userList -> {
            adapter.submitList(userList);
        });

    }

    private void init(){
        repository = MainRepository.getInstance();
        adapter = new UserListAdapter();
        binding.userRv.setAdapter(adapter);


        repository.initLocalView(binding.localView);
        repository.initRemoteView(binding.remoteView);
        repository.listener = this;

        repository.subscribeForLatestEvent(data->{
            if (data.getType()== DataModelType.StartCall){
                runOnUiThread(()->{
                    binding.incomingNameTV.setText(data.getSender() + " is Calling you");
                    binding.incomingCallLayout.setVisibility(View.VISIBLE);
                    binding.acceptButton.setOnClickListener(v->{
                        //star the call here
                        repository.startCall(data.getSender());
                        binding.incomingCallLayout.setVisibility(View.GONE);
                        binding.usersLayout.setVisibility(View.GONE);
                        binding.callLayout.setVisibility(View.VISIBLE);
                    });
                    binding.rejectButton.setOnClickListener(v-> binding.incomingCallLayout.setVisibility(View.GONE));
                });
            }
        });

        binding.switchCameraButton.setOnClickListener(v-> repository.switchCamera());

        binding.micButton.setOnClickListener(v->{
            repository.toggleAudio(isMicrophoneMuted);
            isMicrophoneMuted=!isMicrophoneMuted;
            if (isMicrophoneMuted){
                binding.micButton.setImageResource(R.drawable.ic_baseline_mic_off_24);
            }else {
                binding.micButton.setImageResource(R.drawable.ic_baseline_mic_24);
            }
        });

        binding.videoButton.setOnClickListener(v->{
            repository.toggleVideo(isCameraMuted);
            isCameraMuted=!isCameraMuted;
            if (isCameraMuted){
                binding.videoButton.setImageResource(R.drawable.ic_baseline_videocam_off_24);
            }else {
                binding.videoButton.setImageResource(R.drawable.ic_baseline_videocam_24);
            }
        });

        binding.endCallButton.setOnClickListener(v->{
            repository.endCall();
            finish();
        });
    }

    @Override
    public void webrtcConnected() {
        runOnUiThread(()->{
            binding.incomingCallLayout.setVisibility(View.GONE);
            binding.usersLayout.setVisibility(View.GONE);
            binding.callLayout.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void webrtcClosed() {
        runOnUiThread(this::finish);
    }

}