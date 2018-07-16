package com.oracle.bowling;

import java.util.Scanner;
import java.util.logging.Logger;

public class Bowling {
	static Logger logger = Logger.getLogger(Bowling.class.getName());

	private Scanner keyIn = new Scanner(System.in);

	private int frameCount = 0;

	private int frameLength = 10;

	private boolean lastStrike = false;
	private boolean lastSpare = false;

	private int[] scoreArray = new int[frameLength];
	private int[][] frameArray = new int[2][frameLength + 2];

	public Bowling() {

		for (int l1 = 0; l1 < frameLength; l1++)
			scoreArray[l1] = 0;
		for (int l2 = 0; l2 < frameArray[0].length; l2++)
			frameArray[0][l2] = frameArray[1][l2] = 0;
	}

	public void startGame() {
		for (int i = 0; i < frameLength; i++) {
			System.out.printf("\n\t   FRAME %2d ", i + 1);
			frameCount++;
			getPinsForBall1(i);
			getPinsForBall2(i);
		}
		checkForExtraBalls();
		startPointCalculation();
		startDisplayingPoints();
	}

	private void checkForExtraBalls() {
		if (frameCount < 12) {
			if (lastStrike) {
				lastStrike = false;
				System.out.printf("\n\t   Extra Frame %2d ", (frameCount - frameLength) + 1);
				getPinsForBall1(frameCount);
				getPinsForBall2(frameCount);
				frameCount++;
				checkForExtraBalls();

			} else if (lastSpare) {
				System.out.printf("\n\t   Extra Frame %2d ", 1);
				getPinsForBall1(frameCount++);
			}
		}
	}

	private void getPinsForBall1(int i) {
		System.out.printf("\n\t Ball 1: ");
		int pins = keyIn.nextInt();
		if (pins <= 10 && pins >= 0) {
			frameArray[0][i] = pins;
			if (pins == 10) {
				System.out.println("\n\t\t\tPerfect Strike Bingo!!");
				if (i >= 9)
					lastStrike = true;
			}

		} else {
			System.err.println("\n\t\t\tInvalid Input, Please give input from 0-10 !!");
			getPinsForBall1(i);
		}

	}

	private void getPinsForBall2(int i) {
		if (frameArray[0][i] == 10)
			return;
		System.out.print("\n\t Ball 2: ");
		int pins = keyIn.nextInt();
		if (pins <= 10 && pins >= 0 && pins + frameArray[0][i] < 11) {
			frameArray[1][i] = pins;
			if (pins + frameArray[0][i] == 10 && i < 10) {
				System.out.println("\n\t\t\tIts a Spare!!");
				if (i == 9)
					lastSpare = true;
			}
		} else {
			System.err.println("\n\t\t\tInvalid Input, Please give input from 0 - " + (10 - frameArray[0][i]) + "!!");
			getPinsForBall2(i);
		}
	}

	private void startPointCalculation() {

		for (int j = 0; j < frameLength; j++)
			calculate(BowlingUtil.getStatus(frameArray, 0, j), 0, j);

	}

	private void calculate(BowlingEnum e, int row, int col) {
		switch (e) {
		case STRIKE:
			setStrikeData(row, col);
			break;
		case SPARE:
			setSpareData(row, col);
			break;
		case OTHER:
			setOtherData(row, col);
			break;
		default:
			break;
		}
	}

	private void setOtherData(int row, int col) {
		if (col == 0)
			scoreArray[col] = frameArray[row][col] + frameArray[row + 1][col];
		else
			scoreArray[col] = scoreArray[col - 1] + frameArray[row][col] + frameArray[row + 1][col];

	}

	private void setSpareData(int row, int col) {
		if (col == 0)
			scoreArray[col] = 10 + frameArray[row][col + 1];
		else
			scoreArray[col] = scoreArray[col - 1] + 10 + frameArray[row][col + 1];

	}

	private void setStrikeData(int row, int col) {
		if (col == 0) {
			if (frameArray[row][col + 1] == 10) {
				scoreArray[col] = 20 + frameArray[0][col + 2];
			} else {
				scoreArray[col] = 10 + frameArray[row][col + 1] + frameArray[row + 1][col + 1];
			}
		} else {
			if (frameArray[row][col + 1] == 10) {
				scoreArray[col] = scoreArray[col - 1] + 20 + frameArray[0][col + 2];

			} else {
				scoreArray[col] = scoreArray[col - 1] + 10 + frameArray[row][col + 1] + frameArray[row + 1][col + 1];

			}
		}

	}

	private void startDisplayingPoints() {
		BowlingUtil.displayResult(frameArray, scoreArray, frameCount, frameLength);
	}

	public int getScore() {
		return scoreArray[9];
	}

}
