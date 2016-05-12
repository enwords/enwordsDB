package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Third extends Zero {

    static List<String> listSentences;
    static List<String> listSentencesWithAudio;
    static Map<String, String> map;

    public void start(List<String> list, File output) throws IOException {
        map = new LinkedHashMap<>();
        listSentences = new ArrayList<>();
        listSentencesWithAudio = new ArrayList<>();

        splitList(list);
        listToMap(listSentences, map);
        listToMap(listSentencesWithAudio, map);
        listToFile(mapToList(map), output);
    }


    private List<String> mapToList(Map<String, String> map) {
        List<String> res = new ArrayList<>();

        for (Map.Entry<String, String> pair : map.entrySet()) {
            res.add(pair.getValue());
        }
        return res;
    }

    private void splitList(List<String> list) throws IOException {
        for (String sentence : list) {
            List<String> splitSen = parseLine(sentence);
            if (splitSen.size()>3 && splitSen.get(3).equals("true")) {
                listSentencesWithAudio.add(sentence);
            }
            listSentences.add(sentence);
        }
    }

    private void listToMap(List<String> list, Map<String, String> map) throws IOException {
        for (String sentence : list) {
            List<String> splitSen = parseLine(sentence);
            String key = removePunctuationAndDigits(splitSen.get(2));

            if (key.length() < 200) map.put(key, sentence);
        }
    }
}
