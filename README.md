# COP4520 Programming Assignment 1

To run the code:

In the command line, navigate to the directory containing the files, then compile and run the .java file.

```
$ javac primes.java
$ java primes
```

The approach uses the Sieve of Eratosthenes method to find prime numbers. This way, the amount of time it takes to find which numbers are prime speeds up rather than using other methods such as looping through each number and seeing if it has any factors. 

With this method, we create an array that stores if the value of that index is prime or not. First, each value is initialized to being prime. We then update all multiples of this number that are the greater than or equal to its square, as not being prime. We will only do this process for numbers currently marked as prime.

As we loop through this array, more and more numbers will be marked as not being prime, and the number of "false primes" to mark lessens at each iteration. At the end of the loop, only prime numbers will remain. This has a runtime of O(n log log n). Each thread will have a different number, stored in a variable n, that it is called with. The threads each find every prime number up to n (n is different for every thread). 

For threads with higher numbers of n, the lower numbers will have been found previously by other threads, so the checks for those numbers will not be executed again. Thus, with the threads running concurrently, multiple prime numbers are found at the same time, reducing the total runtime. Each different will find different prime numbers and run concurrently.