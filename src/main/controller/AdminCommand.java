package main.controller;

public class AdminCommand {
	public static boolean checkDice(String arg) {
		int num;
		try {
			num = Integer.parseInt(arg);
		} catch(Exception exception) {
			return false;
		}
		return checkNum(1, 6, num);
	}

	public static boolean checkPieceID(String arg) {
		int num;
		try {
			num = Integer.parseInt(arg);
		} catch(Exception exception) {
			return false;
		}
		return checkNum(0, 3, num);
	}


	public static boolean checkVertex(String arg) {
		int num = Integer.parseInt(arg);
		return checkNum(0, 71, num);
	}

	private static boolean checkNum(int min, int max, int num) {
		return num >= min && num <= max;
	}
}
