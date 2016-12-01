package com.example.katesudal.participantgroupmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.List;

/**
 * Created by katesuda.l on 01/12/2559.
 */

public class ItemProjectAdapter extends BaseAdapter implements View.OnClickListener  {

    Context context;
    List<Project> projects;
    private ItemProjectListener itemProjectListener;
    private View view;
    private int projectParticipant;

    public ItemProjectAdapter(){
    }

    public ItemProjectAdapter(Context context, List<Project> projects, ItemProjectListener itemProjectListener, View view) {
        this.context = context;
        this.projects = projects;
        this.itemProjectListener = itemProjectListener;
        this.view = view;
    }

    @Override
    public int getCount() {
        return projects.size();
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
        projectParticipant = i;
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_project, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textViewProjectNameForEdit.setText(projects.get(i).getProjectName());
        ImageView iconDeleteProject = viewHolder.iconDeleteProject;
        ImageView iconEditProject = viewHolder.iconEditProject;
        viewHolder.iconDeleteProject.setTag(viewHolder);
        iconDeleteProject.setOnClickListener(this);
        viewHolder.iconEditProject.setTag(viewHolder);
        iconEditProject.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (view.getId() == holder.iconDeleteProject.getId()) {
            itemProjectListener.deleteProjectById(projects, view);
        }
        if (view.getId() == holder.iconEditProject.getId()) {
            itemProjectListener.editProjectById(projects, view);
        }
    }

    private class ViewHolder {
        private TextView textViewProjectNameForEdit;
        private ImageView iconDeleteProject;
        private ImageView iconEditProject;

        public ViewHolder(View convertView) {
            textViewProjectNameForEdit = (TextView) convertView.findViewById(R.id.textViewProjectNameForEdit);
            iconDeleteProject = (ImageView) convertView.findViewById(R.id.iconDeleteProject);
            iconEditProject = (ImageView) convertView.findViewById(R.id.iconEditProject);
        }
    }

    public interface ItemProjectListener{
        void deleteProjectById(List<Project> projects, View view);
        void editProjectById(List<Project> projects, View view);
    }
}
