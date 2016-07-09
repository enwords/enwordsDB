package development.subclasses;

import development.Main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WordCount extends Zero {

    public void start(File langSentences, File langWordsTmp) throws IOException {

        List<String> listOfSentences = fileToList(langSentences);

        List<String> listOfWords = wordCount(listOfSentences);

        writeToFile(listOfWords, langWordsTmp);

    }

    public List<String> wordCount(List<String> list) throws IOException {
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String s : list) {
            String[] arr = parseLineLight(s);

            String text = arr[2];

            String[] words = removePunctuationAndDigits(text).split(Main.splitSpace);

            Set<String> set = conditions(words);

            for (String word : set) {
                if (wordCountMap.containsKey(word)) {
                    wordCountMap.put(word, wordCountMap.get(word) + 1);

                } else {
                    wordCountMap.put(word, 1);
                }
            }
        }

        List list2 = new ArrayList(wordCountMap.entrySet());

        Collections.sort(list2, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                return a.getValue() - b.getValue();
            }
        });

        Collections.reverse(list2);

        return list2;
    }


    public void writeToFile(List<String> list, File langWordsTmp) throws IOException {

//        try (PrintWriter printWriter = new PrintWriter(langWords);
        try (PrintWriter printWriter2 = new PrintWriter(langWordsTmp)) {
//            int idCount = 0;
            String sep = Main.separator;
            for (Object s : list) {
                String[] wordAndCount = s.toString().split("=");
                String word = wordAndCount[0];
                String count = wordAndCount[1];
                if ("1".equals(count) || (word.length() <= 3 && Integer.parseInt(count) < 3)) {
                    break;
                } else {
//                    idCount++;
//                    printWriter.println(idCount + sep + word);
                    if (!word.equals("")) {
                        printWriter2.println(Main.superWordCounter + sep + word + sep + count);
                        Main.superWordCounter++;
                    }
                }
            }
        }
    }

    public void setIdsOfYourWords(File input, File output) throws IOException {
        List<String> list = fileToList(input);
        List<String> res = new ArrayList<>();
        Set<String> set = new LinkedHashSet<>();
        set.addAll(list);

        for (String word : set) {

            res.add(Main.superWordCounter + Main.separator + word);
            Main.superWordCounter++;
        }

//        for (String line : list) {
//            String[] arr = parseLineLight(line);
//            String word = arr[1];
//            res.add(Main.superWordCounter + Main.separator + word);
//            Main.superWordCounter++;
//        }
        collectionToFile(res, output);
    }
}