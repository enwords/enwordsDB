package development.subclasses;

import development.Main;

import java.io.*;
import java.util.*;

public class Sixth extends Zero {
    public void start(File engSentencesWithAudioOutWithoutRepeat, File rusSentencesWithAudioOut, File wordSentenceLinks,
                      File engrusLinks, File engSentences, File rusSentences) throws IOException {

        List<String> engList = fileToList(engSentencesWithAudioOutWithoutRepeat);
        List<String> rusList = fileToList(rusSentencesWithAudioOut);

        Map<Integer, String> mapEngSen = listToMapWithFilter(engList);
        Map<Integer, String> mapRusSen = listToMapWithFilter(rusList);
        List<String> linksList = linksToList(wordSentenceLinks);
        List<String> engrusLinksList = engrusLinksListToList(engrusLinks, linksList);

        List<String> listE = mapToList(mapEngSen, engrusLinksList);
        List<String> listR = mapToList(mapRusSen, engrusLinksList);

        listToFile(listE, engSentences);
        listToFile(listR, rusSentences);

    }

    private List<String> mapToList(Map<Integer, String> mapEngSen, List<String> engrusLinksList) {
        List<String> result = new LinkedList<>();

        for (Map.Entry<Integer, String> pair : mapEngSen.entrySet()) {
            Integer key = pair.getKey();
            String value = pair.getValue();

            if (engrusLinksList.contains(key.toString())) {
                result.add(value);
            }
        }
        return result;
    }

    private List<String> engrusLinksListToList(File engrusLinks, List<String> linksList) throws IOException {
        List<String> result = new LinkedList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(engrusLinks.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                List<String> list = parseLine(line);
                if (linksList.contains(list.get(0))) result.addAll(list);
            }
        }
        return result;
    }


    private Map<Integer, String> listToMapWithFilter(List<String> list) throws IOException {
        Map<Integer, String> res = new TreeMap<>();

        for (String line : list) {
            List<String> parseList = parseLine(line);
            res.put(Integer.parseInt(parseList.get(0)), parseList.get(0) + Main.separator + parseList.get(2) + Main.separator + parseList.get(3));
        }
        return res;
    }


    private List<String> linksToList(File file) throws IOException {
        List<String> result = new LinkedList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                List<String> list = parseLine(line);
                result.add(list.get(1));
            }
        }
        return result;
    }
}
