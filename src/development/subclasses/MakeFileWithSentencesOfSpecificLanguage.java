package development.subclasses;

import java.io.*;
import java.util.List;

public class MakeFileWithSentencesOfSpecificLanguage extends Zero {

    public void start(File sentences, File langSentencesTmp, String lang) throws IOException {

        listToFile(sentences, langSentencesTmp, lang);
    }

    public void listToFile(File file, File fileout, String lang) throws IOException {


        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                PrintWriter printWriter = new PrintWriter(fileout)
        ) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();

                String [] splitSen = parseLineLight(line);
                if (splitSen[1].equals(lang)) {
                    printWriter.println(line);
                }
            }
        }
    }
}
