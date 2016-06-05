package development.subclasses;

import development.Main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

public class MakeSentenceFileWithAudioColumn extends Zero {

    public void start(File sentences, File sentencesWithAudioIn, File sentencesWithAudioOut) throws IOException {
        List<String> listSentences = fileToList(sentences);
        Set<String> setAudio = fileToSet(sentencesWithAudioIn);
        writeListAudioToFile(listSentences, setAudio, sentencesWithAudioOut);

    }

    private void writeListAudioToFile(List<String> listSentences, Set<String> setAudio, File sentencesWithAudioOut) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(sentencesWithAudioOut)) {
            boolean isAudio;

            for (String sentence : listSentences) {
                String[]splitSentencesList = parseLineLight(sentence);
                isAudio = setAudio.contains(splitSentencesList[0]);
                printWriter.println(sentence + Main.separator + isAudio);
            }
        }
    }
}
