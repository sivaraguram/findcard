package siva.singtel.findcard.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import siva.singtel.findcard.MainActivity;
import siva.singtel.findcard.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    List<String> titles;
    List<Integer> images;
    List<String> snos;

    LayoutInflater inflater;
    Button btnRestart;

    int counter=0;
    ArrayList<Integer> solvedArr = new ArrayList<Integer>();

    int firstChooseNo=0;
    int secondChooseNo=0;

    int firstChooseVal=0;
    int secondChooseVal=0;

    Context ctxTmp;


    public CardAdapter(Context ctx, List<String> titles, List<Integer> images, List<String> snos){
        this.titles=titles;
        this.images=images;
        this.snos=snos;
        this.inflater=LayoutInflater.from(ctx);
        ctxTmp=ctx;
    }

    @NonNull
    @Override
    public CardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.card_grid_layout, parent, false);
            return new CardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CardAdapter.CardViewHolder holder, int position) {
        holder.sno.setText(snos.get(position));
        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));

        if(solvedArr.size()!=0){
            if(solvedArr.contains(position)){
                holder.title.setVisibility(View.VISIBLE);
                holder.gridIcon.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.title.setVisibility(View.INVISIBLE);
                holder.gridIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        TextView sno;
        TextView title;
        ImageView gridIcon;



        public CardViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView_Card);
            gridIcon=itemView.findViewById(R.id.imageView_card);
            sno=itemView.findViewById(R.id.textView_Sno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    counter++;
                    MainActivity.status.setText("STEPS : " + counter);
                    //int position= getLayoutPosition();

                    if((firstChooseNo==0) && (secondChooseNo==0)) {
                        firstChooseNo=Integer.parseInt(sno.getText().toString());
                        firstChooseVal=Integer.parseInt(title.getText().toString());

                        title.setVisibility(View.VISIBLE);
                        gridIcon.setVisibility(View.INVISIBLE);
                    }
                    else if((firstChooseNo!=0) && (secondChooseNo==0)) {
                        secondChooseNo=Integer.parseInt(sno.getText().toString());
                        secondChooseVal=Integer.parseInt(title.getText().toString());

                        title.setVisibility(View.VISIBLE);
                        gridIcon.setVisibility(View.INVISIBLE);
                    }

                    if((firstChooseNo!=0) && (secondChooseNo!=0)) {
                        if(firstChooseVal==secondChooseVal) {
                            solvedArr.add(firstChooseNo-1);
                            solvedArr.add(secondChooseNo-1);
                            Handler myHandler = new Handler();
                            if(solvedArr.size()==MainActivity.totCards){
                                Toast.makeText(ctxTmp,"Success",Toast.LENGTH_SHORT).show();
                            }
                            myHandler.postDelayed(mMyRunnable, 1);
                        }
                        else{
                            title.setVisibility(View.VISIBLE);
                            gridIcon.setVisibility(View.INVISIBLE);

                            Handler myHandler = new Handler();
                            myHandler.postDelayed(mMyRunnable, 1000);
                        }
                    }
                }
            });
        }

        Runnable mMyRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                //Change state here
                CardAdapter.this.notifyDataSetChanged();
                firstChooseNo=0;
                secondChooseNo=0;
            }
        };
    }


}
