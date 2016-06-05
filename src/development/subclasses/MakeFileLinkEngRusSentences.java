package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MakeFileLinkEngRusSentences extends Zero {
    public void start(File langSentences, File secondLangSentences, File originalLinks, File linksOut) throws IOException {

        Set<String> engSet = fileToSet(langSentences, 0);
        Set<String> rusSet = fileToSet(secondLangSentences, 0);
        List<String> linksList = fileToList(originalLinks);

        List<String> engRusSentencesLinks = method(engSet, rusSet, linksList);

        listToFile(engRusSentencesLinks, linksOut);
    }


    private List<String> method(Set<String> engMap, Set<String> rusMap, List<String> linksList) {
        List<String> result = new ArrayList<>();

        for (String string : linksList) {
            String[] parsedString = parseLineLight(string);

            if (engMap.contains(parsedString[0]) && rusMap.contains(parsedString[1])) {
                result.add(string);
            }
        }

        return result;
    }
}
