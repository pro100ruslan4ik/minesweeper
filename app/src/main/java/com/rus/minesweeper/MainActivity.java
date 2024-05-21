package com.rus.minesweeper;

import java.util.Random;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.icu.text.ListFormatter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

    private CellContent[][] mineField;
    private boolean isFirstClick = true;
    private final Context context = this;
    private TextView coord_text_view;
    private int WIDTH = 10;
    private int HEIGHT = 20;
    private int MINE_COUNT = 20;
    private Button[][] cells;

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
        generate();
    }

    void makeMines()
    {
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                mineField[i][j] = CellContent.Empty;
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
            while(mineField[y][x] != CellContent.Empty);
            mineField[y][x] = CellContent.Mine;
        }
    }
    void generate()
    {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
            {
                cells[i][j].setBackgroundResource(R.drawable.untouched);
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
        mineField = new CellContent[HEIGHT][WIDTH];
        makeMines();

        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this,R.color.gray));
        Drawable mineDrawable = ContextCompat.getDrawable(this,R.drawable.mine);
        BitmapDrawable bitmapMineDrawable = (BitmapDrawable) mineDrawable;

        Drawable[] layers = {colorDrawable, bitmapMineDrawable};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        GridLayout cellsLayout = (GridLayout) findViewById(R.id.CellsLayout);
        cellsLayout.removeAllViews();
        cellsLayout.setColumnCount(WIDTH);

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
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


                        if (mineField[tappedY][tappedX] == CellContent.Mine)
                            tappedCell.setBackground(layerDrawable);
                        else
                            tappedCell.setBackground(colorDrawable);

                        coord_text_view.setText("" + tappedX + " " + tappedY);
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

                        coord_text_view.setText(String.valueOf(mineField[tappedY][tappedX]));
                        return true;
                    }
                });
                cells[i][j].setTag(i + "," + j);
                cellsLayout.addView(cells[i][j]);
            }
    }
}