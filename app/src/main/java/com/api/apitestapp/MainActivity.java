package com.api.apitestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        getPosts();
        getComments();


    }


    private void getPosts(){
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    result.setText("code : "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                StringBuilder text = new StringBuilder();
                for(Post post:posts){
                    text.append("UserID : ").append(post.getUserId()).append("\nId : ").append(post.getId()).append("\nTitle : ").append(post.getTitle()).append("\nBody : ").append(post.getText()).append("\n\n");
                }
                result.setText(text.toString());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(4);

        call.enqueue(new Callback<List<Comment>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()){
                    result.setText("code : "+response.code());
                    return;
                }
                List<Comment> comments = response.body();
                StringBuilder text = new StringBuilder();
                for(Comment comment:comments){
                    text.append("\nPostId : ").append(comment.getPostId()).append("\nName : ").append(comment.getName()).append("\nComment : ").append(comment.getText()+"\n");
                }
                result.setText(text.toString());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });

    }
}