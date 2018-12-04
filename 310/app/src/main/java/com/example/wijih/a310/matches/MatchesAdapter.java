package com.example.wijih.a310.matches;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wijih.a310.R;
import com.example.wijih.a310.model.Match;
import com.example.wijih.a310.model.User;

import java.util.List;

public class  MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {
    private List<Match> matchesList;
    private Context context;
    private User currentUser;

    public MatchesAdapter(List<Match> matchesList, Context context, User currentUser) {
        this.matchesList = matchesList;
        this.context = context;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        final MatchesViewHolder rView = new MatchesViewHolder(layoutView, context);

        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MatchInfoWindow.class);
                Match match = matchesList.get(rView.getAdapterPosition());
                intent.putExtra("match_obj", match);
                intent.putExtra("current_user", currentUser);
                context.startActivity(intent);
            }
        });

        return rView;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        //took this out
        //holder.matchID.setText(matchesList.get(position).getUserId1() + " " + matchesList.get(position).getUserRating1());
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }

    class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView matchID;

        public MatchesViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);

            matchID = itemView.findViewById(R.id.MatchID);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
