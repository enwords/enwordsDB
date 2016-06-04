package development.subclasses;

import development.Main;

import java.io.*;
import java.util.*;

public class Sixth extends Zero {

    static Map<Integer, String> mapWithAudio;
    static Map<Integer, String> mapWithoutAudio;

    public void start(File langWordsFile, File langSentencesFile, File outFile) throws IOException {
        List<String> listOfWords = fileWordsToArrayList(langWordsFile);
        List<String> listOfSentences = fileToList(langSentencesFile);

//        Map<Integer, String> mapWithAudio = listOfWordsAndListOfSentencesToMap(listOfWords, listOfSentences, true);
//        Map<Integer, String> mapWithoutAudio = listOfWordsAndListOfSentencesToMap(listOfWords, listOfSentences, false);
        mapWithAudio = new TreeMap<>();
        mapWithoutAudio = new TreeMap<>();
        listOfWordsAndListOfSentencesToMap(listOfWords, listOfSentences);
        writeMapToFile(mapPlusMap(mapWithAudio, mapWithoutAudio) , outFile);
    }

    private Map<Integer, String> mapPlusMap(Map<Integer, String> mapWithAudio, Map<Integer, String> mapWithoutAudio) {

        Map<Integer, String> res = new TreeMap<>();

        for (Map.Entry<Integer, String> map1 : mapWithAudio.entrySet()) {
            int key1 = map1.getKey();
            String val1 = map1.getValue();

            if (mapWithoutAudio.containsKey(key1)) {
                String val2 = mapWithoutAudio.get(key1);
                String valRes = val1 + ":" + val2;
                res.put(key1, valRes);
            } else {
                res.put(key1, val1);
            }
        }

        for (Map.Entry<Integer, String> map1 : mapWithoutAudio.entrySet()) {
            int key1 = map1.getKey();
            String val1 = map1.getValue();

            if (!res.containsKey(key1)) {
                res.put(key1, val1);
            }
        }


        return res;
    }

    private Set strToSet(String string) {
        String[] arr = string.split(":");
        Set<String> set = new LinkedHashSet<>(Arrays.asList(arr));
        return set;
    }


    private void listOfWordsAndListOfSentencesToMap(List<String> listOfWords, List<String> listOfSentences) throws IOException {
        for (String st : listOfSentences) {
            List<String> arr = parseLine(st);
            String id = arr.get(0);
//            String lang = arr.get(1);
            String text = arr.get(2);
            boolean bool = Boolean.parseBoolean(arr.get(3));

            String [] splitText = removePunctuationAndDigits(text).split(" ");

            for (String s2 : splitText) {

                if (listOfWords.contains(s2)) {

                    int wordId = listOfWords.indexOf(s2) + 1;

                    if (bool) {
                        method(mapWithAudio, wordId, id);
                    }else  {
                        method(mapWithoutAudio, wordId, id);
                    }
                }
            }
        }
    }

    private void method(Map<Integer, String> map, int wordId, String sentenceId) {
        if (map.containsKey(wordId)) {
            map.put(wordId, map.get(wordId) + ":" + sentenceId);
        } else map.put(wordId, sentenceId);
    }


    public List<String> fileWordsToArrayList(File file) throws IOException {
        List<String> result = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();

                result.add(line.split("\\t")[1]);
            }
        }
        return result;
    }


    private void writeMapToFile(Map<Integer, String> map, File outFile) throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFile, false), "UTF-8"))) {
            for (Map.Entry<Integer, String> pair : map.entrySet()) {

                int wordId = pair.getKey();
                String value = pair.getValue();
                Set<String> set = strToSet(value);

                int count = 0;

                //TODO max amount of sentences linked with words = 100!!
                for (String sentId : set) {
                    if (count > 100) {
                        break;
                    } else {
                        printWriter.println(wordId + Main.separator + sentId);
                        count++;
                    }
                }
            }
        }
    }
}
