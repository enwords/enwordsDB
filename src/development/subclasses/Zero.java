package development.subclasses;

import development.Main;

import java.io.*;
import java.util.*;

public class Zero {

    public void listToFile(List<String> list, File file) throws IOException {

//        try (PrintWriter printWriter = new PrintWriter(file)) {
//
//            String result = "";
//
//            for (String string : list) {
//                result += (string + sep);
//            }
//            printWriter.println(result.substring(0, result.length()-1));
//        }

        try (PrintWriter printWriter = new PrintWriter(file)) {

            for (String sentence : list) {
                printWriter.println(sentence);
            }
        }
    }

    public List<String> parseLine(String line) throws IOException {
        List<String> result = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(line, Main.separator);
        while (tokenizer.hasMoreTokens()) {
            result.add(tokenizer.nextToken());
        }
        return result;
    }

    public List<String> fileToList(File file) throws IOException {
        List<String> result = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                result.add(line);
            }
        }
        return result;
    }

    private List<String> mapToList(Map<String, String> map) {
        List<String> res = new ArrayList<>();

        for (Map.Entry<String, String> pair : map.entrySet()) {
            res.add(pair.getValue());
        }
        return res;
    }

    public Map<String, String> listToMap(List<String> list) throws IOException {
        Map<String, String> map = new LinkedHashMap<>();
        for (String sentence : list) {
            List<String> splitSen = parseLine(sentence);
            map.put(splitSen.get(0), sentence);
        }

        return map;
    }

    /**
     * delete bad words for english
     */
    public Set<String> conditions(String[] words) {
        Set<String> set = new HashSet<>();

//        for (String word : words) {
//            if (((word.length() > 1) || "i".equals(word)) && !word.startsWith("'")
//                    && (!"the".equals(word) && !"tom".equals(word) && !"mary".equals(word)
//                    && !word.startsWith("tom'") && !word.startsWith("mary'"))) {
//                set.add(word);
//            }
//        }
        for (String word : words) {
            if (((word.length() > 1) || "i".equals(word)) && !word.startsWith("'")
                    && (!"tom".equals(word) && !"mary".equals(word) && !"tatoeba".equals(word) && !"th".equals(word)
                    && !word.startsWith("tom'") && !word.startsWith("mary'"))) {
                set.add(word);
            }
        }
        return set;
    }


    public String removePunctuationAndDigits(String word) {
        return word.replaceAll("(?!')\\W+", " ").replaceAll("\\p{Digit}+", " ").toLowerCase();
    }
}
