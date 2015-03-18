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

/**
 *
 * @author Able Johnson
 * @Email able@live.in
 */
public class SudokuException extends Throwable {
    int[][] sudoku;
    int SolvedCells;

    public int[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public int getSolvedCells() {
        return SolvedCells;
    }

    public void setSolvedCells(int SolvedCells) {
        this.SolvedCells = SolvedCells;
    }
    public SudokuException(String msg) {
        super(msg);
    }

}
