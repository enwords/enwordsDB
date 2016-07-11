package development;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LangStatistic {

    public static Map<String, Integer> counter = new HashMap<>();

    public static void main(String[] args) throws IOException {
        LangStatistic v = new LangStatistic();
        v.start();
    }

    private void start() throws IOException {
        Map<String, String> map = fileToMap(new File("src/development/files/sentences.csv"));

        for (Map.Entry<String, Integer> pair : entriesSortedByValues(counter)) {
            System.out.println(pair.getKey() + " " + map.get(pair.getKey()) + " " + pair.getValue());
        }

        System.out.println();
        String s = entriesSortedByValues(counter).toString();
        System.out.println(s.replaceAll("\\d+", "").replaceAll("\\[", "\'").replaceAll("\\]", "").replaceAll("\\=", "\'").replaceAll(" ", " '"));
    }


    public Map<String, String> fileToMap(File file) throws IOException {
        Map<String, String> result = new TreeMap<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();

                try {
                    String[] arr = line.split(Main.separator);

                    String id = arr[0];
                    String lang = arr[1];
                    String sen = arr[2];


                    result.put(lang, sen);

                    if (counter.containsKey(lang)) {
                        counter.put(lang, counter.get(lang) + 1);
                    } else {
                        counter.put(lang, 1);
                    }
                } catch (ArrayIndexOutOfBoundsException s) {
                    System.out.println("err");
                }
            }
        }
        return result;
    }

    static List<Map.Entry<String, Integer>> entriesSortedByValues(Map<String, Integer> map) {

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(sortedEntries,
                new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                }
        );

        return sortedEntries;
    }
}
