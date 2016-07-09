package development;

import development.subclasses.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {


    /**
     * OPTIONS
     */

    //DO YOU HAVE FILE WITH WORDS OF LEARNING LANGUAGE? Please add language name to set
    public static Set languagesWithYourWords = new HashSet<>();

    private static final String[] SET_VALUES = new String[] { "a", "b" };
    public static final Set<String> MY_SET = new HashSet<String>(Arrays.asList(SET_VALUES));

    public static String learningLang = "jpn";
    public static String nativeLang = "eng";

    String devDir = "src/development/files/";
//    String devDir = "src/test/files/";


    /******/

    /**
     * DO NO TOUCH!
     */
    public static String splitSpace = " ";//split inside sentence (jpn and cnm = "")
    public static String separator = "\t";//row separator
    public static int minWordLength = 1;//jpn and cnm = 1 !

    public static boolean checkJpnCnmLang() {
        return "jpn".equals(learningLang) || "cnm".equals(learningLang);
    }

    /******/


    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.start();
    }

    private void start() throws IOException {
        System.out.println("Wait a few minutes...");
        File sentencesWithAudioIn = new File(devDir + "/sentences_with_audio.csv");
        File sentences = new File(devDir + "/sentences.csv");
        File originalLinks = new File(devDir + "/links.csv");

        String tmpDir = devDir + "/seeds_data/" + learningLang + nativeLang + "/tmp/";
        File engLangSentencesTmp = new File(tmpDir + learningLang + "SentencesTmp.txt");
        File rusLangSentencesTmp = new File(tmpDir + nativeLang + "SentencesTmp.txt");
        File engSentencesWithAudioOut = new File(tmpDir + learningLang + "SentencesWithAudioColumnTmp.txt");
        File engSentencesWithAudioOutWithoutRepeat = new File(tmpDir + learningLang + "SentencesWithAudioColumnWithoutRepeat.txt");
        File engSentences = new File(tmpDir + learningLang + "Sentences.txt");
        File rusSentences = new File(tmpDir + nativeLang + "Sentences.txt");
        File langWords = new File(tmpDir + learningLang + "Words.txt");

        File langWordsAll = new File(devDir + learningLang + "WordsAll.txt");

        File engRusSentencesLinks = new File(devDir + "seeds_data/" + learningLang + nativeLang + "/" + "links.tsv");
        File wordSentenceLinks = new File(devDir + "seeds_data/" + learningLang + nativeLang + "/" + "word_sentence.tsv");
        File audioLinks = new File(devDir + "seeds_data/" + learningLang + nativeLang + "/" + "audio.tsv");
        File uniteSentence = new File(devDir + "seeds_data/" + learningLang + nativeLang + "/" + "sentences.tsv");
        File words = new File(devDir + "seeds_data/" + learningLang + nativeLang + "/" + "words.tsv");


        long startTime, endTime, duration;

        long l = 1000000000L;
        makeDir(devDir);
        startTime = System.nanoTime();
        first(sentences, engLangSentencesTmp, learningLang);
        first(sentences, rusLangSentencesTmp, nativeLang);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("first is ready " + duration + " sec");

        startTime = System.nanoTime();
        second(engLangSentencesTmp, sentencesWithAudioIn, engSentencesWithAudioOut);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("second is ready " + duration + " sec");

        startTime = System.nanoTime();
        third(engSentencesWithAudioOut, engSentencesWithAudioOutWithoutRepeat);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("third is ready " + duration + " sec");

        startTime = System.nanoTime();
        fourth(engSentencesWithAudioOutWithoutRepeat, rusLangSentencesTmp, originalLinks, engRusSentencesLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("fourth is ready " + duration + " sec");

        startTime = System.nanoTime();
        fifth(engSentencesWithAudioOutWithoutRepeat, langWordsAll);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("fifth is ready " + duration + " sec");


        startTime = System.nanoTime();
        sixth(langWordsAll, engSentencesWithAudioOutWithoutRepeat, engRusSentencesLinks, wordSentenceLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("sixth is ready " + duration + " sec");

        startTime = System.nanoTime();
        seventh(engSentencesWithAudioOutWithoutRepeat, rusLangSentencesTmp, wordSentenceLinks, engRusSentencesLinks, engSentences, rusSentences);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("seventh is ready " + duration + " sec");


        startTime = System.nanoTime();
        eighth(engRusSentencesLinks, wordSentenceLinks, langWordsAll, langWords);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("eighth is ready " + duration + " sec");


        startTime = System.nanoTime();
        ninth(engSentences, audioLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("ninth is ready " + duration + " sec");

        startTime = System.nanoTime();
        tenth(engSentences, rusSentences, uniteSentence);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("tenth is ready " + duration + " sec");

        startTime = System.nanoTime();
        eleventh(langWordsAll, words);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("eleventh is ready " + duration + " sec");

    }

    /**
     * Get tsv file of all words of first learningLang
     */
    private void eleventh(File langWordsAll, File words) throws IOException {
        CopyWords u = new CopyWords();
        u.start(langWordsAll, words);
    }

    /**
     * Get file with first and second learningLang united
     */
    private void tenth(File engSentences, File rusSentences, File uniteSentence) throws IOException {
        UniteSentences u = new UniteSentences();
        u.start(engSentences, rusSentences, uniteSentence);
    }

    /**
     * Get file with ids of first learningLang sentences which has audio
     */
    private void ninth(File engSentences, File audioLinks) throws IOException {
        MakeAudioLinkFile n = new MakeAudioLinkFile();
        n.start(engSentences, audioLinks);
    }

    /**
     * Get file with first learningLang words which exist in sentences which has links to translate
     */
    private void eighth(File engRusSentencesLinks, File wordSentenceLinks, File langWordsTmp, File langWords) throws IOException {
        FilterOfWordsFile filterOfWordsFile = new FilterOfWordsFile();
        filterOfWordsFile.start(engRusSentencesLinks, wordSentenceLinks, langWordsTmp, langWords);
    }

    /**
     * Get two files of first and second languages, each sentence has link to translate
     */
    private void seventh(File engSentencesWithAudioOutWithoutRepeat, File rusSentencesWithAudioOut, File wordSentenceLinks, File engrusLinks, File engSentences, File rusSentences) throws IOException {
        FilterOfSentencesFile x = new FilterOfSentencesFile();
        x.start(engSentencesWithAudioOutWithoutRepeat, rusSentencesWithAudioOut, wordSentenceLinks, engrusLinks, engSentences, rusSentences);
    }

    /**
     * Get file with links of id of words and id of sentence with the word
     */
    private void sixth(File langWords, File langSentences, File engRusSentencesLinks, File wordSentenceLinks) throws IOException {
        MakeFileOfWordSentenceLinks x = new MakeFileOfWordSentenceLinks();
        x.start(langWords, langSentences, engRusSentencesLinks, wordSentenceLinks);
    }

    /**
     * Word counting
     */
    private void fifth(File langSentences, File langWordsTmp) throws IOException {
        if (!checkJpnCnmLang()) {
            WordCount wordCount = new WordCount();
            wordCount.start(langSentences, langWordsTmp);
        }
    }

    /**
     * Get file of links (id of sentence - id of translate)
     */
    private void fourth(File langSentences, File secondLangSentences, File originalLinks, File links) throws IOException {
        MakeFileLinkEngRusSentences makeFileLinkEngRusSentences = new MakeFileLinkEngRusSentences();
        makeFileLinkEngRusSentences.start(langSentences, secondLangSentences, originalLinks, links);
    }

    /**
     * Delete repeated sentences
     */
    private void third(File langSentencesTmp, File langSentences) throws IOException {
        DeleteRepeatedSentences deleteRepeatedSentences = new DeleteRepeatedSentences();
        deleteRepeatedSentences.start(deleteRepeatedSentences.fileToList(langSentencesTmp), langSentences);
    }

    /**
     * Get file of sentences with audio column
     */
    private void second(File sentences, File sentencesWithAudioIn, File sentencesWithAudioOut) throws IOException {
        MakeSentenceFileWithAudioColumn makeSentenceFileWithAudioColumn = new MakeSentenceFileWithAudioColumn();
        makeSentenceFileWithAudioColumn.start(sentences, sentencesWithAudioIn, sentencesWithAudioOut);
    }

    /**
     * Get file of sentences with specific language
     */
    private void first(File sentencesWithAudioOut, File langSentencesTmp, String lang) throws IOException {
        MakeFileWithSentencesOfSpecificLanguage makeFileWithSentencesOfSpecificLanguage = new MakeFileWithSentencesOfSpecificLanguage();
        makeFileWithSentencesOfSpecificLanguage.start(sentencesWithAudioOut, langSentencesTmp, lang);
    }

    private void makeDir(String devDir) {
        File dir2 = new File(devDir + "/seeds_data");
        File dir3 = new File(devDir + "/seeds_data/" + learningLang + nativeLang);
        File dir4 = new File(devDir + "/seeds_data/" + learningLang + nativeLang + "/tmp");
        dir2.mkdir();
        dir3.mkdir();
        dir4.mkdir();
    }
}
