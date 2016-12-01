package com.example.katesudal.participantgroupmanagement;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

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
//        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, 0x0000));
        setHeaderAdapter(simpleTableHeaderAdapter);

//        final int rowColorEven = ContextCompat.getColor(context, 0x619aad);
//        final int rowColorOdd = ContextCompat.getColor(context, 0x619aad);
//        setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
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
