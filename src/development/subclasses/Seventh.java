package development.subclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Seventh extends Zero {
    public void start(File engSentencesWithAudioOutWithoutRepeat, File rusSentencesWithAudioOut, File links, File engrusSentences) throws IOException {
        List<String> engList = fileToList(engSentencesWithAudioOutWithoutRepeat);
        List<String> rusList = fileToList(rusSentencesWithAudioOut);
        List<String> linksList = linksToList(links);

        List<String> filteredRusList = filter(rusList, linksList);

        List<String> newList = new LinkedList<>(engList);
        newList.addAll(filteredRusList);
        listToFile(newList, engrusSentences);
    }

    public List<String> filter(List<String> rusList, List<String> linksList) throws IOException {
        List<String> result = new LinkedList<>();


        for (String string : rusList) {
            List<String> list = parseLine(string);

            if (linksList.contains(list.get(0))) {
                result.add(string);
            }
        }
        return result;
    }


    public List<String> linksToList(File file) throws IOException {
        List<String> result = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                List<String> list = parseLine(line);
                result.addAll(list);
            }
        }
        return result;
    }
}
