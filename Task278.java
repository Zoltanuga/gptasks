import java.io.*;

public class Task278 {
    public static void main(String[] args) {
        String s = "";
        String t = "";
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\statistic\\input.txt"))) {
            s = br.readLine();
            t = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        int n = s.length();
        int m = t.length();
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        boolean isPossible = true;
        if (m > 0 && n != 0) {
            if (n <= m) {
                int h = 0;
                for (int i = 0; i < m; i++) {
                    if (h == n) break;
                    if (sArr[h] == tArr[i]) {
                        h += 1;
                    }
                }
                if (h != n) {
                    isPossible = false;
                }
            } else {
                isPossible = false;
            }
        } else {
            if (!(n == 0 && m == 0)) {
                isPossible = false;
            }

        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\statistic\\output.txt", false))) {
            if (isPossible) {
                bufferedWriter.write("YES");
            } else {
                bufferedWriter.write("NO");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
