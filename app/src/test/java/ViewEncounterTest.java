import com.example.katesudal.participantgroupmanagement.Activity.ViewEncounterActivity;
import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by katesuda.l on 08/12/2559.
 */

public class ViewEncounterTest {

    @Test
    public void countEncounterInSpecialGroup(){
        List<SpecialGroup> specialGroups = new ArrayList<>();
        SpecialGroup specialGroup = new SpecialGroup();
        specialGroup.setSpecialGroupID(1);

        Participant participant1 = new Participant();
        participant1.setParticipantID(1);
        Participant participant2 = new Participant();
        participant2.setParticipantID(2);


        specialGroup.setParticipantIDs(new RealmList<Participant>());
        specialGroup.getParticipantIDs().add(participant1);
        specialGroup.getParticipantIDs().add(participant2);

        specialGroups.add(specialGroup);

        PairEncounter pairEncounter = new PairEncounter(0,participant1,participant2);

        ViewEncounterActivity viewEncounterActivity = new ViewEncounterActivity();
        viewEncounterActivity.pairEncounters = new ArrayList<>();
        viewEncounterActivity.pairEncounters.add(pairEncounter);

        Assert.assertEquals(1,viewEncounterActivity.countEncounterInSpecialGroup(specialGroups,0,0));
    }
}
