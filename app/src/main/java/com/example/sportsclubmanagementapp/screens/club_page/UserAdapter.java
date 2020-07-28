package com.example.sportsclubmanagementapp.screens.club_page;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.screens.eventdetails.EventDetailsActivity;
import com.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public static int MEMBER_BAR_WITHOUT_CHECK_BOX = 1;
    public static int MEMBER_BAR_WITH_CHECK_BOX = 2;

    EventDetailsActivity activity;
    int layoutType;
    private List<Drawable> avatars; //for TESTS
    private List<User> users;
    private List<User> usersSelected = new ArrayList<>();
    private Context context;

    public UserAdapter(List<User> users, Context context, int layoutType) {
        this.users = users;
        this.context = context;
        this.layoutType = layoutType;
    }

    public UserAdapter(List<User> users, Context context, int layoutType, EventDetailsActivity activity) {
        this.users = users;
        this.context = context;
        this.layoutType = layoutType;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);

        if (layoutType == MEMBER_BAR_WITHOUT_CHECK_BOX)
            view.findViewById(R.id.checkBox).setVisibility(View.GONE);

        avatars = Utils.getAvatars(getContext());

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position), avatars);

        //listener for every participant check box
        holder.checkbox.setOnClickListener(view -> {
            int pos = holder.getAdapterPosition();
            User selectedUser = users.get(pos); //get the selected user

            if (usersSelected.indexOf(selectedUser) != -1) {
                //if the user is already in list, the check box is unchecked and the user is removed from the list
                usersSelected.remove(selectedUser);
            } else {
                //the user is selected and added in the list
                usersSelected.add(selectedUser);
            }
            activity.setParticipants(); //send the selected participants to activity (to set their dates in chart)
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public Context getContext() {
        return this.context;
    }

    public List<User> getSelectedUsers() {
        return usersSelected;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView avatar;
        private TextView user_name;
        private CheckBox checkbox;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            user_name = itemView.findViewById(R.id.name);
            checkbox = itemView.findViewById(R.id.checkBox);
        }

        public void bind(User user, List<Drawable> avatars) {
            avatar.setImageDrawable(avatars.get(new Random().nextInt(5)));
            user_name.setText(user.getFirst_name() + " " + user.getLast_name());
        }
    }
}
