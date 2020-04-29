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
    private ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM students");
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
                number = random.nextInt(name.size());
                Calendar thisDate = Calendar.getInstance();
                String data = format.format(thisDate.getTime());
                database.execSQL("INSERT INTO students(name, time) VALUES (\'" + name.get(number)+ "\','" + data + "');");
                name.remove(number);

                dbHelper.close();
            }
        });

        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();

                database.execSQL("UPDATE students SET name = 'Иванов Иван Иванович' WHERE id = (SELECT max(id) FROM students)");

                dbHelper.close();
            }
        });
    }

    private void insertStartInfo() {

        name = new ArrayList<>();
        name.add("Ткачев Антон Михайлович");
        name.add("Безуглый Максим Викторович");
        name.add("Стельмах Полина Геннадьевна");
        name.add("Кравченко Кирилл Сергеевич");
        name.add("Аркадьев Константин Германович");
        name.add("Пушкин Александр Сергеевич");
        name.add("Каширский Денис Вячеславович");
        name.add("Губаева Алина Олеговна");
        name.add("Соленкова Дарья Сергеевна");
        name.add("Картамышева Олеся Витальевна");
        name.add("Белькова Евгения Егоровна");
        name.add("Попова Елизавета Алексеевна");
        name.add("Пугачев Дмитрий Игоревич");
        name.add("Парамонов Артем Иванович");
        name.add("Мочалов Артем Валерьевич");
        name.add("Мурашева Екатерина Валерьевна");
        name.add("Пикалов Глеб Кириллович");
        name.add("Майорова Виктория Антоновна");
        name.add("Куликова Кристина Викторовна");
        name.add("Авдеева Ксения Павловна");
        name.add("Мороз Анна Павловна");
        name.add("Ткачева Евгения Михайловна");
        name.add("Безуглая Елизавета Викторовна");
        name.add("Зубкова Анастасия Максимовна");
        name.add("Щивалин Леонид Витальевич");
        name.add("Милосердова Анастасия Витальевна");

        Random random = new Random();
        int number;

        for (int i = 0; i < 5; i++) {

            number = random.nextInt(name.size());

            Calendar thisDate = Calendar.getInstance();
            String data = format.format(thisDate.getTime());

            database.execSQL("INSERT INTO students(name, time) VALUES (\'" + name.get(number)+ "\',\'" + data + "\');");
            name.remove(number);
        }

    }
}