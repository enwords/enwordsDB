package development.subclasses;

import development.Main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class First extends Zero {

    public void writeListAudioToFile(File file, List<String> listAudio, List<String> listSentences) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            boolean isAudio;
            List<String> splitSentencesList;
            for (String sentence : listSentences) {
                splitSentencesList = parseLine(sentence);
                isAudio = listAudio.contains(splitSentencesList.get(0));
                printWriter.println(sentence + Main.separator + isAudio);
            }
        }
    }
}
