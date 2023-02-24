package game.tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private static char[][] grid;

    private static final char[] winConditionX = {'X', 'X', 'X'};
    private static final char[] winConditionO = {'O', 'O', 'O'};

    public static void playGame() {
        grid = new char[3][3];
        for (char[] chars : grid) Arrays.fill(chars, ' ');
        drawBoard();
        input();
    }

    private static void drawBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < grid.length; j++)
                System.out.print(grid[i][j] + " ");
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static void playMove(int x, int y, char op) {
        grid[x - 1][y - 1] = op;
        drawBoard();
    }

    public static void input() {
        Scanner sc = new Scanner(System.in);
        int xCor, yCor;
        int moves = 1;
        boolean flag = false;
        while (!flag) {
            while (true) {
                try {
                    xCor = sc.nextInt();
                    yCor = sc.nextInt();
                    if (xCor > 3 || yCor > 3) {
                        System.out.println("Coordinates should be from 1 to 3!");
                    } else if (grid[xCor - 1][yCor - 1] != ' ') {
                        System.out.println("This cell is occupied! Choose another one!");
                    } else if (grid[xCor - 1][yCor - 1] == ' ') {
                        if (moves % 2 != 0) {
                            playMove(xCor, yCor, 'X');
                        } else {
                            playMove(xCor, yCor, 'O');
                        }
                        moves++;
                    }
                    if (wins() == 1) {
                        System.out.println("X wins");
                        flag = true;
                        break;
                    } else if (wins() == 0) {
                        System.out.println("O wins");
                        flag = true;
                        break;
                    } else if (drawCondition()) {
                        System.out.println("Draw");
                        flag = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("You should enter numbers!");
                    sc.next();
                }
            }
        }
    }

    public static boolean drawCondition() {
        if (isUnplayable() || hasEmptyCells() || (Math.abs(countO() - countX()) >= 2) || (countX() + countO() != 9))         // It isn't a draw if it has empty cells
            return false;

        for (int i = 0; i < 3; i++)            // Rows checked
            if (Arrays.equals(grid[i], winConditionX) || Arrays.equals(grid[i], winConditionO))
                return false;

        char[][] diagonal = diagonalMat();
        for (char[] chars : diagonal)      // Diagonal checked
            if (Arrays.equals(chars, winConditionX) || Arrays.equals(chars, winConditionO))
                return false;

        char[][] colMat = colMat();
        for (char[] chars : colMat)
            if (Arrays.equals(chars, winConditionX) || Arrays.equals(chars, winConditionO))
                return false;

        return true;
    }

    public static int wins() {
        if (isUnplayable())
            return -1;
        int xWins = 0, oWins = 0;
        for (char[] value : grid) {            // checking row wise
            if (Arrays.equals(value, winConditionX))
                xWins++;
            else if (Arrays.equals(value, winConditionO))
                oWins++;
        }
        if (xWins == 1 && oWins == 0)
            return 1;
        else if (xWins == 0 && oWins == 1)
            return 0;

        xWins = 0;
        oWins = 0;
        char[][] colMat = colMat();
        for (char[] value : colMat) {            // checking column wise
            if (Arrays.equals(value, winConditionX))
                xWins++;
            else if (Arrays.equals(value, winConditionO))
                oWins++;
        }
        if (xWins == 1 && oWins == 0)
            return 1;
        else if (xWins == 0 && oWins == 1)
            return 0;

        xWins = 0;
        oWins = 0;
        char[][] diagonal = diagonalMat();
        for (char[] chars : diagonal) {            // checking diagonal wise
            if (Arrays.equals(chars, winConditionX))
                xWins++;
            else if (Arrays.equals(chars, winConditionO))
                oWins++;
        }

        if (xWins == 1 && oWins == 0)
            return 1;
        else if (xWins == 0 && oWins == 1)
            return 0;
        return -1;
    }

    public static boolean gameNotFinished() {
        if (!hasEmptyCells() || isUnplayable())
            return false;
        for (char[] chars : grid)
            if (Arrays.equals(chars, winConditionX) || Arrays.equals(chars, winConditionO))
                return false;

        char[][] diagonal = diagonalMat();
        for (char[] chars : diagonal)         // Diagonal checked
            if (Arrays.equals(chars, winConditionX) || Arrays.equals(chars, winConditionO))
                return false;

        char[][] colMat = colMat();
        for (char[] chars : colMat)
            if (Arrays.equals(chars, winConditionX) || Arrays.equals(chars, winConditionO))
                return false;

        return true;
    }


    public static boolean isUnplayable() {              // is in an impossible state
        if ((Math.abs(countO() - countX()) >= 2) && (countX() + countO() != 9))
            return true;
        int xWins = 0, oWins = 0;
        for (char[] value : grid) {            // checking row wise
            if (Arrays.equals(value, winConditionX))
                xWins++;
            else if (Arrays.equals(value, winConditionO))
                oWins++;
        }
        if (xWins >= 1 && oWins >= 1)
            return true;

        xWins = 0;
        oWins = 0;
        char[][] colMat = colMat();
        for (int i = 0; i < 3; i++) {            // checking column wise
            if (Arrays.equals(colMat[i], winConditionX))
                xWins++;
            else if (Arrays.equals(colMat[i], winConditionO))
                oWins++;
        }
        if (xWins >= 1 && oWins >= 1)
            return true;

        xWins = 0;
        oWins = 0;
        char[][] diagonal = diagonalMat();
        for (char[] chars : diagonal) {            // checking diagonal wise
            if (Arrays.equals(chars, winConditionX))
                xWins++;
            else if (Arrays.equals(chars, winConditionO))
                oWins++;
        }
        return xWins >= 1 && oWins >= 1;
    }

    public static char[][] diagonalMat() {
        char[] diag1 = {grid[0][0], grid[1][1], grid[2][2]};
        char[] diag2 = {grid[0][2], grid[1][1], grid[2][0]};
        return new char[][]{diag1, diag2};
    }

    public static char[][] colMat() {
        char[] col1 = {grid[0][0], grid[1][0], grid[2][0]};
        char[] col2 = {grid[0][1], grid[1][1], grid[2][1]};
        char[] col3 = {grid[0][2], grid[1][2], grid[2][2]};
        return new char[][]{col1, col2, col3};
    }

    public static int countX() {
        int xs = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i][j] == 'X')
                    xs++;
        return xs;
    }

    public static int countO() {
        int os = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i][j] == 'O')
                    os++;
        return os;
    }

    public static boolean hasEmptyCells() {
        for (char[] chars : grid)
            for (int j = 0; j < grid.length; j++)
                if (chars[j] == '_' || chars[j] == ' ')
                    return true;
        return false;
    }

}
