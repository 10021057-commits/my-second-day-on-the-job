import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedCallableDemo {

    public static void main(String[] args) {
        long targetLimit = 1_000_000_000L;
        int numThreads = 1000;
        long rangePerThread = targetLimit / numThreads;

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Long>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            final long start = i * rangePerThread + 1;
            final long end = (i == numThreads - 1) ? targetLimit : (i + 1) * rangePerThread;

            Callable<Long> task = () -> {
                long count = 0;
                for (long j = start; j <= end; j++) {
                    count++;
                }
                return count;
            };

            futures.add(executor.submit(task));
        }

        long totalCount = 0;
        for (Future<Long> future : futures) {
            try {
                totalCount += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        System.out.println("Total numbers counted between 1 and 1,000,000,000: " + totalCount);
System.out.println("Timer branch complete");    
}
}