package development.subclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MakeAudioLinkFile extends Zero {
    public void start(File engSen,  File audioLinks) throws IOException {
        Set<Integer> set = fileToSet(engSen, 0);
        collectionToFile(set, audioLinks);
    }

    public Set<Integer> fileToSet(File file, int param) throws IOException {
        Set<Integer> result = new TreeSet<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();

                String[] arr = parseLineLight(line);

                if ("true".equals(arr[2])) {
                    result.add(Integer.parseInt(arr[param]));
                }
            }
        }
        return result;
    }
}
