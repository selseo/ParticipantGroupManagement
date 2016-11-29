package com.example.katesudal.participantgroupmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.R;

import java.util.List;

/**
 * Created by katesuda.l on 29/11/2559.
 */

public class ItemSectionNameAdapter extends BaseAdapter implements View.OnClickListener{
    Context context;
    List<String> sectionNames;
    ViewHolder holder;

    public ItemSectionNameAdapter(Context context) {
        this.context = context;
    }

    public List<String> getSectionNames() {
        return sectionNames;
    }

    public void setSectionNames(List<String> sectionNames) {
        this.sectionNames = sectionNames;
    }

    @Override
    public int getCount() {
        return sectionNames.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_section_name, null);
            holder = new ItemSectionNameAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ItemSectionNameAdapter.ViewHolder) view.getTag();
        }
        holder.textViewSectionName.setText(sectionNames.get(i));
        ImageView iconDeleteSectionName = holder.iconDeleteSectionName;
        holder.iconDeleteSectionName.setTag(holder);
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
        public TextView textViewSectionName;
        public ImageView iconDeleteSectionName;

        public ViewHolder(View convertView) {
            textViewSectionName = (TextView) convertView.findViewById(R.id.textViewCreatedSessionName);
            iconDeleteSectionName = (ImageView) convertView.findViewById(R.id.iconDeleteSectionName);
        }
    }
}
