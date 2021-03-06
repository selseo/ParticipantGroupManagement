package com.example.katesudal.participantgroupmanagement.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;
import com.example.katesudal.participantgroupmanagement.R;

import java.text.NumberFormat;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter;

/**
 * Created by katesuda.l on 01/12/2559.
 */

public class PairEncounterTableAdapter extends LongPressAwareTableDataAdapter<PairEncounter> {
    private static final int TEXT_SIZE = 18;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();


    public PairEncounterTableAdapter(final Context context, final List<PairEncounter> data, final TableView<PairEncounter> tableView) {
        super(context, data, tableView);
    }

    @Override
    public View getDefaultCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final PairEncounter pairEncounter = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderParticipantA(pairEncounter);
                break;
            case 1:
                renderedView = renderParticipantB(pairEncounter);
                break;
            case 2:
                renderedView = renderPairEncounterTimes(pairEncounter);
                break;
        }

        return renderedView;
    }

    @Override
    public View getLongPressCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        PairEncounter pairEncounter = getRowData(rowIndex);
        View renderedView;
        renderedView = getDefaultCellView(rowIndex, columnIndex, parentView);
        return renderedView;
    }


    private View renderPairEncounterTimes(PairEncounter pairEncounter) {
        final String timesString = PRICE_FORMATTER.format(pairEncounter.getEncounterTimes());

        final TextView textView = new TextView(getContext());
        textView.setText(timesString);
        textView.setTextColor(getResources().getColor(R.color.colorNormalBrown));
        textView.setPadding(30,15,30,15);
        textView.setTextSize(TEXT_SIZE);

        if (pairEncounter.getEncounterTimes() < 1) {
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (pairEncounter.getEncounterTimes() > 5) {
            textView.setTextColor(getResources().getColor(R.color.colorNormalRed));
        }

        return textView;
    }

    private View renderParticipantA(PairEncounter pairEncounter) {
        return renderString(pairEncounter.getParticipantA().getParticipantName());
    }

    private View renderParticipantB(PairEncounter pairEncounter) {
        return renderString(pairEncounter.getParticipantB().getParticipantName());
    }

    private View renderString(String value) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setTextColor(getResources().getColor(R.color.colorNormalBrown));
        textView.setPadding(30,15,30,15);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }

}
