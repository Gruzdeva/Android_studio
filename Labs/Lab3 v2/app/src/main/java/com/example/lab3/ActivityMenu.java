package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class ActivityMenu extends AppCompatActivity {

    private DateFormat format = new SimpleDateFormat("HH:mm:ss "); //"yyyy.MM.dd 'at'
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private ArrayList<String> FIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        //database.execSQL("DELETE FROM students");
        insertStartInfo();

        dbHelper.close();

        Button btn_openDB = findViewById(R.id.btn_openDB);
        Button btn_addItemDB = findViewById(R.id.btn_addItemDB);
        Button btn_replace = findViewById(R.id.btn_replace);


        btn_openDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenu.this, ActivityShowDatabase.class);
                startActivity(intent);
            }
        });

        btn_addItemDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();

                Random random = new Random();
                int number;
                number = random.nextInt(FIO.size());
                Calendar thisDate = Calendar.getInstance();
                String data = format.format(thisDate.getTime());

                String[] arrFIO = FIO.get(number).split(" ");
                database.execSQL("INSERT INTO students(first_name, middle_name, last_name, time) " +
                        "VALUES (\'" + arrFIO[1] + "\', \'" + arrFIO[2] + "\', \'" + arrFIO[0] + "\', \'" + data + "\');");
                FIO.remove(number);

                dbHelper.close();
            }
        });

        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();

                database.execSQL("UPDATE students SET first_name = 'Иван', middle_name = 'Иванович', last_name = 'Иванов' WHERE id = (SELECT max(id) FROM students)");

                dbHelper.close();
            }
        });
    }

    private void insertStartInfo() {

        FIO = new ArrayList<>();
        FIO.add("Ткачев Антон Михайлович");
        FIO.add("Безуглый Максим Викторович");
        FIO.add("Стельмах Полина Геннадьевна");
        FIO.add("Кравченко Кирилл Сергеевич");
        FIO.add("Аркадьев Константин Германович");
        FIO.add("Пушкин Александр Сергеевич");
        FIO.add("Каширский Денис Вячеславович");
        FIO.add("Губаева Алина Олеговна");
        FIO.add("Соленкова Дарья Сергеевна");
        FIO.add("Картамышева Олеся Витальевна");
        FIO.add("Белькова Евгения Егоровна");
        FIO.add("Попова Елизавета Алексеевна");
        FIO.add("Пугачев Дмитрий Игоревич");
        FIO.add("Парамонов Артем Иванович");
        FIO.add("Мочалов Артем Валерьевич");
        FIO.add("Мурашева Екатерина Валерьевна");
        FIO.add("Пикалов Глеб Кириллович");
        FIO.add("Майорова Виктория Антоновна");
        FIO.add("Куликова Кристина Викторовна");
        FIO.add("Авдеева Ксения Павловна");
        FIO.add("Мороз Анна Павловна");
        FIO.add("Ткачева Евгения Михайловна");
        FIO.add("Безуглая Елизавета Викторовна");
        FIO.add("Зубкова Анастасия Максимовна");
        FIO.add("Щивалин Леонид Витальевич");
        FIO.add("Милосердова Анастасия Витальевна");

        Random random = new Random();
        int number;
        String[] arrFIO;

        for (int i = 0; i < 5; i++) {

            number = random.nextInt(FIO.size());

            Calendar thisDate = Calendar.getInstance();
            String data = format.format(thisDate.getTime());

            arrFIO = FIO.get(number).split(" ");

            database.execSQL("INSERT INTO students(first_name, middle_name, last_name, time) " +
                    "VALUES (\'" + arrFIO[1] + "\', \'" + arrFIO[2] + "\', \'" + arrFIO[0] + "\', \'" + data + "\');");


            FIO.remove(number);
        }

    }
}