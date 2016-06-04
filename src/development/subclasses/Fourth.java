package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fourth extends Zero {
    public void start(File langSentences, File secondLangSentences, File originalLinks, File links) throws IOException {

        Map<String, String> engMap = listToMap(fileToList(langSentences));
        Map<String, String> rusMap = listToMap(fileToList(secondLangSentences));
        List<String> list = fileToList(originalLinks);

        List<String> result = new ArrayList<>();

        for (String string : list) {
            String[] parsedString = parseLineLight(string);

            if (engMap.containsKey(parsedString[0]) && rusMap.containsKey(parsedString[1])) {
                result.add(string);
            }
        }

        listToFile(result, links);
    }
}
