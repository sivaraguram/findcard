package siva.singtel.findcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import siva.singtel.findcard.adapter.CardAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView findCardList;
    List<String> titles;
    List<Integer> images;
    List<String> snos;
    CardAdapter cardAdapter;
    public static TextView status;
    public static int totCards;
    Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findCardList=findViewById(R.id.recyclerView_findCard);
        btnRestart=findViewById(R.id.btnRestart);
        status=findViewById(R.id.textView_status);

        titles = new ArrayList<>();  //Random Number
        images = new ArrayList<>();  //Avatar
        snos = new ArrayList<>();   //Sno

        int noOfRow=Integer.parseInt(getString(R.string.gridRow));  //Row
        int noOfCol=Integer.parseInt(getString(R.string.gridCol));  //Col
        totCards=noOfRow* noOfCol;

        int [] cardArr;
        cardArr=getRandomSeries(totCards);

        for(int i=0;i<cardArr.length;i++){
            snos.add(i+1+"");   //Serial No
            titles.add(cardArr[i]+"");  //Random Number
            images.add(R.drawable.ic_help_black_24dp);  //Picture
        }

        cardAdapter = new CardAdapter(this, titles, images, snos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, noOfCol, GridLayoutManager.VERTICAL, false);
        findCardList.setLayoutManager(gridLayoutManager);
        findCardList.setAdapter(cardAdapter);

        btnRestart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                finish();
                startActivity(getIntent());
            }
        });
    }

    public int[] getRandomSeries(int len) {
        int [] ranIntArr=new int[len];
        int arrPos=0;

        //Random Data Generator
        for(int i=0;i<len/2;i++){
            double random = Math.random();
            double randDbl = random*100;
            int randInt = (int)randDbl + 1;
            ranIntArr[arrPos]= randInt; //First Number
            ranIntArr[arrPos+1]= randInt; //Same Match Number
            arrPos+=2;
        }

        //Swap Randomly
        Random rand = new Random();
        for (int i = 0; i < ranIntArr.length; i++) {
            int randomIndexToSwap = rand.nextInt(ranIntArr.length);
            int temp = ranIntArr[randomIndexToSwap];
            ranIntArr[randomIndexToSwap] = ranIntArr[i];
            ranIntArr[i] = temp;
        }
        return ranIntArr;
    }
}
