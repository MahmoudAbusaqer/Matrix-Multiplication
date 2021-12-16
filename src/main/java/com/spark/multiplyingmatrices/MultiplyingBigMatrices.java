package com.spark.multiplyingmatrices;

import com.google.common.base.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

/**
 *
 * @author Mahmoud_Abusaqer
 */
public class MultiplyingBigMatrices {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Start measuring the execution time
        Stopwatch stopwatch = Stopwatch.createStarted();

        //Spark configuration
        SparkConf conf = new SparkConf();
        conf.setAppName("Test Spark");
        conf.setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //Creating the Matrix
        Random rand = new Random(); //instance of random class
        int upperbound = 10;
        List<int[]> myList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int[] arr = new int[1000];
            for (int j = 0; j < 1000; j++) {
                arr[j] = rand.nextInt(upperbound);
            }
            myList.add(arr);
        }

        int numberOfRows = myList.size();

        //Dividing the data to workers -> map function
        JavaRDD<int[]> disData = sc.parallelize(myList, 8);
        JavaRDD<List<Integer>> disResult = disData.map(new Function<int[], List<Integer>>() {
            @Override
            public List<Integer> call(int[] t1) throws Exception {
                List<Integer> map = new ArrayList<>();
                List<Integer> reduce = new ArrayList<>();
                //multiply two numbers from matrix 1 and the number from matrix 2
                for (int i = 0; i < t1.length; i++) {
                    for (int j = 0; j < myList.size(); j++) {
                        int num1 = myList.get(i)[j];
                        int num2 = t1[i];
                        map.add(num1 * num2);
                    }
                }

                //summing the numbers occur in the multiply process -> more of a reduce function
                for (int i = 0; i < numberOfRows; i++) {

                    int sum = 0;
                    for (int j = 0; j < map.size(); j += numberOfRows) {
                        sum += map.get(j + i);
                    }
                    reduce.add(sum);
                }
                return reduce;
            }
        });

        //Collecting the map values
        //Print the final Matrix 
        List<List<Integer>> results = disResult.collect();
        System.out.println("The Final Matrix");
        results.forEach((result) -> {
            System.out.println(result);
        });

        //Stop the stopwatch and print the execution time 
        stopwatch.stop();
        System.out.println("Time elapsed: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
