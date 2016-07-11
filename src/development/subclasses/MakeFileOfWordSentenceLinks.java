package development.subclasses;

import development.Main;

import java.io.*;
import java.util.*;

public class MakeFileOfWordSentenceLinks extends Zero {

    public void start(File langWordsFile, File langSentencesFile, File engRusSentencesLinks , File outFile) throws IOException {

        Map<String, Integer> wordMap = fileToMap(langWordsFile);

        Set<String> engRusSentencesLinksSet = fileToSet(engRusSentencesLinks, 0);
//        List<String> wordList = fileWordsToArrayList(langWordsFile);
        List<String> listOfSentences = fileToList(langSentencesFile);
        Map<Integer, List<String>> mapWithAudio = new HashMap<>();
        Map<Integer, List<String>> mapWithoutAudio = new HashMap<>();
        listOfWordsAndListOfSentencesToMap(wordMap, listOfSentences, mapWithAudio, mapWithoutAudio, engRusSentencesLinksSet);
        Map<Integer, List<String>> m = mapPlusMap(mapWithAudio, mapWithoutAudio);
        writeMapToFile(m, outFile);

    }

    private Map<String, Integer> fileToMap(File file) throws IOException {
        Map<String, Integer> result = new LinkedHashMap<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                String[] arr = parseLineLight(line);

                result.put(arr[1], Integer.parseInt(arr[0]));
            }
        }
        return result;
    }

    private Map<Integer, List<String>> mapPlusMap(Map<Integer, List<String>> mapWithAudio, Map<Integer, List<String>> mapWithoutAudio) {

        Map<Integer, List<String>> res = new HashMap<>();

        for (Map.Entry<Integer, List<String>> map1 : mapWithAudio.entrySet()) {
            Integer key1 = map1.getKey();
            List<String> val1 = map1.getValue();

            if (mapWithoutAudio.containsKey(key1)) {
                List<String> val2 = mapWithoutAudio.get(key1);
                val1.addAll(val2);
                res.put(key1, val1);
            } else {
                res.put(key1, val1);
            }
        }

        for (Map.Entry<Integer, List<String>> map1 : mapWithoutAudio.entrySet()) {
            int key1 = map1.getKey();
            List<String> val1 = map1.getValue();

            if (!res.containsKey(key1)) {
                res.put(key1, val1);
            }
        }

        TreeMap<Integer, List<String>> treeMap = new TreeMap<>();
        treeMap.putAll(res);

        return treeMap;
    }


    private void listOfWordsAndListOfSentencesToMap(Map<String, Integer> wordMap, List<String> listOfSentences,
                                                    Map<Integer, List<String>> mapWithAudio, Map<Integer, List<String>> mapWithoutAudio, Set<String>engRusSentencesLinksSet) {

        String[] arr;
        String sentenceId;
        String text;
        String bool;
        String[] words;

        Map<Integer, String> localMap;


        for (String st : listOfSentences) {
            arr = parseLineLight(st);
            sentenceId = arr[0];
            text = arr[2];
            bool = arr[3];
            localMap = new HashMap<>();

            if (!Main.checkJpnCmnLang()) {
                words = removePunctuationAndDigits(text).split(Main.splitSpace);

                for (String word : words) {

                    if (wordMap.containsKey(word)) {
                        localMap.put(wordMap.get(word), sentenceId);
                    }
                }
            }else {

                for (Map.Entry<String, Integer> pair : wordMap.entrySet()) {

                    String word = pair.getKey();
                    int wordId = pair.getValue();

                    if (text.contains(word)){
                        localMap.put(wordId, sentenceId);
                    }
                }

            }

            for (Map.Entry<Integer, String> pair : localMap.entrySet()) {
                int wordId = pair.getKey();
                String senId = pair.getValue();

                if (engRusSentencesLinksSet.contains(senId)) {
                    if ("true".equals(bool)) {
                        putToMap(mapWithAudio, wordId, senId);
                    } else {
                        putToMap(mapWithoutAudio, wordId, senId);
                    }
                }

            }
        }
    }

    private void putToMap(Map<Integer, List<String>> map, int wordId, String sentenceId) {
        if (map.containsKey(wordId)) {
            List<String> val = map.get(wordId);
            val.add(sentenceId);
            map.put(wordId, val);
        } else {
            List<String> newList = new ArrayList<>();
            newList.add(sentenceId);
            map.put(wordId, newList);
        }
    }


    private List<String> fileWordsToArrayList(File file) throws IOException {
        List<String> result = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();

                result.add(line.split(Main.separator)[1]);
            }
        }
        return result;
    }


    private void writeMapToFile(Map<Integer, List<String>> map, File outFile) throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFile, false), "UTF-8"))) {
            for (Map.Entry<Integer, List<String>> pair : map.entrySet()) {

                int wordId = pair.getKey();
                List<String> value = pair.getValue();

                int count = 0;

                //TODO max amount of sentences linked with words = 100!!
                for (String sentId : value) {
                    if (count > 100) {
                        break;
                    } else {
                        String res = wordId + Main.separator + sentId;
                        printWriter.println(res);

                        Main.allWordSenLinks.add(res);

                        count++;
                    }
                }
            }
        }
    }
}
