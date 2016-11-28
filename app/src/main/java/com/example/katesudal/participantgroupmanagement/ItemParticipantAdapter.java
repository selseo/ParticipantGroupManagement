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
 * Created by katesuda.l on 28/11/2559.
 */

public class ItemParticipantAdapter extends BaseAdapter implements View.OnClickListener {

    Context context;
    List<Participant> participants;
    private ItemParticipantListener itemParticipantListener;
    private int positionParticipant;

    public ItemParticipantAdapter(List<Participant> participants, Context context) {
        this.participants = participants;
        this.context = context;
        this.itemParticipantListener=itemParticipantListener;
    }

    public ItemParticipantAdapter() {
    }

    @Override
    public int getCount() {
        return participants.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        positionParticipant = i;
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_participant, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textViewParticipantName.setText(participants.get(i).getParticipantName());
        viewHolder.textViewParticipantSex.setText(participants.get(i).getParticipantSex());
        viewHolder.textViewParticipantType.setText(participants.get(i).getParticipantType());
        ImageView iconDeleteParticipant = viewHolder.iconDeleteParticipant;
        iconDeleteParticipant.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (view.getId() == holder.iconDeleteParticipant.getId()) {
            itemParticipantListener.deleteParticipantById(participants.get(positionParticipant).getParticipantName());
        }

    }

    private class ViewHolder {
        private TextView textViewParticipantName;
        private TextView textViewParticipantSex;
        private TextView textViewParticipantType;
        private ImageView iconDeleteParticipant;

        public ViewHolder(View convertView) {
            textViewParticipantName = (TextView) convertView.findViewById(R.id.textViewParticipantName);
            textViewParticipantSex = (TextView) convertView.findViewById(R.id.textViewParticipantSex);
            textViewParticipantType = (TextView) convertView.findViewById(R.id.textViewParticipantType);
            iconDeleteParticipant = (ImageView) convertView.findViewById(R.id.iconDeleteParticipant);
        }
    }

    public interface ItemParticipantListener{
        void deleteParticipantById(String participantId);
    }
}
