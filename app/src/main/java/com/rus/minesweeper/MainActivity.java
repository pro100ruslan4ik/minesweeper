package com.rus.minesweeper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{

    enum CellContent
    {
        Mine,
        Empty
    }

    private boolean isFirstClick = true;
    private final Context context = this;
    private TextView coord_text_view;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private final int MINE_COUNT = 20;
    private int countOfOpenedCells = 0;
    private Button[][] cells;
    private CellContent[][] mineFieldCells;
    private boolean[][] openedCells;
    private boolean[][] flaggedCells;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        coord_text_view = findViewById(R.id.coord_text_view);
        makeCells();
        initializeBackgroundInCells();
    }

    void makeMines(int xFirst, int yFirst)
    {
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                mineFieldCells[i][j] = CellContent.Empty;
            }
        }

        Random random = new Random();
        int x;
        int y;

        for (int currentMineCount = 0; currentMineCount < MINE_COUNT; currentMineCount++)
        {
            do
            {
                x = random.nextInt(WIDTH);
                y = random.nextInt(HEIGHT);
            }
            while(mineFieldCells[y][x] != CellContent.Empty || (x == xFirst && y == yFirst));
            mineFieldCells[y][x] = CellContent.Mine;
        }
    }
    void initializeBackgroundInCells()
    {
        openedCells = new boolean[HEIGHT][WIDTH];
        flaggedCells = new boolean[HEIGHT][WIDTH];

        InputStream inputStream = getResources().openRawResource(R.raw.untouched);
        Drawable untouchedDrawable = Drawable.createFromStream(inputStream, null);

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
            {
                cells[i][j].setBackground(untouchedDrawable);
                openedCells[i][j] = false;
                flaggedCells[i][j] = false;
            }
    }
    int getY(View v)
    {
        return Integer.parseInt(((String) v.getTag()).split(",")[0]);
    }

    int getX(View v)
    {
        return Integer.parseInt(((String) v.getTag()).split(",")[1]);
    }

    void makeCells()
    {
        cells = new Button[HEIGHT][WIDTH];
        mineFieldCells = new CellContent[HEIGHT][WIDTH];

        GridLayout cellsLayout = (GridLayout) findViewById(R.id.CellsLayout);
        cellsLayout.removeAllViews();
        cellsLayout.setColumnCount(WIDTH);

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
            {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                cells[i][j] = (Button) inflater.inflate(R.layout.cell, cellsLayout, false);
                cells[i][j].setOnClickListener(new View.OnClickListener()
            {
                    @Override
                    public void onClick(View v)
                    {
                        Button tappedCell = (Button) v;

                        int tappedX = getX(tappedCell);
                        int tappedY = getY(tappedCell);

                        if (isFirstClick)
                        {
                            makeMines(tappedX, tappedY);
                            isFirstClick = false;
                        }

                        try
                        {
                            if (!flaggedCells[tappedY][tappedX])
                                openCell(tappedX, tappedY);
                        }
                        catch (Exception e)
                        {
                            coord_text_view.setText(e.getMessage());
                        }

                        coord_text_view.setText(tappedX + " " + tappedY);
                    }
                });
                cells[i][j].setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        Button tappedCell = (Button) v;

                        int tappedX = getX(tappedCell);
                        int tappedY = getY(tappedCell);

                        if (!openedCells[tappedY][tappedX])
                        {
                            flaggedCells[tappedY][tappedX] = !flaggedCells[tappedY][tappedX];

                            if (flaggedCells[tappedY][tappedX])
                                tappedCell.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flag));
                            else
                            {
                                InputStream inputStream = getResources().openRawResource(R.raw.untouched);
                                Drawable untouchedDrawable = Drawable.createFromStream(inputStream, null);

                                tappedCell.setBackground(untouchedDrawable);
                            }
                        }


                        coord_text_view.setText(String.valueOf(mineFieldCells[tappedY][tappedX]));
                        return true;
                    }
                });
                cells[i][j].setTag(i + "," + j);
                cellsLayout.addView(cells[i][j]);
            }
    }
    void defeat()
    {
        for(int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
            {
                if(mineFieldCells[i][j] == CellContent.Mine && !openedCells[i][j])
                {
                    cells[i][j].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.untouched_mine));
                }
            }
        showDefeatDialog();
    }

    void showDefeatDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.You_lost);
        builder.setMessage(R.string.Play_again);

        builder.setNegativeButton(R.string.Exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setPositiveButton(R.string.Start_over, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recreate();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    void win()
    {
        for(int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
            {
                if(mineFieldCells[i][j] == CellContent.Mine && !openedCells[i][j])
                {
                    cells[i][j].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.untouched_mine));
                }
            }
        showWinDialog();
    }

    void showWinDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.You_win);
        builder.setMessage(R.string.Play_again);

        builder.setNegativeButton(R.string.Exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setPositiveButton(R.string.Start_over, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recreate();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    void openCell(int x, int y) throws Exception
    {
        if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT)
            throw new Exception("Error: Out of bounds");

        if (mineFieldCells[y][x] == CellContent.Mine)
        {
            cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.exploded_mine));
            openedCells[y][x] = true;
            defeat();
            return;
        }

        if (openedCells[y][x] || flaggedCells[y][x])
            return;

        openedCells[y][x] = true;
        countOfOpenedCells++;

        ArrayList<Button> neighboors = makeNeighboorsList(x,y);

        switch (countMines(neighboors))
        {
            case 0:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.empty));

                for (Button b : neighboors)
                    openCell(getX(b),getY(b));
                break;

            case 1:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.one));
                break;

            case 2:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.two));
                break;

            case 3:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.three));
                break;

            case 4:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.four));
                break;

            case 5:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.five));
                break;

            case 6:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.six));
                break;

            case 7:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.seven));
                break;

            case 8:
                cells[y][x].setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.eight));
                break;

            default:
                throw new Exception("Error: Incorrect count of mine neighboors");

        }
        if (countOfOpenedCells == WIDTH * HEIGHT - MINE_COUNT)
            win();
    }

    int countMines(ArrayList<Button> neighboors)
    {
        int mineCounter = 0;

        for (Button b : neighboors)
        {
            if (mineFieldCells[getY(b)][getX(b)] == CellContent.Mine)
                mineCounter++;
        }

        return mineCounter;
    }
    ArrayList<Button> makeNeighboorsList(int x, int y)
    {
        ArrayList<Button> neighboors = new ArrayList<>();

        if (x > 0)
        {
            neighboors.add(cells[y][x-1]);
            if (y > 0)
                neighboors.add(cells[y-1][x-1]);
            if (y < HEIGHT - 1)
                neighboors.add(cells[y+1][x-1]);
        }
        if (y > 0)
        {
            neighboors.add(cells[y-1][x]);
            if (x < WIDTH - 1)
                neighboors.add(cells[y-1][x+1]);
        }
        if (x < WIDTH - 1)
        {
            neighboors.add(cells[y][x+1]);
            if (y < HEIGHT - 1)
                neighboors.add(cells[y+1][x+1]);
        }
        if (y < HEIGHT - 1)
        {
            neighboors.add(cells[y+1][x]);
        }
        return neighboors;
    }

}