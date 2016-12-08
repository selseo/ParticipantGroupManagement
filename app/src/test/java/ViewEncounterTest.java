import android.util.Log;

import com.example.katesudal.participantgroupmanagement.Activity.ViewEncounterActivity;
import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Section;
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

    @Test
    public void countEncounterInSection(){
        List<Section> sections = new ArrayList<>();
        Section section = new Section();
        section.setSectionID(1);

        Participant participant1 = new Participant();
        participant1.setParticipantID(1);
        Participant participant2 = new Participant();
        participant2.setParticipantID(2);

        section.setParticipantIDs(new RealmList<Participant>());
        section.getParticipantIDs().add(participant1);
        section.getParticipantIDs().add(participant2);

        sections.add(section);

        PairEncounter pairEncounter = new PairEncounter(0,participant1,participant2);

        ViewEncounterActivity viewEncounterActivity = new ViewEncounterActivity();
        viewEncounterActivity.pairEncounters = new ArrayList<>();
        viewEncounterActivity.pairEncounters.add(pairEncounter);

        Assert.assertEquals(1,viewEncounterActivity.countEncounterInSection(sections,0,0));
    }

    @Test
    public void addPairParticipant(){
        List<Participant> participants = new ArrayList<>();
        Participant participant1 = new Participant();
        participant1.setParticipantID(1);
        Participant participant2 = new Participant();
        participant2.setParticipantID(2);
        participants.add(participant1);
        participants.add(participant2);

        ViewEncounterActivity viewEncounterActivity = new ViewEncounterActivity();
        viewEncounterActivity.pairEncounters = new ArrayList<>();
        int startIndex=0;
        viewEncounterActivity.addPairParticipant(participants, startIndex);

        int encounterStart = 0;
        PairEncounter pairEncounter = new PairEncounter(encounterStart,participant1,participant2);

        Assert.assertEquals(pairEncounter.getParticipantA().getParticipantID()
                ,viewEncounterActivity.pairEncounters.get(startIndex).getParticipantA().getParticipantID());
        Assert.assertEquals(pairEncounter.getParticipantB().getParticipantID()
                ,viewEncounterActivity.pairEncounters.get(startIndex).getParticipantB().getParticipantID());
        Assert.assertEquals(encounterStart
                ,viewEncounterActivity.pairEncounters.get(startIndex).getEncounterTimes());
    }
}
