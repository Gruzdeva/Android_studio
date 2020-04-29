package com.example.lab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private int size;

    private final static String[][] num = new String[][] {
            {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
            {"", "десять ", "двадцать ", "тридцать ", "сорок ", "пятьдесят ", "шестьдесят ", "семьдесят ", "восемьдесят ", "девяносто "},
            {"", "сто ", "двести ", "триста ", "четыреста ", "пятьсот ", "шестьсот ", "семьсот ", "восемьсот ", "девятьсот "},
            {"тысяч ", "одна тысяча ", "две тысячи ", "три тысячи ", "четыре тысячи ", "пять тысяч ", "шесть тысяч ", "семь тысяч ", "восемь тысяч ", "девять тысяч "},
            {"", "десять ", "двадцать ", "тридцать ", "сорок ", "пятьдесят ", "шестьдесят ", "семьдесят ", "восемьдесят ", "девяносто "},
            {"", "сто ", "двести ", "триста ", "четыреста ", "пятьсот ", "шестьсот ", "семьсот ", "восемьсот ", "девятьсот "}
    };

    public MyAdapter(int size) {
        this.size = size;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int idForItem = R.layout.recycler_view_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(idForItem, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position + 1);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        void bind(int number) {
            textView.setBackgroundResource(R.drawable.background_text_view_recycler2);
            if (number % 2 == 1) {
                textView.setBackgroundResource(R.drawable.background_text_view_recycler);
                //textView.setBackgroundColor(Color.YELLOW);
            }
            if(number == 1000000) {
                textView.setText("миллион");
                return;
            }

            StringBuilder str = new StringBuilder();

            int element = 0;
            int count = 0;
            while (number != 0) {
                element = number % 10;
                number /= 10;
                count++;
                str.insert(0, num[count - 1][element]);
            }


            String result = str.toString();

            Pattern pattern = Pattern.compile("десять \\S* (тысячи|тысяча|тысяч)");
            Matcher matcher = pattern.matcher(result);
            if (matcher.find()) {
                result = thousands(result);
            }
            pattern = Pattern.compile("десять (\\S{1,})$");
            matcher = pattern.matcher(result);
            if (matcher.find()) {
                result = dozens(result);
            }

            textView.setText(result);

        }

        public  String thousands(String str) {
            str = str.replaceAll("десять одна тысяча", "одиннадцать тысяч");
            str = str.replaceAll("десять две тысячи", "двенадцать тысяч");
            str = str.replaceAll("десять три тысячи", "тринадцать тысяч");
            str = str.replaceAll("десять четыре тысячи", "четырнадцать тысяч");
            str = str.replaceAll("десять пять тысяч", "пятнадцать тысяч");
            str = str.replaceAll("десять шесть тысяч", "шестнадцать тысяч");
            str = str.replaceAll("десять семь тысяч", "семнадцать тысяч");
            str = str.replaceAll("десять восемь тысяч", "восемнадцать тысяч");
            str = str.replaceAll("десять девять тысяч", "девятнадцать тысяч");

            return str;
        }
        public  String dozens(String str) {
            str = str.replaceAll("десять один", "одиннадцать");
            str = str.replaceAll("десять два", "двенадцать");
            str = str.replaceAll("десять три", "тринадцать");
            str = str.replaceAll("десять четыре", "четырнадцать");
            str = str.replaceAll("десять пять", "пятнадцать");
            str = str.replaceAll("десять шесть", "шестнадцать");
            str = str.replaceAll("десять семь", "семнадцать");
            str = str.replaceAll("десять восемь", "восемнадцать");
            str = str.replaceAll("десять девять", "девятнадцать");

            return str;
        }
    }
}
