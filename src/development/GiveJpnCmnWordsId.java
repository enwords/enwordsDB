package development;

import development.subclasses.Zero;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GiveJpnCmnWordsId extends Zero {
    public static void main(String[] args) throws IOException {

        GiveJpnCmnWordsId x = new GiveJpnCmnWordsId();
        x.tmp();

    }

    public void tmp() throws IOException {
        File jpnWords = new File("/home/sadedv/Desktop/enwordsFiles/jpnWords.txt");
        List<String> l = fileToList(jpnWords);
        List<String> l2 = new ArrayList<>();

        int count = 1;
        for (String s : l) {
            l2.add(count + "\t" + s);
            count++;
        }


        collectionToFile(l2, new File("/home/sadedv/Documents/repo/enwordsDB/src/development/files/jpnWordsAll.txt"));
    }
}
