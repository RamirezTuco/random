package dp;

/*
 * Given coins of certain denominations and a total, 
 * 	how many ways these coins can be combined to get the total.
 */
public class CoinDenominationsForTotal {
	
	public static void main(String[] args) {
		int[] deno = new int[]{5, 2, 1};
		int num = 10;
		
		coinDenominationsForTotal(deno, num);
		coinDenominationsForTotalUsingDP(deno, num);
		
		deno = new int[]{20, 10, 100, 50, 5, 2, 1};
		num = 1000;
		
		coinDenominationsForTotal(deno, num);
		coinDenominationsForTotalUsingDP(deno, num);
		
	}
	
	private static int coinDenominationsForTotal(int[] deno, int sum){
		long t1 = System.currentTimeMillis();
		int result = coinDenominationsForTotal(deno, sum, 0);
		long t2 = System.currentTimeMillis();
		System.out.println("without DP, got result " + result + " in " + (t2-t1) + " ms.");
		
		return result;
	}
	
	private static int coinDenominationsForTotalUsingDP(int[] deno, int sum){
		long t1 = System.currentTimeMillis();
		
		int[][] cache = new int[sum+1][deno.length];
		for(int i = 0 ; i < sum+1 ; i++ ){
			for(int j = 0 ; j < deno.length ; j++){
				cache[i][j]=-1;
			}
		}
		
		int result = coinDenominationsForTotalUsingDP(deno, sum, 0, cache);
		long t2 = System.currentTimeMillis();
		System.out.println("with DP, got result " + result + " in " + (t2-t1) + " ms.");
		
		return result;
	}
	
	/*
	 * No of ways to reach 'sum' using 
	 * only the denominations starting from 'start' in deno.
	 * if deno is {5, 2, 1} and start is 1, and sum is 5
	 * the method returns num of ways to reach 5 using only 2, and 1.
	 */
	private static int coinDenominationsForTotal(int[] deno, int num, int start){
		// if we reach the end of the array, and we don't have any coin to consider.
		// or the sum to reach is less than 0, we return 0(no way to reach that)
		if(start > deno.length-1 || num < 0) {
			return 0;
		}
		
		// if we reach the end of the array, and we only have one denomination left, 
		// we find out the number of ways to divide num in the denomination.
		if(start == deno.length-1) {
			if(num % deno[start] == 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
		// initialize the no of ways to 0 and the i counter to 0.
		int i = 0; int ways = 0;
		
		// get the denomination.
		int val = deno[start];
		
		// below translates to 
		// 	No of ways to get to sum 10 = 
		//		   No of ways to get to 10 using 0 number of 5s(or any other deno)
		// 		+  No of ways to get to 10 using 1 number of 5s(or any other deno)
		//      +  No of ways to get to 10 using 2 number of 5s(or any other deno)
		
		while(num >= i*val){
			ways = ways + coinDenominationsForTotal(deno, num-i*val, start+1);
			i++;
		}
		
		// note that in the above 
		return ways;
	}
	
	
	private static int coinDenominationsForTotalUsingDP(int[] deno, int num, 
			int start, int[][] cache){
		
		if(start > deno.length-1 || num < 0) {
			// don't put in cache here, because the values are invalid.
			return 0;
		}
		
		if(start == deno.length-1) {
			if(num % deno[start] == 0) {
				// put in cache here.
				cache[num][start]=1;
				return 1;
			} else {
				// put in cache here
				cache[num][start]=0;
				return 0;
			}
		}
		
		int i = 0; int ways = 0;
		int val = deno[start];
		
		while(num >= i*val){
			// check the value. if it is -1, calculate the value
			int existingWays = cache[num-i*val][start+1];
			if(existingWays == -1){
				existingWays = coinDenominationsForTotalUsingDP(deno, num-i*val, start+1, cache);
			} 
			ways = ways + existingWays;
			i++;
		}
		
		// put in cache here.
		cache[num][start]=ways;
		return ways;
	}
}
