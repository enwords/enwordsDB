package development;

import development.subclasses.*;

import java.io.File;
import java.io.IOException;

public class Main {

    public static String separator = "\t";

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.start();
    }

    private void start() throws IOException {

        String devDir = "src/development/files";
        String lang = "eng";
        String secondLang = "rus";


        File sentencesWithAudioIn = new File(devDir + "/sentences_with_audio.csv");
        File sentences = new File(devDir + "/sentences.csv");
        File sentencesWithAudioOut = new File(devDir + "/tmp" + "/sentencesWithAudio.txt");
        File langSentencesTmp = new File(devDir + "/tmp/" + lang + "SentencesTmp.txt");
        File langSentences = new File(devDir + "/out/" + lang + "Sentences.txt");

        File secondLangSentences = new File(devDir + "/out/" + secondLang + "Sentences.txt");
        File originalLinks = new File(devDir + "/links.csv");
        File links = new File(devDir + "/out/" + lang + secondLang + "Links.txt");

        File langWords = new File(devDir + "/out/" + lang + "Words.txt");
        File langWordsTmp = new File(devDir + "/tmp/" + lang + "WordsTmp.txt");

        File wordSentenceLinks = new File(devDir + "/out/" + lang + "WordSentenceLinks.txt");

        long startTime, endTime, duration;

        makeDir(devDir);

        startTime = System.nanoTime();
        second(sentences, langSentencesTmp, lang);
        second(sentences, secondLangSentences, secondLang);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 60000000000L;
        System.out.println("first is ready " + duration + " min");

        startTime = System.nanoTime();
        first(langSentencesTmp, sentencesWithAudioIn, sentencesWithAudioOut);

        endTime = System.nanoTime();
        duration = (endTime - startTime) / 60000000000L;
        System.out.println("second is ready " + duration + " min");

        startTime = System.nanoTime();
        third(sentencesWithAudioOut, langSentences);

        endTime = System.nanoTime();
        duration = (endTime - startTime) / 60000000000L;
        System.out.println("third is ready " + duration + " min");

        startTime = System.nanoTime();
        fourth(langSentences, secondLangSentences, originalLinks, links);

        endTime = System.nanoTime();
        duration = (endTime - startTime) / 60000000000L;
        System.out.println("fourth is ready " + duration + " min");

        startTime = System.nanoTime();
        fifth(langSentences, langWords, langWordsTmp);


        endTime = System.nanoTime();
        duration = (endTime - startTime) / 60000000000L;
        System.out.println("fifth is ready " + duration + " min");

        startTime = System.nanoTime();
        sixth(langWords, langSentences, wordSentenceLinks);


        endTime = System.nanoTime();
        duration = (endTime - startTime) / 60000000000L;
        System.out.println("sixth is ready " + duration + " min");

    }

    /**
     * Get file with links of id of words and id of sentence with the word
     */
    private void sixth(File langWords, File langSentences, File wordSentenceLinks) throws IOException {
        Sixth sixth = new Sixth();
        sixth.start(langWords, langSentences, wordSentenceLinks);
    }

    /**
     * Word counting
     */
    private void fifth(File langSentences, File langWords, File langWordsTmp) throws IOException {
        Fifth fifth = new Fifth();
        fifth.start(langSentences, langWords, langWordsTmp);
    }

    /**
     * Get file of links (id of sentence - id of translate)
     */
    private void fourth(File langSentences, File secondLangSentences, File originalLinks, File links) throws IOException {
        Fourth fourth = new Fourth();
        fourth.start(langSentences, secondLangSentences, originalLinks, links);
    }

    /**
     * Delete repeated sentences
     */
    private void third(File langSentencesTmp, File langSentences) throws IOException {
        Third third = new Third();
        third.start(third.fileToList(langSentencesTmp), langSentences);
    }

    /**
     * Get file of sentences with specific language
     */
    private void second(File sentencesWithAudioOut, File langSentencesTmp, String lang) throws IOException {
        Second second = new Second();
        second.listToFile(second.fileToList(sentencesWithAudioOut), langSentencesTmp, lang);
    }

    /**
     * Get file of sentences with audio column
     */
    private void first(File sentences, File sentencesWithAudioIn, File sentencesWithAudioOut) throws IOException {
        First first = new First();
        first.writeListAudioToFile(sentencesWithAudioOut, first.fileToList(sentencesWithAudioIn), first.fileToList(sentences));
    }

    private void makeDir(String devDir) {
        File dir1 = new File(devDir + "/tmp");
        File dir2 = new File(devDir + "/out");
        dir1.mkdir();
        dir2.mkdir();
    }
}
