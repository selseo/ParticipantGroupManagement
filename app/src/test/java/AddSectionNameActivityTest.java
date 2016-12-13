import com.example.katesudal.participantgroupmanagement.Activity.AddSectionNameActivity;
import com.example.katesudal.participantgroupmanagement.Model.Section;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katesuda.l on 13/12/2559.
 */

public class AddSectionNameActivityTest {

    @Test
    public void setSectionNameList(){
        List<String> sectionNames = new ArrayList<>();
        sectionNames.add("Thai");
        sectionNames.add("English");

        List<Section> sections = new ArrayList<>();

        AddSectionNameActivity addSectionNameActivity = new AddSectionNameActivity();
        addSectionNameActivity.setSectionNameList(sections,sectionNames);

        List<Section> sectionsForCheck = new ArrayList<>();
        Section sectionThai = new Section();
        sectionThai.setSectionName("Thai");
        sectionsForCheck.add(sectionThai);
        Section sectionEnglish = new Section();
        sectionEnglish.setSectionName("English");
        sectionsForCheck.add(sectionEnglish);

        Assert.assertEquals(sections.get(0).getSectionName(),sectionsForCheck.get(0).getSectionName());
        Assert.assertEquals(sections.get(1).getSectionName(),sectionsForCheck.get(1).getSectionName());
    }
}
