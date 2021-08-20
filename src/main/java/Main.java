import model.CalculateResult;
import model.DownloadResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService downloadExecutorService = Executors.newFixedThreadPool(100);
        ExecutorService calculateExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<DownloadResult>> downloadRusults = new ArrayList<>();

        Calendar start = Calendar.getInstance();

        int finishCounter = 0;
        for (int i = 0; i < 1000; i++) {
            Download download = new Download(i);
            downloadRusults.add(downloadExecutorService.submit(download));
        }

        List<Future<CalculateResult>> futureCalculateResults = new ArrayList<>();

        for(Future<DownloadResult> downloadResultFuture : downloadRusults){
            Calculate calculateTask = new Calculate(downloadResultFuture.get());
            Future<CalculateResult> futureCalculateResult = calculateExecutorService.submit(calculateTask);
            futureCalculateResults.add(futureCalculateResult);
        }

        downloadExecutorService.shutdown();

        for(Future<CalculateResult> futureCalculateResult : futureCalculateResults){
            if(futureCalculateResult.get().found){
                finishCounter++;
            }
        }

        calculateExecutorService.shutdown();


        System.out.println("Total success checks: " + finishCounter);

        Calendar stop = Calendar.getInstance();

        System.out.println("Total time: " + (stop.getTimeInMillis() - start.getTimeInMillis()) + " ms");

    }
}