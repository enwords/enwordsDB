package development;

import development.subclasses.*;

import java.io.File;
import java.io.IOException;

public class Main {

    static String devDir = "src/development/test";

    public static String separator = "\t";
    static String lang = "eng";
    static String secondLang = "rus";
    static File sentencesWithAudioIn = new File(devDir + "/sentences_with_audio.csv");
    static File sentences = new File(devDir + "/sentences.csv");
    static File sentencesWithAudioOut = new File(devDir + "/tmp" + "/sentencesWithAudio.txt");

    static File langSentencesTmp = new File(devDir + "/tmp/" + lang + "SentencesTmp.txt");
    static File langSentences = new File(devDir + "/out/" + lang + "Sentences.txt");

    static File secondLangSentences = new File(devDir + "/out/" + secondLang + "Sentences.txt");
//    static File secondLangSentences = new File(devDir + "/" + secondLang + "Sentences.csv");

    static File originalLinks = new File(devDir + "/links.csv");
    static File links = new File(devDir + "/out/" + lang + secondLang + "Links.txt");

    static File langWords = new File(devDir + "/out/" + lang + "Words.txt");
    static File langWordsTmp = new File(devDir + "/tmp/" + lang + "WordsTmp.txt");

    static File wordSentenceLinks = new File(devDir + "/out/" + lang + "WordSentenceLinks.txt");

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.makeDir();

        main.second(sentences, langSentencesTmp, lang);
        main.second(sentences, secondLangSentences, secondLang);

        main.first(langSentencesTmp, sentencesWithAudioIn, sentencesWithAudioOut);

        main.third(sentencesWithAudioOut, langSentences);
        main.fourth(langSentences, secondLangSentences, originalLinks, links);

        main.fifth(langSentences, langWords, langWordsTmp);
        main.sixth(langWords, langSentences, wordSentenceLinks);
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

    private void makeDir() {
        File dir1 = new File(devDir + "/tmp");
        File dir2 = new File(devDir + "/out");
        dir1.mkdir();
        dir2.mkdir();
    }
}
