package development.subclasses;

import development.Main;

import java.io.*;
import java.util.*;

public class FilterOfSentencesFile extends Zero {
    public void start(File engSentencesWithAudioOutWithoutRepeat, File rusSentencesWithAudioOut, File wordSentenceLinks,
                      File engrusLinks, File engSentences, File rusSentences) throws IOException {
        List<String> engList = fileToList(engSentencesWithAudioOutWithoutRepeat);
        List<String> rusList = fileToList(rusSentencesWithAudioOut);
        Map<Integer, String> mapEngSen = listToMapWithFilter(engList);
        Map<Integer, String> mapRusSen = listToMapWithFilter(rusList);
        Set<String> linksSet = linksToSet(wordSentenceLinks);
        List<String> engrusLinksList1 = fileToList(engrusLinks);//
        Set<Integer> engrusLinksList = engrusLinksListToList(engrusLinksList1, linksSet);//
        List<String> listE = mapToList(mapEngSen, engrusLinksList);
        List<String> listR = mapToList(mapRusSen, engrusLinksList);
        collectionToFile(listE, engSentences);
        collectionToFile(listR, rusSentences);

    }


    private List<String> mapToList(Map<Integer, String> mapEngSen, Set<Integer> engrusLinksList) {
        List<String> result = new ArrayList<>();

        for (Map.Entry<Integer, String> pair : mapEngSen.entrySet()) {
            int key = pair.getKey();
            String value = pair.getValue();

            if (engrusLinksList.contains(key)) {
                result.add(value);
            }
        }
        return result;
    }


    private Set<Integer> engrusLinksListToList(List<String> engrusLinks, Set<String> linksSet) {
        Set<Integer> result = new HashSet<>();
        String[] list;

        for (String line : engrusLinks) {
            list = parseLineLight(line);

            if (linksSet.contains(list[0])) {

                result.add(Integer.parseInt(list[0]));
                result.add(Integer.parseInt(list[1]));
            }
        }
        return result;
    }

    private Map<Integer, String> listToMapWithFilter(List<String> list) {
        Map<Integer, String> res = new TreeMap<>();

        for (String line : list) {
            String[] parseList = parseLineLight(line);

            if (parseList.length == 3) {
                res.put(Integer.parseInt(parseList[0]), parseList[0] + Main.separator + parseList[2]);
            } else if (parseList.length == 4) {
                res.put(Integer.parseInt(parseList[0]), parseList[0] + Main.separator + parseList[2] + Main.separator + parseList[3]);
            }
        }
        return res;
    }


    private Set<String> linksToSet(File file) throws IOException {
        Set<String> result = new HashSet<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                String[] list = parseLineLight(line);
                result.add(list[1]);
            }
        }
        return result;
    }
}
