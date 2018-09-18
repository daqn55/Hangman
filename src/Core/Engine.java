package Core;

import Constants.Messages;
import Models.ExtractDataFromFile;
import Core.IO.Reader;
import Core.IO.Writer;
import Models.Dictionary;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private Reader reader;
    private Writer writer;
    private Messages messages;
    private ExtractDataFromFile extractDataFromFile;

    private List<Dictionary> dataCategories;

    public Engine(){
        this.reader = new Reader();
        this.writer = new Writer();
        this.messages = new Messages();
        this.dataCategories = new ArrayList<>();
        this.extractDataFromFile = new ExtractDataFromFile();
    }

    public void Run() {

        try {
            this.dataCategories = this.extractDataFromFile.InsertFromDictionaryIntoDataCategory(this.messages.dictionaryPath);

            boolean isGameOver = false;
            int score = -1;

            while (true) {
                if (isGameOver) {
                    break;
                } else {
                    score++;
                }

                if (score > 0) {
                    this.writer.WriteLine(this.messages.currentScore + score);
                }
                this.writer.WriteLine(this.messages.chooseCategory);

                this.writer.WriteLine(ExtractAllCategories());

                this.writer.Write("> ");
                String inputCategory = reader.ReadLine();

                String word = GetRandomWordFromCategory(inputCategory);

                if (word.equals("")) {
                    this.writer.WriteLine(this.messages.invalidCategory);
                    score--;
                    continue;
                }

                /*this.writer.WriteLine(word);*/

                char[] wordArrWithCapitalLetters = word.toCharArray();
                char[] wordArr = word.toLowerCase().toCharArray();
                char[] hiddenWordArr = word.replaceAll("[^ ]", "_").toCharArray();


                int attemptsLeft = 10;
                this.writer.WriteLine(this.messages.attempts + attemptsLeft);

                StringBuilder hiddenWordWithSpaces = new StringBuilder();
                for (char i : hiddenWordArr) {
                    hiddenWordWithSpaces.append(i);
                    hiddenWordWithSpaces.append(" ");
                }
                this.writer.WriteLine(this.messages.currentWord + hiddenWordWithSpaces.toString());

                List<Character> usedLetters = new ArrayList<>();
                int numberOfWordsThatAreFound = 0;
                while (true) {

                    this.writer.WriteLine(this.messages.enterLetter);
                    this.writer.Write("> ");
                    char[] letterInput = reader.ReadLine().toLowerCase().toCharArray();

                    if (letterInput.length > 1 || letterInput.length == 0){
                        this.writer.WriteLine(this.messages.invalidLetter);
                        continue;
                    }

                    if (usedLetters.contains(letterInput[0])){
                        this.writer.WriteLine(this.messages.usedLetter);
                        continue;
                    }
                    else{
                        usedLetters.add(letterInput[0]);
                    }

                    boolean containsLetter = false;
                    int[] letterPositions = new int[hiddenWordArr.length];
                    for (int i = 0; i < wordArr.length; i++) {
                        if (wordArr[i] == letterInput[0]) {
                            letterPositions[i] = 1;
                            containsLetter = true;
                        }
                    }

                    if (containsLetter) {
                        for (int i = 0; i < hiddenWordArr.length; i++) {
                            if (letterPositions[i] == 1) {
                                hiddenWordArr[i] = wordArrWithCapitalLetters[i];
                                numberOfWordsThatAreFound++;
                            }
                        }
                    } else {
                        this.writer.WriteLine(this.messages.doesntHaveLetter);
                        attemptsLeft--;
                    }

                    StringBuilder sb = new StringBuilder();
                    for (char i : hiddenWordArr) {
                        sb.append(i);
                        sb.append(" ");
                    }

                    if (word.replaceAll("\\s+", "").length() == numberOfWordsThatAreFound) {
                        this.writer.WriteLine(this.messages.fullWord);
                        this.writer.WriteLine(sb.toString());
                        break;
                    }

                    this.writer.WriteLine(this.messages.attempts + attemptsLeft);
                    this.writer.WriteLine(this.messages.currentWord + sb.toString());

                    if (attemptsLeft == 0) {
                        isGameOver = true;
                        this.writer.WriteLine(this.messages.gameOver);

                        break;
                    }
                }
            }
        } catch (Exception e) {
            this.writer.WriteLine(e.getMessage());
        }

    }

    private String ExtractAllCategories(){
        StringBuilder sb = new StringBuilder();

        for (Dictionary dataCat : this.dataCategories) {
            sb.append(dataCat.getCategoryName());
            sb.append("\n\r");
        }

        return sb.toString().trim();
    }

    private String GetRandomWordFromCategory(String inputCategoryName){
        boolean isCategoryExist = false;

        for (Dictionary data: this.dataCategories) {
            if (data.getCategoryName().toLowerCase().equals(inputCategoryName.toLowerCase())){
                isCategoryExist = true;
                break;
            }
        }

        String word = "";

        if (isCategoryExist) {
            for (Dictionary dataCat : dataCategories) {
                if (dataCat.getCategoryName().toLowerCase().equals(inputCategoryName.toLowerCase())) {
                    word = dataCat.RandomWord();

                    break;
                }
            }
        }
        return  word;
    }
}

