package development.subclasses;

import development.Main;

import java.io.*;
import java.util.*;

public class Seventh extends Zero {


    public void start(File langWordsFile, File langSentencesFile, File outFile) throws IOException {
        List<String> listOfWords = fileWordsToArrayList(langWordsFile);
        List<String> listOfSentences = fileToList(langSentencesFile);

        Map<Integer, String> map = listOfWordsAndListOfSentencesToMap(listOfWords, listOfSentences);
        writeMapToFile(map, outFile);
    }

    private Set strToSet(String string) {
        String[] arr = string.split(":");
        Set<String> set = new TreeSet<>(Arrays.asList(arr));
        return set;
    }


    private Map<Integer, String> listOfWordsAndListOfSentencesToMap(List<String> listOfWords, List<String> listOfSentences) throws IOException {

        Map<Integer, String> res = new TreeMap<>();

        for (String st : listOfSentences) {
            List<String> arr = parseLine(st);
            String id = arr.get(0);
//            String lang = arr.get(1);
            String text = arr.get(2);

            String [] splitText = removePunctuationAndDigits(text).split(" ");

            for (String s2 : splitText) {

                if (listOfWords.contains(s2)) {

                    Integer wordId = listOfWords.indexOf(s2) + 1;
                   // String wordIdStr = wordId.toString();

                    if (res.containsKey(wordId)) {
                        res.put(wordId, res.get(wordId) + ":" + id);
                    } else res.put(wordId, id);
                }
            }
        }
        return res;
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

                int key = pair.getKey();
                String value = pair.getValue();
                Set<String> set = strToSet(value);

                int count = 0;

                //TODO max amount of sentences linked with words = 100!!
                for (String s : set) {
                    if (count > 100) {
                        break;
                    } else {
                        printWriter.println(key + Main.separator + s);
                        count++;
                    }
                }
            }
        }
    }
}
