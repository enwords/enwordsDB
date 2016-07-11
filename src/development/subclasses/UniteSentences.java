package development.subclasses;

import development.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniteSentences extends Zero {
    public void start(File engSentences, File rusSentences, File uniteSentence) throws IOException {
        List<String> engList = fileToList(engSentences, Main.learningLang);
        List<String> rusList = fileToList(rusSentences, Main.nativeLang);

        engList.addAll(rusList);
        collectionToFile(engList, uniteSentence);
    }

    public List<String> fileToList(File file, String lang) throws IOException {
        List<String> result = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                String[] arr = line.split(Main.separator);
                String id = arr[0];
//                String text = convertStringForTSV(arr[1]);
                String text = arr[1];

                String res = id + Main.separator + lang + Main.separator + text;
                result.add(res);
                Main.allSen.add(res);
            }
        }
        return result;
    }

}
