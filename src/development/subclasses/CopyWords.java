package development.subclasses;

import development.Main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CopyWords extends Zero {
    public void start(File langWordsAll, File words) throws IOException {
        List<String> oldWordsList = fileToList(langWordsAll);
        collectionToFile(oldWordsList, words);
    }

    private void collectionToFile(List<String> list, File file) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {

            for (String sentence : list) {
                String[] arr = sentence.split(Main.separator);

                String id = arr[0];
                String text = convertStringForTSV(arr[1]);

                printWriter.println(id + Main.separator + Main.langOneId + Main.separator + text);
            }
        }
    }
}
