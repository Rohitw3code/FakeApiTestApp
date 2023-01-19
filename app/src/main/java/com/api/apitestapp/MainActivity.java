package com.api.apitestapp;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    result.setText("code : "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                String text = "";
                for(Post post:posts){
                    text+="UserID : "+post.getUserId()+"\nId : "+post.getId()+"\nTitle : "+post.getTitle()+
                            "\nBody : "+post.getText()+"\n\n";
                }
                result.setText(text);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });

    }
}