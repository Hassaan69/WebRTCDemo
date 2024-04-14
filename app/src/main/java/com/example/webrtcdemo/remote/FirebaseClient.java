package com.example.webrtcdemo.remote;

import androidx.annotation.NonNull;


import com.example.webrtcdemo.Callbacks.ErrorCallBack;
import com.example.webrtcdemo.Callbacks.NewEventCallBack;
import com.example.webrtcdemo.Callbacks.SuccessCallBack;
import com.example.webrtcdemo.Callbacks.UsersFetchCallback;
import com.example.webrtcdemo.data.model.DataModel;
import com.example.webrtcdemo.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirebaseClient {

    private final Gson gson = new Gson();
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private String currentUsername;
    private static final String LATEST_EVENT_FIELD_NAME = "latest_event";
    private static final String STATUS_FIELD_NAME = "status";
    public static final String online = "online";
    public static final String offline = "offline";

    public void login(String username, SuccessCallBack callBack){
        dbRef.child(username).child(STATUS_FIELD_NAME).setValue(online).addOnCompleteListener(task -> {
            currentUsername = username;
            callBack.onSuccess();
        });
    }

    public void getAllUsers(UsersFetchCallback callback)
    {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> userList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String userName = dataSnapshot.getKey();
                    if (userName != null && !userName.equals(currentUsername))
                    {
                        String status = dataSnapshot.child(STATUS_FIELD_NAME).getValue(String.class);
                        User user = new User(userName,status);
                        userList.add(user);
                    }
                }
                callback.onFetchSuccess(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    callback.onFetchSuccess(new ArrayList<>());
            }

        });
    }

    public void sendMessageToOtherUser(DataModel dataModel, ErrorCallBack errorCallBack){
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(dataModel.getTarget()).exists()){
                    //send the signal to other user
                    dbRef.child(dataModel.getTarget()).child(LATEST_EVENT_FIELD_NAME)
                            .setValue(gson.toJson(dataModel));

                }else {
                    errorCallBack.onError();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorCallBack.onError();
            }
        });
    }

    public void observeIncomingLatestEvent(NewEventCallBack callBack){
        dbRef.child(currentUsername).child(LATEST_EVENT_FIELD_NAME).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try{
                            String data= Objects.requireNonNull(snapshot.getValue()).toString();
                            DataModel dataModel = gson.fromJson(data,DataModel.class);
                            callBack.onNewEventReceived(dataModel);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


    }

    public void logout(SuccessCallBack successCallBack) {
        dbRef.child(currentUsername).child(STATUS_FIELD_NAME).setValue(offline).addOnCompleteListener(task -> {
                successCallBack.onSuccess();
        });
    }
}
