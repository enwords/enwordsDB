package development;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class First {

    static File sentencesWithAudio = new File("src/development/test/sentences_with_audio.csv");
    static File sentences = new File("src/development/test/sentences.csv");
    static File sentences2 = new File("src/development/test/sentences2.csv");

    static List<String> list;


    public static void main(String[] args) throws IOException {
        First first = new First();
        first.read(sentencesWithAudio);
        first.parser(sentences, sentences2);
    }

    private void parser(File file, File file2) throws IOException {

        try (PrintWriter printWriter = new PrintWriter(file2);
             BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            String[] arr;
            String id;
            String lang;
            String text;
            boolean isAudio;
            while (fileReader.ready())

            {
                line = fileReader.readLine();
                arr = line.split("\\t");
                id = arr[0];
                lang = arr[1];
                text = arr[2];
                isAudio = list.contains(id);

                printWriter.println(id + "\t" + lang + "\t" + text + "\t" + isAudio);
            }
        }
    }

    private void read(File file) throws IOException {


        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            list = new ArrayList<>();
            String line;
            while (fileReader.ready()) {
                line = fileReader.readLine();
                list.add(line);
            }
        }
    }

}
