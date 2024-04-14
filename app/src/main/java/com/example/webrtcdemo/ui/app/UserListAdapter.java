package com.example.webrtcdemo.ui.app;

import static com.example.webrtcdemo.remote.FirebaseClient.online;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webrtcdemo.R;
import com.example.webrtcdemo.data.model.User;
import com.example.webrtcdemo.databinding.ListUserItemBinding;
import com.example.webrtcdemo.remote.FirebaseClient;
import com.example.webrtcdemo.repository.MainRepository;


public class UserListAdapter extends ListAdapter<User, UserListAdapter.UserViewHolder> {


    public UserListAdapter() {
        super(new UserDiffUtil());
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        com.example.webrtcdemo.databinding.ListUserItemBinding binding = ListUserItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        holder.bind(user);
    }

    public static class UserDiffUtil extends androidx.recyclerview.widget.DiffUtil.ItemCallback<User>{

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUsername().equals(newItem.getUsername());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);

        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private final ListUserItemBinding binding;
        public UserViewHolder(@NonNull ListUserItemBinding listUserItemBinding) {
            super(listUserItemBinding.getRoot());
            binding = listUserItemBinding;
        }
        public void bind(@NonNull User user)
        {
            String status = user.getStatus();
            String userName = user.getUsername();
            binding.userName.setText(userName);
            binding.userStatus.setText(status);

            if (status.equals(online))
            {
                binding.userImage.setImageResource(R.drawable.batman_icon);
                binding.imageVideoCall.setVisibility(View.VISIBLE);
            }
            else
            {
                binding.userImage.setImageResource(R.drawable.customer_service);
                binding.imageVideoCall.setVisibility(View.INVISIBLE);
            }

            binding.imageVideoCall.setOnClickListener(view -> {
                //start a call request here
                MainRepository.getInstance().sendCallRequest(userName, () -> {});
            });

        }
    }
}
