import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class TestQuickSort
{
    public static final int MAX_ARRAY_SIZE = 10000000;
    public static final int MIN_ARRAY_SIZE = 20000;
    public static final int INTERVALS = 20;
    public static final int DELTA = ((MAX_ARRAY_SIZE - MIN_ARRAY_SIZE) / INTERVALS);

    public static final int MIN_REC_LIMIT = 2;
    public static final int MAX_REC_LIMIT = 300;
    public Random randIntGen;
    public

    /**
     * Constructs a TestQuickSort Object
     */
    TestQuickSort()
    {
        randIntGen = new Random();
    }

    /**
     * Times how long the quick sort of a given array
     * takes using the given recursion limit
     * @param array the array of ints
     * @param reclimit the recursion limit
     * @return the time the sort took in seconds
     */
    public double timeQuickSort(Integer[] array, int reclimit)
    {
        double startTime = System.nanoTime();
        FHsort.setRecursionLimit(reclimit);
        FHsort.quickSort(array);
        double endTime = System.nanoTime();
        return (endTime - startTime) / 1e9;
    }

    public Integer[] generateRandomArray(int size, int maxNum)
    {
        Integer[] retArray = new Integer[size];
        for (int i = 0; i < size; i ++)
        {
            retArray[i] = randIntGen.nextInt(maxNum);
        }
        return retArray;
    }

    public void writeToCSV(String filename, String line)
    {
        try
        {
            PrintWriter writer = new PrintWriter(new FileOutputStream(filename, true));
            line.replaceAll(", $", "");
            writer.println(line);
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.err.println();
        }
    }

    public void clearFileContents(String filename)
    {
        try { new PrintWriter(filename).close(); }
        catch (FileNotFoundException e){ ; }
        return;
    }

    public static void main(String args[])
    {
        final String FILENAME = "RunTimes.csv";
        TestQuickSort test = new TestQuickSort();
        test.clearFileContents(FILENAME);

        for (int i = MIN_ARRAY_SIZE; i <= MAX_ARRAY_SIZE; i+=DELTA)
        {
            Integer[] array = test.generateRandomArray(i, 1000);
            StringBuilder builder = new StringBuilder();
            System.out.println("Current Array Size: " + i + " elements\n");
            for (int j = MIN_REC_LIMIT; j <= MAX_REC_LIMIT; j+=2)
            {
                double totalElapsed = 0;
                for (int k = 0; k < 3; k++)
                {
                    totalElapsed += test.timeQuickSort(array.clone(), j);
                }
                double avgElapsed = totalElapsed / 3;
                System.out.println("Current Recursion Limit: " + j);
                System.out.println("Time elapsed: " + avgElapsed + " seconds");
                builder.append(avgElapsed + ",");
            }
            test.writeToCSV(FILENAME, builder.toString());
        }
    }
}
