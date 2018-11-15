package com.example.wijih.a310.matches;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wijih.a310.R;
import com.example.wijih.a310.model.Match;

import java.util.List;

import static android.graphics.Color.TRANSPARENT;
import static com.example.wijih.a310.R.layout.item_matches;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {
    private List<Match> matchesList;
    private Context context;
//    private Dialog dialog;


    public MatchesAdapter(List<Match> matchesList, Context context) {
        this.matchesList = matchesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        final MatchesViewHolder rView = new MatchesViewHolder(layoutView, context);

//        dialog = new Dialog(context);
//        dialog.setContentView(R.layout.matches_info_window);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        rView.matchInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                TextView dialogMatchName = dialog.findViewById(R.id.matchNameView);
////                TextView dialogContactInfo = dialog.findViewById(R.id.matchContactInfo);
////                ImageView dialogMatchImage = dialog.findViewById(R.id.matchImage);
////                dialogMatchName.setText("DUMMY TEXT");
////                dialogContactInfo.setText("DUMMY TEXT");
////                dialog.show();
//            }
//        });

        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MatchInfoWindow.class);
                context.startActivity(intent);
            }
        });

        return rView;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        holder.matchID.setText(matchesList.get(position).getUserId1());
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
