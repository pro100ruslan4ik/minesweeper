package com.rus.minesweeper;

import android.widget.Button;

class GameCell {

    enum CellContent
    {
        Mine,
        Empty
    }

    CellContent cellContent;
    Button button;
    boolean isOpened;
    boolean isFlagged;

}
