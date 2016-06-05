package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MakeFileWithSentencesOfSpecificLanguage extends Zero {

    public void start(File sentences, File langSentencesTmp, String lang) throws IOException {
        List<String> sentencesList = fileToList(sentences);
        listToFile(sentencesList, langSentencesTmp, lang);
    }


    public void listToFile(List<String> list, File file, String lang) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {

            for (String sentence : list) {

                String [] splitSen = parseLineLight(sentence);
                if (splitSen[1].equals(lang)) {
                    printWriter.println(sentence);
                }
            }
        }
    }

}
