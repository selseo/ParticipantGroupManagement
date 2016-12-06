package com.example.katesudal.participantgroupmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Activity.ManageSpecialGroup;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.List;

/**
 * Created by katesuda.l on 06/12/2559.
 */

public class ItemSpecialGroupAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    private ListView listViewSpecialGroup;
    List<SpecialGroup> specialGroups;
    private ItemSpecialGroupListener itemSpecialGroupListener;

    public ItemSpecialGroupAdapter(){}

    public ItemSpecialGroupAdapter(Context context, List<SpecialGroup> specialGroups,ItemSpecialGroupListener itemSpecialGroupListener, ListView listViewSpecialGroup) {
        this.itemSpecialGroupListener = itemSpecialGroupListener;
        this.specialGroups = specialGroups;
        this.context = context;
        this.listViewSpecialGroup = listViewSpecialGroup;
    }


    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (view.getId() == holder.iconDeleteSpecialGroup.getId()) {
            itemSpecialGroupListener.deleteSpecialGroupById(specialGroups, view);
        }
        if (view.getId() == holder.iconEditSpecialGroup.getId()) {
            itemSpecialGroupListener.editSpecialGroupById(specialGroups, view);
        }
    }

    @Override
    public int getCount() {
        return specialGroups.size();
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
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_project, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textViewSpecialGroupName.setText(specialGroups.get(i).getSpecialGroupName());
        ImageView iconDeleteSpecialGroup = viewHolder.iconDeleteSpecialGroup;
        ImageView iconEditSpecialGroup = viewHolder.iconEditSpecialGroup;
        viewHolder.iconDeleteSpecialGroup.setTag(viewHolder);
        iconDeleteSpecialGroup.setOnClickListener(this);
        viewHolder.iconEditSpecialGroup.setTag(viewHolder);
        iconEditSpecialGroup.setOnClickListener(this);
        return view;
    }

    private class ViewHolder {
        private TextView textViewSpecialGroupName;
        private ImageView iconDeleteSpecialGroup;
        private ImageView iconEditSpecialGroup;

        public ViewHolder(View convertView) {
            textViewSpecialGroupName = (TextView) convertView.findViewById(R.id.textViewProjectNameForEdit);
            iconDeleteSpecialGroup = (ImageView) convertView.findViewById(R.id.iconDeleteProject);
            iconEditSpecialGroup = (ImageView) convertView.findViewById(R.id.iconEditProject);
        }
    }

    public interface ItemSpecialGroupListener{
        void deleteSpecialGroupById(List<SpecialGroup> projects, View view);
        void editSpecialGroupById(List<SpecialGroup> projects, View view);
    }
}
