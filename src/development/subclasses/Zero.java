package development.subclasses;

import development.Main;

import java.io.*;
import java.util.*;

public class Zero {

    public String convertStringForTSV(String text) {
        if (text.contains("\"")) {
            text = "\"" + text.replaceAll("\"", "\"\"") + "\"";
        }

        if (text.contains("\'")) {
            text = text.replaceAll("\'", "\'\'");
        }
        return text;
    }

    public <T> Set<T> fileToSet(File file, int param) throws IOException {
        Set<T> result = new HashSet<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();

                String[] arr = parseLineLight(line);

                result.add((T) arr[param]);

            }
        }
        return result;
    }

    public <T> Set<T> fileToSet(File file) throws IOException {
        Set<T> result = new HashSet<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                result.add((T) line);

            }
        }
        return result;
    }

//    public <T> void collectionToFile( Collection<T> list, File file) throws IOException {
//        try (PrintWriter printWriter = new PrintWriter(file)) {
//
//            for (T sentence : list) {
//                printWriter.println(sentence);
//            }
//        }
//    }
//
//    public <T> void setToFile( Set<T> list, File file) throws IOException {
//        try (PrintWriter printWriter = new PrintWriter(file)) {
//
//            for (T sentence : list) {
//                printWriter.println(sentence);
//            }
//        }
//    }

    public <T> void collectionToFile(Collection<T> list, File file) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {

            for (T sentence : list) {
                printWriter.println(sentence);
            }
        }
    }

    public String[] parseLineLight(String line) {
        String[] list = line.split(Main.separator);
        return list;
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

    /**
     * delete bad words for english
     */
    public Set<String> conditions(String[] words) {
        Set<String> set = new HashSet<>();

        if ("eng".equals(Main.learningLang)) {
            for (String word : words) {
                if (((word.length() >= Main.minWordLength) || "i".equals(word)) && !word.startsWith("'")
                        && (!"tom".equals(word) && !"mary".equals(word) && !"tatoeba".equals(word) && !"th".equals(word)
                        && !word.startsWith("tom'") && !word.startsWith("mary'"))) {

                    if (word.length() <= 20) {
                        if (set.size() < Main.divider) {
                            set.add(word);
                        } else break;
                    }
                }
            }
            return set;
        } else {
            for (String word : words) {
                if (!word.startsWith("'") && (!"tom".equals(word) && !"mary".equals(word) && !"tatoeba".equals(word)
                        && !word.startsWith("tom'") && !word.startsWith("mary'"))) {
                    if (word.length() <= 20) {
                        if (set.size() < Main.divider) {
                            set.add(word);
                        } else break;
                    }
                }
            }
            return set;
        }
    }


    public String removePunctuationAndDigits(String word) {
//        return word.replaceAll("(?!')\\W+\\p{Digit}+", " ").toLowerCase();

        String res1 = word.replaceAll("[^'\\p{L}]+", Main.splitSpace).toLowerCase();
        String res2 = "";
        if ("eng".equals(Main.learningLang)) {
            String[] arr = res1.split(Main.splitSpace);
            for (String s : arr) {
                if (s.endsWith("'s") && !s.equals("let's")) {
                    s = s.replace("'s", "");
                } else if (s.endsWith("s'")) {
                    s = s.replace("s'", "");
                } else if (s.endsWith("rs")) {
                    s = replaceLast(s, "rs", "r");
                }
                res2 += (Main.splitSpace + s);
            }
        } else {
            return res1;
        }
        return res2;
    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

}
