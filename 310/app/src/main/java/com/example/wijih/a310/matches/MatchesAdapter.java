package com.example.wijih.a310.matches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wijih.a310.R;

import java.util.List;

import static com.example.wijih.a310.R.layout.item_matches;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {
    private List<Matches> matchesList;
    private Context context;


    public MatchesAdapter(List<Matches> matchesList, Context context) {
        this.matchesList = matchesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolder rView = new MatchesViewHolder(layoutView);

        return rView;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        holder.matchID.setText(matchesList.get(position).getUserId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
