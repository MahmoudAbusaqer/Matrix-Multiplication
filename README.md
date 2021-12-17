# Matrix-Multiplication

Big matrix multiplication over Apache Spark in Java to perform a MapReduce task. 

MultiplyingMatrices file containing 4 * 4 matrix and MultiplyingBigMatrices containing 1000 * 1000 matrix.

For the time performance of using a gradual number of workers, I got the following result on my laptop: 

4 * 4 Matrix

1 -> Time elapsed: 9266

2 -> Time elapsed: 9831

4 ->  Time elapsed: 9649

8 -> Time elapsed: 8772

16 -> Time elapsed: 9610

1000 * 1000 Matrix

1 -> Time elapsed: 33833

2 -> Time elapsed: 39516

4 ->  Time elapsed: 40746

8 -> Time elapsed: 33511

16 -> Time elapsed: 38526
