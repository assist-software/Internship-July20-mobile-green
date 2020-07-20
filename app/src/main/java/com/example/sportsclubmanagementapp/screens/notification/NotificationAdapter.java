package com.example.sportsclubmanagementapp.screens.notification;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    private Context context;

    public NotificationAdapter(List<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView notification_text;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            notification_text = itemView.findViewById(R.id.notification_text);
        }

        public void bind(Notification notify) {
            time.setText(notify.getTime());
            Spanned notify_text = Html.fromHtml(
                    "<font color=#848484>" + notify.getRole() + " " +
                            "</font><font color=#FFFFFF><b>" + notify.getName() + " " +
                            "</b></font><font color=#848484>" + notify.getAction() + " " +
                            "</font><font color=#44DABD><b>" + notify.getActionOn() + "</b></font><font color=#848484>.</font>");
            notification_text.setText(notify_text);
        }
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
            holder.bind(notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return this.notifications.size();
    }

    public Context getContext(){
        return this.context;
    }
}
