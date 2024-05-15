package com.rus.minesweeper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import task.Task;
import task.Stub;

public class MainActivity extends AppCompatActivity {

    private final Context context = this;
    private int WIDTH = 9;
    private int HEIGHT = 15;
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
        makeCells();
        generate();
    }

    void generate()
    {

        //Эту строку нужно удалить
        Task.showMessage(this, "Добавьте код в функцию активности generate() для генерации клеточного поля");


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
    void makeCells() {
        cells = new Button[HEIGHT][WIDTH];
        GridLayout cellsLayout = (GridLayout) findViewById(R.id.CellsLayout);
        cellsLayout.removeAllViews();
        cellsLayout.setColumnCount(WIDTH);
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                cells[i][j] = (Button) inflater.inflate(R.layout.cell, cellsLayout, false);
                cells[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Эту строку нужно удалить
                        Stub.show(context, "Добавьте код в функцию активности onClick() - реакцию на нажатие на клетку");

                        Button tappedCell = (Button) v;

                        //Получаем координтаты нажатой клетки
                        int tappedX = getX(tappedCell);
                        int tappedY = getY(tappedCell);
                        //ADD YOUR CODE HERE
                        //....
                    }
                });
                cells[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //Эту строку нужно удалить
                        Stub.show(context, "Добавьте код в функцию активности onLongClick() - реакцию на долгое нажатие на клетку");
                        return false;
                    }
                });
                cells[i][j].setTag(i + "," + j);
                cellsLayout.addView(cells[i][j]);
            }
    }
}