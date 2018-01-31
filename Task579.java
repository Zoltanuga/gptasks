import java.io.*;

public class Task579 {

    public static void main(String[] args) {
        int queueLength = 0;
        int[] array = null;
        int negSum = 0;
        int posSum = 0;
        StringBuilder positive = new StringBuilder();
        StringBuilder negative = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\statistic\\input.txt"))) {
            String lineL = br.readLine();
            String[] lineArr = br.readLine().split(" ");
            queueLength = Integer.parseInt(lineL);
            array = new int[queueLength];
            for (int i = 0; i < lineArr.length; i++) {
                array[i] = Integer.parseInt(lineArr[i]);
                if (array[i] > 0) {
                    posSum += array[i];
                    positive.append(i + 1).append(" ");
                } else if (array[i] < 0) {
                    negSum += array[i];
                    negative.append(i + 1).append(" ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\statistic\\output.txt", false))) {

            if (posSum > Math.abs(negSum)) {
                write(positive, bufferedWriter);
            } else if (posSum < Math.abs(negSum)) {
                write(negative, bufferedWriter);
            } else {
                if (posSum == negSum && posSum == 0) {
                    bufferedWriter.write("0");
                } else {
                    write(positive, bufferedWriter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(StringBuilder positive, BufferedWriter bufferedWriter) throws IOException {
        int length = positive.toString().trim().split(" ").length;
        bufferedWriter.write(String.valueOf(length));
        bufferedWriter.newLine();
        bufferedWriter.write(positive.toString());
    }
}
