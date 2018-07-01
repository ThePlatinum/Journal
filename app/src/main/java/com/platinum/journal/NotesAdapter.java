package com.platinum.journal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PLATINUM
 * Date 6/29/2018
 * Time 10:20 AM
 * Package com.platinum.journal
 * Project Journal
 */

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Date , Time , Title , Note ;

        ViewHolder(View itemView) {
            super(itemView);
            Date = (TextView)itemView.findViewById(R.id.Date_textView);
            Time = (TextView)itemView.findViewById(R.id.Time_textView);
            Title = (TextView)itemView.findViewById(R.id.Title_textView);
            Note = (TextView)itemView.findViewById(R.id.Note_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String click_identifier = Date.getText().toString().replaceAll(",","").replaceAll("\\s+","") + Title.getText().toString();
                    Context context = v.getContext();
                    Intent i = new Intent(context,AddOrEditJournalActivity.class);
                    i.putExtra("Extra",click_identifier);
                    context.startActivity(i);
                }
            });
        }
    }

    private List<Journals> JournalsListSet;

    NotesAdapter(List<Journals> JournalsListSet) {
       this.JournalsListSet = JournalsListSet;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_notes_card_design,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Journals journals = JournalsListSet.get(position);
        holder.Date.setText(journals.getDate());
        holder.Time.setText(journals.getTime());
        holder.Title.setText(journals.getTitle());
        holder.Note.setText(journals.getNote());

    }

    @Override
    public int getItemCount() {
        return JournalsListSet.size();
    }

}
