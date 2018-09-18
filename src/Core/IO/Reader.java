package Core.IO;

import java.util.Scanner;

public class Reader {
    public String ReadLine(){
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        return input;
    }
}
