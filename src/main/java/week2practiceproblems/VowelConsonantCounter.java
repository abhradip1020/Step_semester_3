import java.util.Scanner;

public class VowelConsonantCounter {

    void countVowelsAndConsonants(String text) {

        int vowels = 0;
        int consonants = 0;

        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);
\
    
            if (ch == ' ') {
                continue;
            }
            ch = Character.toLowerCase(ch);
            if (ch == 'a' || ch == 'e' || ch == 'i'
                    || ch == 'o' || ch == 'u') {
                vowels++;
            } else {
                consonants++;
            }
        }

        System.out.println("Vowels: " + vowels
                + " | Consonants: " + consonants);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a title: ");
        String text = sc.nextLine();

        VowelConsonantCounter obj = new VowelConsonantCounter();
        obj.countVowelsAndConsonants(text);

        sc.close();
    }
}
