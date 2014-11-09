package com.pingpoint.pingpoint;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GroupAdapter extends ArrayAdapter<PingGroup> {
    private Context mContext;
    private List<PingGroup> mGroups;

    public GroupAdapter(Context context, List<PingGroup> objects) {
        super(context, R.layout.group_row_item, objects);
        this.mContext = context;
        this.mGroups = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.group_row_item, null);
        }

        PingGroup group = mGroups.get(position);

        TextView nameView = (TextView) convertView.findViewById(R.id.group_name);

        nameView.setText(group.getName());
        nameView.setTextColor(Color.WHITE);
        nameView.setPadding(20,0,0,0);
        nameView.setTextSize(20);

        return convertView;
    }
}