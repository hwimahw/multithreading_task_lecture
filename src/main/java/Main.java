import model.CalculateResult;
import model.DownloadResult;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Calendar start = Calendar.getInstance();

        int finishCounter = 0;
        for (int i = 0; i < 1000; i++) {

            Download d = new Download(i);
            DownloadResult downloadResult = d.downloadNext();

            Calculate c = new Calculate(downloadResult);
            CalculateResult calculateResult = c.calculate();

            if (calculateResult.found) {
                finishCounter++;
            }
        }

        System.out.println("Total success checks: " + finishCounter);

        Calendar stop = Calendar.getInstance();

        System.out.println("Total time: " + (stop.getTimeInMillis() - start.getTimeInMillis()) + " ms");

    }
}