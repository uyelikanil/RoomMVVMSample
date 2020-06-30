package com.anilyilmaz.roommvvmsample.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.anilyilmaz.roommvvmsample.R;
import com.anilyilmaz.roommvvmsample.db.Word;
import com.anilyilmaz.roommvvmsample.viewmodel.WordViewModel;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private Activity activity;
    private WordViewModel mWordViewModel;
    private final LayoutInflater mInflater;
    private List<Word> mWords; // Cached copy of words

    public WordListAdapter(Activity activity, WordViewModel wordViewModel) {
        this.activity = activity;
        this.mWordViewModel = wordViewModel;
        mInflater = LayoutInflater.from(activity.getApplicationContext()); }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Word current = mWords.get(position);
        if (mWords != null)
            holder.wordItemView.setText(current.getWord());
        else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }

        holder.wordItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a new dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.update_word);

                //Create a new edit text
                final EditText input = new EditText(activity.getApplicationContext());
                input.setText(current.getWord());
                //Set edit text to dialog
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mWordViewModel.update(current.id, input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.create().show();
            }
        });
    }

    public void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}