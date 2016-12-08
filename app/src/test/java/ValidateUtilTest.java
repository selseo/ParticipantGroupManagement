import com.example.katesudal.participantgroupmanagement.Util.ValidateUtil;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katesuda.l on 08/12/2559.
 */

public class ValidateUtilTest {

    @Test
    public void duplicateSectionName()throws Exception{
        List<String> names = new ArrayList<>();
        names.add("Thai");

        Assert.assertTrue(ValidateUtil.isDuplicateSectionName("Thai",names));
    }

    @Test
    public void notDuplicateSectionName() throws  Exception{
        List<String> names = new ArrayList<>();
        names.add("Thai");

        Assert.assertFalse(ValidateUtil.isDuplicateSectionName("English",names));
    }

    @Test
    public void invalidName() throws  Exception{
        Assert.assertTrue(ValidateUtil.isInvalidName("$Thai"));
    }

    @Test
    public void validName() throws  Exception{
        Assert.assertFalse(ValidateUtil.isInvalidName("1-Thai"));
    }

    @Test
    public void invalidParticipantName() throws  Exception{
        Assert.assertTrue(ValidateUtil.isInvalidParticipantName("1Bobby"));
        Assert.assertTrue(ValidateUtil.isInvalidParticipantName("Bobby-1"));
    }

    @Test
    public void validParticipantName() throws  Exception{
        Assert.assertFalse(ValidateUtil.isInvalidParticipantName("Bobby1"));
    }
}
