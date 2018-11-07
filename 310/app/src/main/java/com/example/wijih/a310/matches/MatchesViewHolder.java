package com.example.wijih.a310.matches;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wijih.a310.R;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView matchID;

    public MatchesViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        matchID = itemView.findViewById(R.id.MatchID);
    }

    @Override
    public void onClick(View view) {

    }
}
