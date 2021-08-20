import model.CalculateResult;
import model.DownloadResult;

import java.util.Random;

public class Calculate {

    private final DownloadResult downloadResult;

    public Calculate(DownloadResult downloadResult) {
        this.downloadResult = downloadResult;
    }


    public CalculateResult calculate() {
        Random r = new Random();
        CalculateResult result = new CalculateResult();
        result.id = downloadResult.id;

        int check;
        do {
            check = r.nextInt(2000000);
        } while (!downloadResult.check(check));

        result.found = true;

        return result;
    }
}