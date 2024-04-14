package com.example.webrtcdemo.Callbacks;

import com.example.webrtcdemo.data.model.User;

import java.util.List;

public interface UsersFetchCallback {
    void onFetchSuccess(List<User> userList);
}
