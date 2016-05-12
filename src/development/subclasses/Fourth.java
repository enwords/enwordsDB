package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fourth extends Zero {
    public void start(File file, File file2, File file3, File file4) throws IOException {

        Map<String, String> firstMap = listToMap(fileToList(file));
        Map<String, String> secondMap = listToMap(fileToList(file2));
        List<String> list = fileToList(file3);

        List<String> result = new ArrayList<>();

        for (String string : list) {
            List<String> parsedString = parseLine(string);
            if (firstMap.containsKey(parsedString.get(0)) && secondMap.containsKey(parsedString.get(1))) {
                result.add(string);
            }
        }

        listToFile(result, file4);
    }
}
