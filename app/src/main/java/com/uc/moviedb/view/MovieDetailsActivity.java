package com.uc.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uc.moviedb.R;
import com.uc.moviedb.helper.Const;
import com.uc.moviedb.model.Genre;
import com.uc.moviedb.model.NowPlaying;
import com.uc.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private TextView lbl_movie_title, lbl_movie_release_date, lbl_movie_overview, lbl_movie_rating, lbl_movie_genres;
    private NowPlaying.Results movie;
    private ImageView image_movie_poster;
    private ArrayList<Integer> genres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");
        genres = intent.getIntegerArrayListExtra("genre_ids");

        lbl_movie_title = findViewById(R.id.lbl_movie_details_title);
        lbl_movie_release_date = findViewById(R.id.lbl_movie_details_release_date);
        lbl_movie_overview = findViewById(R.id.lbl_movie_details_overview);
        lbl_movie_rating = findViewById(R.id.lbl_movie_details_rating);
        image_movie_poster = findViewById(R.id.image_movie_poster);
        lbl_movie_genres = findViewById(R.id.lbl_movie_genres);

        lbl_movie_title.setText(movie.getTitle());
        lbl_movie_release_date.setText("Released " + movie.getRelease_date());
        lbl_movie_overview.setText(movie.getOverview());
        lbl_movie_rating.setText(String.valueOf(movie.getVote_average()) + "/10");
        Glide.with(this).load(Const.IMG_URL + movie.getPoster_path()).into(image_movie_poster);

        viewModel.getMovieGenres(genres);
        viewModel.getResultGetMovieGenres().observe(this, new Observer<List<Genre.Genres>>() {
            @Override
            public void onChanged(List<Genre.Genres> genres) {
                StringBuilder genreToSet = new StringBuilder();
                for (int i = 0; i < genres.size(); i++) {
                    if (i != genres.size() - 1) {
                        genreToSet.append(genres.get(i).getName() + ", ");
                    } else {
                        genreToSet.append(genres.get(i).getName());
                    }
                }

                lbl_movie_genres.setText(genreToSet);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}