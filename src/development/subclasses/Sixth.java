package development.subclasses;

import development.Main;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Sixth extends Zero {

    public void start(File langWordsFile, File langSentencesFile, File outFile) throws IOException {
        List<String> listOfWords = fileToList(langWordsFile);
        List<String> listOfSentences = fileToList(langSentencesFile);

        Map<String, Queue<String>> map = method(listOfWords, listOfSentences);

        writeMapToFile(map, outFile);
    }

    private void writeMapToFile(Map<String, Queue<String>> map, File outFile) throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFile, false), "UTF-8"))) {
            //        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFile, true), "UTF-8"));
            for (Map.Entry<String, Queue<String>> pair : map.entrySet()) {
                String key = pair.getKey();
                Queue<String> idsOfLinkedSentences = pair.getValue();

                for (String s : idsOfLinkedSentences) {
                    printWriter.println(key + Main.separator + s);
                }
            }
        }
    }

    public Map<String, Queue<String>> method(List<String> listOfWords, List<String> listOfSentences) throws IOException {
        Map<String, Queue<String>> map = new LinkedHashMap<>();

        for (String wordLine : listOfWords) {
            String[] arr = wordLine.split(Main.separator);
            String wordId = arr[0];
            String word = arr[1].toLowerCase();
//          String rating = arr[2];

            Queue<String> idsOfLinkedSentences = new LinkedBlockingQueue<>();
            for (String line : listOfSentences) {

                String[] arrSen = line.split(Main.separator);

                String id = arrSen[0];
                String engLang = arrSen[1];
                String engText = arrSen[2];

                String[] words = removePunctuationAndDigits(engText).split(" ");

                List<String> list = Arrays.asList(words);

                if (idsOfLinkedSentences.size() >= 100) {
                    break;
                }
                if (list.contains(word)) {
                    idsOfLinkedSentences.add(id);
                }
            }
            map.put(wordId, idsOfLinkedSentences);
        }
        return map;
    }
}
