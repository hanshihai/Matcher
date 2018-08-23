import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Matcher {

    private static List<String> patterns = new ArrayList<>();

    public boolean match(String arg) {
        return patterns.stream().anyMatch(p -> {
            Pattern pattern = Pattern.compile(p);
            java.util.regex.Matcher matcher = pattern.matcher(arg);
            return matcher.matches();
        });
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        patterns.stream().forEach(pattern -> buffer.append(pattern + "; "));
        return buffer.toString();
    }

    public Matcher(String configPath) throws Exception {
        try(BufferedReader reader = new BufferedReader(new FileReader(configPath))){
            reader.lines().filter(pattern -> pattern != null && !pattern.isEmpty()).forEach(patten -> patterns.add(patten));
        }
    }

    public static void main(String[] args) throws Exception {
        Matcher matcher = new Matcher(args[0]);
        System.out.println("the patterns : " + matcher.toString());

        System.out.println("Please input os to check :");
        Scanner scaner = new Scanner(System.in);
        while(scaner.hasNextLine()){
            String input = scaner.nextLine();
            if ("q".equals(input.trim()) || "quit".equals(input.trim()) || "exit".equals(input.trim())) {
                break;
            }
            System.out.println("The result is " + matcher.match(input) + " for " + input);
        }
    }
}
