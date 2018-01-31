import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Task670 {
    public static void main(String[] args) {
        final int limit = 100000;
        Integer n = 0;
        int counter = 0;
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("d:\\statistic\\INPUT.TXT"))) {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    n = Integer.parseInt(sCurrentLine);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int out = 0;
        for (Integer i = 1; i < limit; i++) {
            char[] c = i.toString().toCharArray();
            int[] noDuplicates = i.toString().chars().distinct().toArray();

            if (c.length == noDuplicates.length) {
                counter += 1;
            }
            if (counter == n) {
                out = i;
                break;
            }
        }


        try (FileWriter bufferedWriter = new FileWriter("d:\\statistic\\OUTPUT.TXT", false)) {
            bufferedWriter.write(String.valueOf(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(out);
    }
}
