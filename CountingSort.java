/*
 * @author Aaren Patel
 * I pledge my honor that I have abided by the Stevens Honor System
 * CS 284 B - HW 6
 * 4/24/2023
 */

public class CountingSort {
	public static void sort (int[] A) {
		/* Performs the counting sort */
		int max = 0;
		for (int i = 0; i < A.length; i++)
			if (max < A[i])
				max = A[i];
		int[] C = new int[max+1];
		for (int i = 0; i < max; i++)
			C[i] = 0;
		for (int i = 0; i < A.length; i++)
			C[A[i]]++;
		for (int i = 1; i < C.length; i++)
			C[i] += C[i-1];
		int[] B = new int[A.length];
		for (int i = A.length-1; i >= 0; i--) {
			B[C[A[i]]-1] = A[i];
			C[A[i]]--;
		}
		for (int i = 0; i < A.length; i++)
			A[i] = B[i];
	}
}
