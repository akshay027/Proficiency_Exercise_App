package com.example.proficiency_exercise_app;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("facts.json")
    Call<ExerciseDetails> doGetListResources();
}
