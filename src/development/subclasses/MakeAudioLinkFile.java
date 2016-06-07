package development.subclasses;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class MakeAudioLinkFile extends Zero {
    public void start(File engSen, File rusSen, File audioLinks) throws IOException {
        Set<String> set = fileToSet(engSen, 0);
        Set<String> set2 = fileToSet(rusSen, 0);

        Set<Integer> set3 = setPlusSetStringToInt(set, set2);
        collectionToFile(set3, audioLinks);

    }

    private Set<Integer> setPlusSetStringToInt(Set<String> set, Set<String> set2) {
        set.addAll(set2);
        Set<Integer> res = new TreeSet<>();

        for (String sentence : set) {
            int x = Integer.parseInt(sentence);
            res.add(x);
        }

        return res;
    }
}
