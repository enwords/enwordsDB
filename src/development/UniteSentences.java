package development;

import development.subclasses.Zero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniteSentences extends Zero {
    public void start(File engSentences, File rusSentences, File uniteSentence) throws IOException {
        List<String> engList = fileToList(engSentences, Main.langOneId);
        List<String> rusList = fileToList(rusSentences, Main.langTwoId);

        engList.addAll(rusList);
        collectionToFile(engList, uniteSentence);
    }

    public List<String> fileToList(File file, String langId) throws IOException {
        List<String> result = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();

                String [] arr = line.split(Main.separator);
                String id = arr[0];
                String text = arr[1];

                result.add(id+Main.separator+ langId+ Main.separator +text);
            }
        }
        return result;
    }

}
