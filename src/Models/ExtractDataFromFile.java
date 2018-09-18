package Models;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExtractDataFromFile {

    private List<Dictionary> dictionary;

    public ExtractDataFromFile(){
        this.dictionary = new ArrayList<>();
    }

    public List<Dictionary> InsertFromDictionaryIntoDataCategory(String path) throws Exception{
        String data = ReadFileAsString(path).substring(1);
        String[] dataArray = data.split("\r\n");

        Dictionary dictionary = null;

        for (int i = 0; i < dataArray.length; i++) {
            if (dataArray[i].charAt(0) == '_'){
                if (dictionary != null) {
                    this.dictionary.add(dictionary);
                }

                dictionary = new Dictionary();

                dictionary.setCategoryName(dataArray[i].substring(1));

                continue;
            }

            dictionary.AddWord(dataArray[i]);
        }

        this.dictionary.add(dictionary);

        return this.dictionary;
    }

    private String ReadFileAsString(String fileName)throws Exception{
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
