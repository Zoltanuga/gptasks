import java.io.*;
import java.util.Arrays;

public class Task557 {

    public static void main(String[] args) throws IOException {
        int[][] matrC = null;
        int[][] matrA = null;
        int mxSize = 0;
        int iC = 0;
        int jC = 0;
        int matrixQuan = 0;
        BufferedReader br = new BufferedReader(new FileReader("d:\\statistic\\INPUT.TXT"));
        String currLine;
        int counter = 0;
        int prorata = 0;
        int indexCounter = 0;
        int[][] matrB = null;
        boolean isFirstMatr = true;
        while ((currLine = br.readLine()) != null) {
            String[] sData = currLine.split(" ");
            if (counter == 0) {
                matrixQuan = Integer.parseInt(sData[0]);
                mxSize = Integer.parseInt(sData[1]);
                matrA = new int[mxSize][mxSize];
                matrB = new int[mxSize][mxSize];
                matrC = new int[mxSize][mxSize];
            } else if (counter == 1) {
                iC = Integer.parseInt(sData[0]) - 1;
                jC = Integer.parseInt(sData[1]) - 1;
            } else if (counter == 2) {
                prorata = Integer.parseInt(currLine);
            } else {
                if (sData.length == 1 && sData[0].isEmpty()) {
                    indexCounter = 0;
                    if (counter > 3) {
                        isFirstMatr = false;
                        if (counter > 2 * mxSize + 2) {
                            multiply(iC, prorata, mxSize, matrA, matrB, matrC);
                            copy(iC, mxSize, matrC, matrA);
                        }
                    }
                    continue;
                }
                if (mxSize != 1) {
                    for (int i = 0; i < sData.length; i++) {
                        if (isFirstMatr) {
                            matrA[indexCounter][i] = Integer.parseInt(sData[i]);
                        } else {
                            matrB[i][indexCounter] = Integer.parseInt(sData[i]);
                        }
                    }
                } else {
                    if (isFirstMatr) {
                        matrA[indexCounter][indexCounter] = Integer.parseInt(sData[0]);
                    } else {
                        matrB[indexCounter][indexCounter] = Integer.parseInt(sData[0]);
                    }
                }
                System.out.println(Arrays.toString(sData));
                indexCounter += 1;
            }
            counter++;

        }
        if (matrixQuan > 1) {
            multiply(iC, prorata, mxSize, matrA, matrB, matrC);
        }

        BufferedWriter bW = new BufferedWriter(new FileWriter("D:\\statistic\\output.txt", false));
        if (matrixQuan == 1) {
            bW.write(String.valueOf(matrA[iC][jC]));
        } else {
            bW.write(String.valueOf(matrC[iC][jC]));
        }
        bW.close();
    }


    private static void copy(int iC, int mSize, int[][] matrC, int[][] matrA) {
        System.arraycopy(matrC[iC], 0, matrA[iC], 0, mSize);
        for (int j = 0; j < mSize; j++) {
            matrC[iC][j] = 0;
        }
    }

    private static void multiply(int iC, int prorata, int mSize, int[][] matrA, int[][] matrB, int[][] matrC) {
        for (int j = 0; j < mSize; j++) {
            for (int k = 0; k < mSize; k++) {
                matrC[iC][j] = matrC[iC][j] + matrA[iC][k] * matrB[j][k];
                if (matrC[iC][j] >= prorata) {
                    matrC[iC][j] = matrC[iC][j] % prorata;
                }
            }
        }
    }
}
