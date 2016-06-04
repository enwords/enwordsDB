package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Eighth extends Zero {
    public void start(File engRusSentencesLinks, File wordSentenceLinks, File langWordsTmp, File langWords) throws IOException {
        List<String> engRusSentencesLinksList = fileToList(engRusSentencesLinks);
        List<String> wordSentenceLinksList = fileToList(wordSentenceLinks);
        List<String> langWordsTmpList = fileToList(langWordsTmp);

        Set<String> set = listToSet(engRusSentencesLinksList);
        Set<String> set2 = listToSet2(set, wordSentenceLinksList);

        listToFile(langWordsTmpList, set2, langWords);

    }

    private void listToFile(List<String> list,Set<String> set, File file) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {

            for (String s : list) {
                String[] arr = parseLineLight(s);
                if (set.contains(arr[0])) {
                    printWriter.println(s);
                }
            }
        }
    }

    private Set<String> listToSet2(Set<String> set, List<String> wordSentenceLinksList) {
        Set<String> set2 = new HashSet<>();
        String[] arr;
        for (String s : wordSentenceLinksList) {
            arr = parseLineLight(s);
            if (set.contains(arr[1])) {
                set2.add(arr[0]);
            }
        }

        return set2;
    }

    private Set<String> listToSet(List<String> engRusSentencesLinksList) {
        Set<String> set = new HashSet<>();
        String[] arr;
        for (String s : engRusSentencesLinksList) {
            arr = parseLineLight(s);
            set.add(arr[0]);
        }

        return set;
    }
}
