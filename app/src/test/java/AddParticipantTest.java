import com.example.katesudal.participantgroupmanagement.Activity.AddParticipantActivity;

import org.junit.Assert;

import org.junit.Test;

/**
 * Created by katesuda.l on 09/12/2559.
 */

public class AddParticipantTest {

    @Test
    public void setParticipantGenderToMale() throws Exception{
        AddParticipantActivity addParticipantActivity = new AddParticipantActivity();
        String gender = addParticipantActivity.setParticipantGender(true,false);
        Assert.assertEquals("Male",gender);
    }

    @Test
    public void setParticipantGenderToFemale() throws Exception{
        AddParticipantActivity addParticipantActivity = new AddParticipantActivity();
        String gender = addParticipantActivity.setParticipantGender(false,true);
        Assert.assertEquals("Female",gender);
    }

    @Test
    public void setParticipantGenderToEmpty() throws Exception{
        AddParticipantActivity addParticipantActivity = new AddParticipantActivity();
        String gender = addParticipantActivity.setParticipantGender(false,false);
        Assert.assertEquals("",gender);
    }

    @Test
    public void setParticipantTypeToStaff() throws Exception{
        AddParticipantActivity addParticipantActivity = new AddParticipantActivity();
        String type = addParticipantActivity.setParticipantType(true,false);
        Assert.assertEquals("Staff",type);
    }

    @Test
    public void setParticipantTypeToParticipant() throws Exception{
        AddParticipantActivity addParticipantActivity = new AddParticipantActivity();
        String type = addParticipantActivity.setParticipantType(false,true);
        Assert.assertEquals("Participant",type);
    }

    @Test
    public void setParticipantTypeToEmpty() throws Exception{
        AddParticipantActivity addParticipantActivity = new AddParticipantActivity();
        String type = addParticipantActivity.setParticipantType(false,false);
        Assert.assertEquals("",type);
    }
}