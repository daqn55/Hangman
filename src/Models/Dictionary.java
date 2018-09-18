package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dictionary {

    public Dictionary(){
        this.words = new ArrayList<>();
    }

    private String categoryName;

    private List<String> words;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getWords() {
        return words;
    }

    public void AddWord(String word){
        this.words.add(word);
    }

    public String RandomWord(){
        int rnd = new Random().nextInt(this.words.size());

        return this.words.get(rnd);
    }
}
