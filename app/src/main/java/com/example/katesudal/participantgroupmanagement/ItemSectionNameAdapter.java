package com.example.katesudal.participantgroupmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by katesuda.l on 29/11/2559.
 */

public class ItemSectionNameAdapter extends BaseAdapter implements View.OnClickListener{
    Context context;
    List<String> sectionNames;

    public ItemSectionNameAdapter(Context context, List<String> sectionNames) {
        this.context = context;
        this.sectionNames = sectionNames;
    }

    @Override
    public int getCount() {
        return sectionNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemSectionNameAdapter.ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_participant, null);
            viewHolder = new ItemSectionNameAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ItemSectionNameAdapter.ViewHolder) view.getTag();
        }
        viewHolder.textViewSectionName.setText(sectionNames.get(i));
        ImageView iconDeleteSectionName = viewHolder.iconDeleteSectionName;
        viewHolder.iconDeleteSectionName.setTag(viewHolder);
//        iconDeleteSectionName.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        ItemSectionNameAdapter.ViewHolder holder = (ItemSectionNameAdapter.ViewHolder) view.getTag();
        if (view.getId() == holder.iconDeleteSectionName.getId()) {
//            sectionNames.remove();
//            itemParticipantListener.deleteParticipantById(participants, view);
        }

    }

    private class ViewHolder {
        private TextView textViewSectionName;
        private ImageView iconDeleteSectionName;

        public ViewHolder(View convertView) {
            textViewSectionName = (TextView) convertView.findViewById(R.id.textViewCreatedSessionName);
            iconDeleteSectionName = (ImageView) convertView.findViewById(R.id.iconDeleteSectionName);
        }
    }
}
