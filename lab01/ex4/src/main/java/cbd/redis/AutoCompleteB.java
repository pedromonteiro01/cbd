package cbd.redis;

import java.util.Scanner;
import java.util.Set;
import java.io.*;

public class AutoCompleteB {
    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        SetCsv csv = new SetCsv();

        Scanner sc3 = new Scanner(System.in);

        //https://stackoverflow.com/questions/20841980/read-a-file-and-split-lines-in-java
        try(BufferedReader in = new BufferedReader(new FileReader("nomes-pt-2021.csv"))){
            String line;
            while((line = in.readLine())!=null){
                String[] pair = line.split(";");
                csv.saveUser(pair[0], Integer.parseInt(pair[1]));
            }
        } catch (FileNotFoundException e) { // handling FileNotFoundException
            System.err.println("File not found. Maybe wrong directory?");
            System.exit(-1);
        }

        while(true) {
            System.out.print("Search for ('Enter' for quit): ");
            String input = sc3.nextLine();

            if(input.length() != 0) {
                Set<String> set = csv.getUserSet();
                for (String s : set)
                    if (s.toLowerCase().matches(input + "(.*)")) // words that match input
                        System.out.println(s);
            } else{
                System.err.println("Invalid input.");
                break;
            }
        }
        sc3.close();
    }
}
