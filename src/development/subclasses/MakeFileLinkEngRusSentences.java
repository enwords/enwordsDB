package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MakeFileLinkEngRusSentences extends Zero {
    public void start(File langSentences, File secondLangSentences, File originalLinks, File linksOut) throws IOException {

        Set<String> engSet = fileToSet(langSentences, 0);
        Set<String> rusSet = fileToSet(secondLangSentences, 0);
        List<String> linksList = fileToList(originalLinks);

        List<String> engRusSentencesLinks = filter(engSet, rusSet, linksList);
        collectionToFile(engRusSentencesLinks, linksOut);
    }


    private List<String> filter(Set<String> engSet, Set<String> rusSet, List<String> linksList) {
        List<String> result = new ArrayList<>();
        String[] parsedString;
        for (String string : linksList) {
            parsedString = parseLineLight(string);

            if (engSet.contains(parsedString[0]) && rusSet.contains(parsedString[1])) {
                result.add(string);
            }
        }

        return result;
    }
}
