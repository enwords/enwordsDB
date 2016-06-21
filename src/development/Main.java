package development;

import development.subclasses.*;

import java.io.File;
import java.io.IOException;

public class Main {


    /**
     * OPTIONS
     */
    public static String lang = "eng";
    public static String secondLang = "rus";
    public static String langOneId = "1";
    public static String langTwoId = "2";

    String devDir = "src/development/files/";
//    String devDir = "src/test/files/";


    /******/

    /**
     * DO NO TOUCH!
     */
    public static String splitSpace = " ";//split inside sentence (jpn and cnm = "")
    public static String separator = "\t";//row separator
    public static int minWordLength = 2;//jpn and cnm = 1 !

    public static boolean checkLang() {
        return "jpn".equals(lang) || "cnm".equals(lang);
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

        File engLangSentencesTmp = new File(devDir + "tmp/" + lang + "SentencesTmp.txt");
        File rusLangSentencesTmp = new File(devDir + "tmp/" + secondLang + "SentencesTmp.txt");
        File engSentencesWithAudioOut = new File(devDir + "tmp/" + lang + "SentencesWithAudioTmp.txt");
        File rusSentencesWithAudioOut = new File(devDir + "tmp/" + secondLang + "SentencesWithAudioTmp.txt");
        File engSentencesWithAudioOutWithoutRepeat = new File(devDir + "tmp/" + lang + "SentencesWithAudioWithoutRepeat.txt");

        File langWordsTmp = new File(devDir + lang + "WordsTmp.txt");

        File engRusSentencesLinks = new File(devDir +"seeds_data/"+ lang + secondLang + "/" + lang + secondLang + "SentencesLinks.txt");
        File langWords = new File(devDir +"seeds_data/"+ lang + secondLang + "/" + lang + "Words.txt");//
        File wordSentenceLinks = new File(devDir +"seeds_data/"+ lang + secondLang + "/" + lang + "WordSentenceLinks.txt");
        File engSentences = new File(devDir +"seeds_data/" + lang + secondLang + "/" + lang + "Sentences.txt");
        File rusSentences = new File(devDir +"seeds_data/"+ lang + secondLang + "/" + secondLang + "Sentences.txt");
        File audioLinks = new File(devDir +"seeds_data/"+ lang + secondLang + "/" + "audioLinks.txt");
        File uniteSentence = new File(devDir +"seeds_data/"+ lang + secondLang + "/" + "sentences.txt");



        long startTime, endTime, duration;

        long l = 1000000000L;
        makeDir(devDir);
        startTime = System.nanoTime();
        first(sentences, engLangSentencesTmp, lang);
        first(sentences, rusLangSentencesTmp, secondLang);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("first is ready " + duration + " sec");

        startTime = System.nanoTime();
        second(engLangSentencesTmp, sentencesWithAudioIn, engSentencesWithAudioOut);
        second(rusLangSentencesTmp, sentencesWithAudioIn, rusSentencesWithAudioOut);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("second is ready " + duration + " sec");

        startTime = System.nanoTime();
        third(engSentencesWithAudioOut, engSentencesWithAudioOutWithoutRepeat);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("third is ready " + duration + " sec");

        startTime = System.nanoTime();
        fourth(engSentencesWithAudioOutWithoutRepeat, rusSentencesWithAudioOut, originalLinks, engRusSentencesLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("fourth is ready " + duration + " sec");

        startTime = System.nanoTime();
        fifth(engSentencesWithAudioOutWithoutRepeat, langWordsTmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("fifth is ready " + duration + " sec");


        startTime = System.nanoTime();
        sixth(langWordsTmp, engSentencesWithAudioOutWithoutRepeat, engRusSentencesLinks, wordSentenceLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("sixth is ready " + duration + " sec");

        startTime = System.nanoTime();
        seventh(engSentencesWithAudioOutWithoutRepeat, rusSentencesWithAudioOut, wordSentenceLinks, engRusSentencesLinks, engSentences, rusSentences);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("seventh is ready " + duration + " sec");


        startTime = System.nanoTime();
        eighth(engRusSentencesLinks, wordSentenceLinks, langWordsTmp, langWords);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("eighth is ready " + duration + " sec");


        startTime = System.nanoTime();
        ninth(engSentences, rusSentences, audioLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("ninth is ready " + duration + " sec");

        startTime = System.nanoTime();
        tenth(engSentences, rusSentences, uniteSentence);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("tenth is ready " + duration + " sec");

    }

    private void tenth(File engSentences, File rusSentences, File uniteSentence) throws IOException {
        UniteSentences u = new UniteSentences();
        u.start(engSentences, rusSentences, uniteSentence);
    }

    private void ninth(File engSentences, File rusSentences, File audioLinks) throws IOException {
        MakeAudioLinkFile n = new MakeAudioLinkFile();
        n.start(engSentences,  rusSentences,  audioLinks);
    }

    private void eighth(File engRusSentencesLinks, File wordSentenceLinks, File langWordsTmp, File langWords) throws IOException {
        FilterOfWordsFile filterOfWordsFile = new FilterOfWordsFile();
        filterOfWordsFile.start(engRusSentencesLinks, wordSentenceLinks, langWordsTmp, langWords);
    }

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
        if (!checkLang()) {
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
        File dir1 = new File(devDir + "/tmp");
        File dir2 = new File(devDir + "/seeds_data");
        File dir3 = new File(devDir + "/seeds_data/" +lang+secondLang);
        dir1.mkdir();
        dir2.mkdir();
        dir3.mkdir();
    }
}
