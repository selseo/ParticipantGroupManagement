package com.example.katesudal.participantgroupmanagement;

import android.content.Context;
import android.util.AttributeSet;

import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;

/**
 * Created by katesuda.l on 01/12/2559.
 */

public class SortablePairEncounterTableView extends SortableTableView<PairEncounter> {
    public SortablePairEncounterTableView(final Context context) {
        this(context, null);
    }

    public SortablePairEncounterTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public SortablePairEncounterTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);

        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context, "A", "B", "Times");
        setHeaderAdapter(simpleTableHeaderAdapter);
        setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        final TableColumnWeightModel tableColumnWeightModel = new TableColumnWeightModel(3);
        tableColumnWeightModel.setColumnWeight(0, 3);
        tableColumnWeightModel.setColumnWeight(1, 3);
        tableColumnWeightModel.setColumnWeight(2, 2);
        setColumnModel(tableColumnWeightModel);

        setColumnComparator(0, PairEncounterComparator.getPairEncounterParticipantAComparator());
        setColumnComparator(1, PairEncounterComparator.getPairEncounterParticipantBComparator());
        setColumnComparator(2, PairEncounterComparator.getPairEncounterTimesComparator());
    }
}
