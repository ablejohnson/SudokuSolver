/*
 * Copyright (C) 2015 Able Johnson
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sudokusolver;

import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Able Johnson
 * @Email able@live.in
 */
public class Sudoku {

    int[][] sudoku;
    TreeSet<Integer>[][] possibleValues;
    String data;
    int solvedCells;
    boolean changed = true;

    public Sudoku() {
        this.solvedCells = 0;
        sudoku = new int[9][9];
        possibleValues = new TreeSet[9][];
        for (int i = 0; i < 9; i++) {
            possibleValues[i] = new TreeSet[9];
            for (int j = 0; j < 9; j++) {
                possibleValues[i][j] = new TreeSet<>();
            }
        }
    }

    private boolean isCompleate() {
        return (solvedCells == 81);
    }

    public void solvePuzzle() throws Exception, SudokuException {
        fillPossibles();
        while (!isCompleate() && changed) {
            changed = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (sudoku[i][j] != 0) {
                        continue;
                    }
                    if (possibleValues[i][j].isEmpty()) {
                        throw new Exception("The Puzzle is Invalid");
                    }
                    if (possibleValues[i][j].size() == 1) {
                        sudoku[i][j] = possibleValues[i][j].first();
                        possibleValues[i][j].clear(); //remove this line for more performance but more memory
                        removePossibleValue(i, j, sudoku[i][j]);
                        changed = true;
                        solvedCells++;
                        //System.out.println("----------------------single possibility--------------------");
                        //printPuzzle();
                    } else {
                        TreeSet<Integer> tempPossible = new TreeSet<>();
                        tempPossible.addAll(possibleValues[i][j]);
                        for (Integer k : tempPossible) {
                            if (isPossibleHereOnly(i, j, k)) {
                                sudoku[i][j] = k;
                                possibleValues[i][j].clear(); //remove this line for more performance but more memory
                                removePossibleValue(i, j, k);
                                changed = true;
                                solvedCells++;
                                // System.out.println("---------------------hereonly---------------------");
                                //printPuzzle();
                                break;
                            }
                        }

                    }

                }
            }

        }
        if (solvedCells < 81) {
            //ToDo Need a better methode to solve this puzzle or this puzzle has more than 
            SudokuException e=new SudokuException("The puzzle need to be solved using BackTracking");
            e.setSudoku(sudoku);
            e.setSolvedCells(solvedCells);
            throw e;
        }

    }

    private boolean isPossibleHereOnly(int x, int y, int num) {
        int xSection = x / 3;
        int ySection = y / 3;
        int found = 0;

        for (int row = 0; row < possibleValues.length && found < 2; row++) {
            if (possibleValues[row][y].contains(num)) {
                found++;
            }
        }
        if (found == 1) {
            return true;
        }
        found = 0;
        for (int col = 0; col < possibleValues.length && found < 2; col++) {
            if (possibleValues[x][col].contains(num)) {
                found++;
            }
        }
        if (found == 1) {
            return true;
        }
        found = 0;
        for (int box = 3 * xSection; (box < 3 * xSection + 3) && (found < 2); box++) {
            for (int boxY = 3 * ySection; boxY < 3 * ySection + 3; boxY++) {
                if (possibleValues[box][boxY].contains(num)) {
                    found++;
                }
            }

        }
        return (found == 1);
    }

    private void removePossibleValue(int x, int y, int num) {
        int xSection = x / 3;
        int ySection = y / 3;

        for (Set<Integer>[] possibleValue : possibleValues) {
            possibleValue[y].remove(num);
        }
        for (int col = 0; col < possibleValues.length; col++) {
            possibleValues[x][col].remove(num);
        }

        for (int box = 3 * xSection; box < 3 * xSection + 3; box++) {
            for (int boxY = 3 * ySection; boxY < 3 * ySection + 3; boxY++) {
                possibleValues[box][boxY].remove(num);
            }

        }
    }

    public void getPuzzle(String fileName) {
        try {
            try (Scanner in = new Scanner(new File(fileName))) {
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        data = in.next();
                        if (data.equals(".")) {
                            sudoku[x][y] = 0;
                        } else {
                            sudoku[x][y] = Integer.parseInt(data);
                            solvedCells++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    public void printPuzzle() {

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.print(sudoku[x][y]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private boolean isValid(int x, int y, int num) {
        int xSection = x / 3;
        int ySection = y / 3;

        for (int row = 0; row < sudoku.length; row++) {
            if (sudoku[row][y] == num) {
                return false;
            }
        }
        for (int col = 0; col < sudoku.length; col++) {
            if (sudoku[x][col] == num) {
                return false;
            }
        }

        for (int box = 3 * xSection; box < 3 * xSection + 3; box++) {
            for (int boxY = 3 * ySection; boxY < 3 * ySection + 3; boxY++) {
                if (sudoku[box][boxY] == num) {
                    return false;
                }
            }

        }
        return true;
    }

    private int xPosition(int x, int y) {
        if (x < 8) {
            return x + 1;
        } else {
            return 0;
        }
    }

    private int yPosition(int x, int y) {
        if (x < 8) {
            return y;
        } else {
            return y + 1;
        }
    }

    /* private boolean solvePuzzle(int x, int y) {
     if (x >= 9 || y >= 9) {

     return true;
     }

     else {
     for (int num = 1; num < 10; num++) {
     if (isValid(x, y, num)) {
     sudoku[x][y].value = num;
     if (solvePuzzle(xPosition(x, y), yPosition(x, y))) {
     return true;
     } else {
     sudoku[x][y].value = 0;
     }

     }


     }
     return false;
     }


     }*/
    public void fillPossibles() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                possibleValues[i][j].clear();
                if (sudoku[i][j] == 0) {
                    for (int k = 1; k < 10; k++) {
                        if (isValid(i, j, k)) {
                            possibleValues[i][j].add(k);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
    }

}
