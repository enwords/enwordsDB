package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Second extends Zero {

    public void listToFile(List<String> list, File file, String lang) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {

            for (String sentence : list) {

                List<String> splitSen = parseLine(sentence);
                if (splitSen.get(1).equals(lang)) {
                    printWriter.println(sentence);
                }
            }
        }
    }

}
