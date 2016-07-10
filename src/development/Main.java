package development;

import development.subclasses.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {


    /**
     * OPTIONS
     */

    //DO YOU HAVE FILE WITH WORDS OF LEARNING LANGUAGE? Please add language name to set


    private static String devDir = "src/development/files/";
//    String devDir = "src/test/files/";


    /******/

    /**
     * DO NO TOUCH!
     */

    public static int superWordCounter = 1;//start position
    public static int divider = 50000;//divider for end position
    private static File langWordsAll = new File(devDir + "/seeds_data/wordsTmp/wordsTmp.txt");
    private static Set<String> dontSetId = new HashSet<>();
    private static Map<String, File> mapSetId = new HashMap<>();

    public static String learningLang = "";
    public static String nativeLang = "";

    public static String[] languages = {"eng", "rus", "jpn"};
    //    public static String[] languages = {"eng", "epo", "tur", "ita", "rus", "deu", "fra", "spa", "por", "jpn", "hun", "heb", "ber", "pol", "mkd", "fin", "nld", "cmn", "mar", "ukr", "swe", "dan", "srp", "bul", "ces", "ina", "lat", "ara", "nds", "lit", "pes", "tlh", "jbo", "ell", "nob", "tgl", "tat", "isl", "toki", "ron"};
    private static boolean multiply = false;


    public static Set<String> allWords = new LinkedHashSet<>();
    public static Set<String> allSen = new LinkedHashSet<>();
    public static Set<String> allLinks = new LinkedHashSet<>();
    public static Set<String> allWordSenLinks = new LinkedHashSet<>();
    public static Set<Integer> allAudio = new LinkedHashSet<>();


    public static String splitSpace = " ";//split inside sentence (jpn and cnm = "")
    public static String separator = "\t";//row separator
    public static int minWordLength = 1;//jpn and cnm = 1 !


    public static boolean checkJpnCnmLang() {
        return "jpn".equals(learningLang) || "cnm".equals(learningLang);
    }

    /******/


    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.menu();
    }

    private boolean yesNo(String line) {
        boolean res = false;
        if ("n".equals(line)) {
            res = false;
        } else if ("y".equals(line)) {
            res = true;
        }

        return res;
    }

    private void menu() throws IOException {

        long startTime, endTime, duration;
        startTime = System.currentTimeMillis();


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello, please select options:");
        System.out.println("Do yo want to create all languages db? (Y/N)");

//        multiply = yesNo(br.readLine().toLowerCase()); TODO

        multiply = true;


        if (multiply) {
            System.out.println("Do you have file with words for your language? (Y/N)");
//            boolean answer = yesNo(br.readLine().toLowerCase()); TODO
            boolean answer = true;
            if (answer) {


//                String line = br.readLine(); TODO
                for (int i = 0; i < languages.length; i++) {

                    String path = "/home/sadedv/Desktop/enwordsFiles/langs/T/" + languages[i] + ".txt";

                    File file = new File(path);
                    if (file.exists() && !file.isDirectory()) {
                        if (!dontSetId.contains(languages[i])) {

                            mapSetId.put(languages[i], file);
                        }
                    }
                }
            }

            startSearch();


        } else {
            System.out.println("There are all languages");
            System.out.println(Arrays.toString(languages));
            System.out.println();
            System.out.println("What is your learning language? (3 letters):");
            learningLang = br.readLine();
            System.out.println("What is your native language? (3 letters):");
            nativeLang = br.readLine();
            System.out.println("Do you have file with words for your language? (Y/N)");
//            boolean answer  = yesNo(br.readLine().toLowerCase()); TODO
            boolean answer = true;

            if (answer) {
                System.out.println("Please enter path to file with words of learning language:");
                mapSetId.put(learningLang, new File(br.readLine())); //TODO
            }
            System.out.println();

            start();
        }

        endTime = System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("complete time is  " + duration + " min");

    }

    private void startSearch() throws IOException {
        for (int i = 0; i < languages.length; i++) {

            learningLang = languages[i];

            for (int j = 0; j < languages.length; j++) {
                if (i != j) {
                    nativeLang = languages[j];
                    start();
                    mapSetId.put(learningLang, langWordsAll);
                    System.out.println();
                    System.out.println("******************* " + learningLang + "-" + nativeLang + " is done *******************");
                    System.out.println();
                }
            }
        }

        System.out.println("last step started...");


        File li = new File(devDir + "/seeds_data/links.tsv");
        File se = new File(devDir + "/seeds_data/sentences.tsv");
        File wo = new File(devDir + "/seeds_data/words.tsv");
        File wose = new File(devDir + "/seeds_data/word_sentence.tsv");
        File a = new File(devDir + "/seeds_data/audio.tsv");

        Zero zero = new Zero();
        zero.collectionToFile(allWords, wo);
        zero.collectionToFile(allSen, se);
        zero.collectionToFile(allLinks, li);
        zero.collectionToFile(allWordSenLinks, wose);
        zero.collectionToFile(allAudio, a);

        System.out.println("last step ended...");


    }

    private void start() throws IOException {

        System.out.println("Wait few minutes...");

        String langLang = learningLang + "-" + nativeLang;

        File sentencesWithAudioIn = new File(devDir + "/sentences_with_audio.csv");
        File sentences = new File(devDir + "/sentences.csv");
        File originalLinks = new File(devDir + "/links.csv");

        String tmpDir = devDir + "/seeds_data/" + langLang + "/tmp/";
        File engLangSentencesTmp = new File(tmpDir + learningLang + "SentencesTmp.txt");
        File rusLangSentencesTmp = new File(tmpDir + nativeLang + "SentencesTmp.txt");
        File engSentencesWithAudioOut = new File(tmpDir + learningLang + "SentencesWithAudioColumnTmp.txt");
        File engSentencesWithAudioOutWithoutRepeat = new File(tmpDir + learningLang + "SentencesWithAudioColumnWithoutRepeat.txt");
        File engSentences = new File(tmpDir + learningLang + "Sentences.txt");
        File rusSentences = new File(tmpDir + nativeLang + "Sentences.txt");
        File langWords = new File(tmpDir + learningLang + "Words.txt");


        File engRusSentencesLinks = new File(devDir + "seeds_data/" + langLang + "/" + "links.tsv");
        File wordSentenceLinks = new File(devDir + "seeds_data/" + langLang + "/" + "word_sentence.tsv");
        File audioLinks = new File(devDir + "seeds_data/" + langLang + "/" + "audio.tsv");
        File uniteSentence = new File(devDir + "seeds_data/" + langLang + "/" + "sentences.tsv");
        File words = new File(devDir + "seeds_data/" + langLang + "/" + "words.tsv");


        long startTime, endTime, duration;

        long l = 1000000000L;
        makeDir(devDir, langLang);
        startTime = System.nanoTime();
        first(sentences, engLangSentencesTmp, learningLang);
        first(sentences, rusLangSentencesTmp, nativeLang);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("first is done " + duration + " sec");

        startTime = System.nanoTime();
        second(engLangSentencesTmp, sentencesWithAudioIn, engSentencesWithAudioOut);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("second is done " + duration + " sec");

        startTime = System.nanoTime();
        third(engSentencesWithAudioOut, engSentencesWithAudioOutWithoutRepeat);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("third is done " + duration + " sec");

        startTime = System.nanoTime();
        fourth(engSentencesWithAudioOutWithoutRepeat, rusLangSentencesTmp, originalLinks, engRusSentencesLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("fourth is done " + duration + " sec");

        startTime = System.nanoTime();
        fifth(engSentencesWithAudioOutWithoutRepeat, langWordsAll);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("fifth is done " + duration + " sec");


        startTime = System.nanoTime();
        sixth(langWordsAll, engSentencesWithAudioOutWithoutRepeat, engRusSentencesLinks, wordSentenceLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("sixth is done " + duration + " sec");

        startTime = System.nanoTime();
        seventh(engSentencesWithAudioOutWithoutRepeat, rusLangSentencesTmp, wordSentenceLinks, engRusSentencesLinks, engSentences, rusSentences);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("seventh is done " + duration + " sec");


        startTime = System.nanoTime();
        eighth(engRusSentencesLinks, wordSentenceLinks, langWordsAll, langWords);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("eighth is done " + duration + " sec");


        startTime = System.nanoTime();
        ninth(engSentences, audioLinks);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("ninth is done " + duration + " sec");

        startTime = System.nanoTime();
        tenth(engSentences, rusSentences, uniteSentence);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("tenth is done " + duration + " sec");

        startTime = System.nanoTime();
        eleventh(langWordsAll, words);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / l;
        System.out.println("eleventh is done " + duration + " sec");

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
        if (mapSetId.containsKey(learningLang) && !dontSetId.contains(learningLang)) {
            WordCount wordCount = new WordCount();
            wordCount.setIdsOfYourWords(mapSetId.get(learningLang), langWordsTmp);
            dontSetId.add(learningLang);
            mapSetId.remove(learningLang);
        } else if (!mapSetId.containsKey(learningLang) && !dontSetId.contains(learningLang)) {
            WordCount wordCount = new WordCount();
            wordCount.start(langSentences, langWordsTmp);
            dontSetId.add(learningLang);
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

    private void makeDir(String devDir, String langLang) {
        File dir2 = new File(devDir + "/seeds_data");
        File dir1 = new File(devDir + "/seeds_data/wordsTmp");
        File dir3 = new File(devDir + "/seeds_data/" + langLang);
        File dir4 = new File(devDir + "/seeds_data/" + langLang + "/tmp");
        dir2.mkdir();
        dir1.mkdir();
        dir3.mkdir();
        dir4.mkdir();
    }
}
