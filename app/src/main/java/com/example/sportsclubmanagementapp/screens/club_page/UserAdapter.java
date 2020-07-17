package com.example.sportsclubmanagementapp.screens.club_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private Context context;
    int layout;

    public UserAdapter(List<User> users, Context context, int layout) {
        this.users = users;
        this.context = context;
        this.layout = layout;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView user_name;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            user_name = itemView.findViewById(R.id.name);
        }

        public void bind(User user) {
            //avatar.setImageIcon();
            user_name.setText(user.getName());
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public Context getContext(){
        return this.context;
    }
}
