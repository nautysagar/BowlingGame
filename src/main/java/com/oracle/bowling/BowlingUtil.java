package com.oracle.bowling;

public final class BowlingUtil {
	
	private BowlingUtil() {}
	
	public static void displayResult(int[][] frameArray,int[] scoreArray,int frameCount,int frameLength) {
		System.out.print("\n\n\t  FRAME");
		for (int k = 0; k < frameCount; k++) {
			if (k < frameLength)
				System.out.printf("%4d", k + 1);
			else
				System.out.print("  EXT");

		}

		System.out.print("\n\n\tBall 1 ");

		for (int l = 0; l < frameCount; l++)
			System.out.printf("%4d",frameArray[0][l]);

		System.out.print("\n\tBall 2 ");
		for (int i1 = 0; i1 < frameCount; i1++)
			System.out.printf("%4d", frameArray[1][i1]);

		System.out.print("\n\n\t  SCORE");
		for (int j1 = 0; j1 < 10; j1++)
			System.out.printf("%4d", scoreArray[j1]);
			System.out.printf("\n");
	}
	
	public static BowlingEnum getStatus(int[][] frameArray,int row, int col) {
		if (frameArray[row][col] == 10) {
			return BowlingEnum.STRIKE;
		} else if (frameArray[row][col] + frameArray[row + 1][col] == 10) {
			return BowlingEnum.SPARE;
		} else {
			return BowlingEnum.OTHER;
		}
	}


}
