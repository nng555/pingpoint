package com.pingpoint.pingpoint;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendAdapter extends ArrayAdapter<PingFriends> {
    private Context mContext;
    private List<PingFriends> mGroups;

    public FriendAdapter(Context context, List<PingFriends> objects) {
        super(context, R.layout.friend_row_item, objects);
        this.mContext = context;
        this.mGroups = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.friend_row_item, null);
        }

        PingFriends friends = mGroups.get(position);

        TextView nameView = (TextView) convertView.findViewById(R.id.friend_name);

        nameView.setText(friends.getFriend());
        nameView.setTextColor(Color.WHITE);
        nameView.setPadding(10,0,0,0);
        return convertView;
    }
}