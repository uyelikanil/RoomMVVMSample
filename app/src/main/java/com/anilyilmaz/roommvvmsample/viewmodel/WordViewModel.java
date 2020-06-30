package com.anilyilmaz.roommvvmsample.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.anilyilmaz.roommvvmsample.db.Word;
import com.anilyilmaz.roommvvmsample.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    public WordRepository mRepository;

    public LiveData<List<Word>> mAllWords;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() { return mAllWords; }

    public void insert(Word word) { mRepository.insert(word); }

    public void update(int id, String word) { mRepository.update(id, word); }

}