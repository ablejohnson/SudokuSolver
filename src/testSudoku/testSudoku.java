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
package testSudoku;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import sudokusolver.Sudoku;
import sudokusolver.SudokuException;

/**
 *
 * @author Able Johnson
 * @Email able@live.in
 */
public class testSudoku {

   static String[] fileNames = {"puzzle_hard.txt", "puzzle1.txt", "puzzle2.txt","puzzle4.txt"};
   //static String[] fileNames = {"puzzle1.txt"};
    public static void main(String[] args) {
        for (String fileName : fileNames) {
            
            Sudoku sudoku = new Sudoku();
            try {
                System.out.println("=======================" + fileName + "==============================");
                sudoku.getPuzzle((new File(".").getCanonicalPath())+"/src/testSudoku/"+fileName);
                //sudoku.fillPossibles();
                //sudoku.printPossibilities();
                sudoku.solve();
                sudoku.printPuzzle();
           } catch (SudokuException ex) {
                System.out.println(ex.getMessage());
                System.out.println("--------Current Status-----");
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        System.out.print(ex.getSudoku()[i][j]);
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                 ex.printPossibilities();
            } catch (Exception ex) {
                Logger.getLogger(testSudoku.class.getName()).log(Level.SEVERE, null, ex);
                sudoku.fillPossibles();
            }
        }
    }

}
