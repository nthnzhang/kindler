package com.example.wijih.a310.matches;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wijih.a310.R;
import com.example.wijih.a310.model.Match;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView matchID;
    private Context context;

    public MatchesViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        itemView.setOnClickListener(this);

        matchID = itemView.findViewById(R.id.MatchID);
    }

    @Override
    public void onClick(View view) {

    }
}


