package development.subclasses;

import development.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MakeFileLinkEngRusSentences extends Zero {
    public void start(File langSentences, File secondLangSentences, File originalLinks, File linksOut) throws IOException {

        Set<String> engSet = fileToSet(langSentences, 0);
        Set<String> rusSet = fileToSet(secondLangSentences, 0);

        List<String> engRusSentencesLinks = filter(engSet, rusSet, originalLinks);
        collectionToFile(engRusSentencesLinks, linksOut);
    }

    private List<String> filter(Set<String> engSet, Set<String> rusSet, File linksList) throws IOException {
        List<String> result = new ArrayList<>();
        String[] parsedString;
        String engSenId;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(linksList.getAbsolutePath()))) {

            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                parsedString = parseLineLight(line);
                engSenId = parsedString[0];

                if (engSet.contains(engSenId) && rusSet.contains(parsedString[1])) {
                    result.add(line);
                    engSet.remove(engSenId);
                    Main.allLinks.add(line);
                }
            }
        }
        return result;
    }
}
