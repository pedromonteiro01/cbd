package cbd.redis;


import java.util.*;
import java.io.*;

public class AutoComplete {
    public static void main(String [] args) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        PostSet set = new PostSet();

        File file = new File("names.txt");

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String name = sc.nextLine();
            set.saveUser(name);
        }
        sc.close();

        Scanner sc2 = new Scanner(System.in);

        while(true) {
            System.out.print("Search for ('Enter' for quit): ");
            String input = sc2.nextLine();


            if (input.length() != 0) {
                Set<String> answer = set.getUser(input);
                for (String str : answer)
                    System.out.println(str);
            } else {
                System.err.println("Invalid Input");
                break;
            }
        }
        sc2.close();
    }


}
