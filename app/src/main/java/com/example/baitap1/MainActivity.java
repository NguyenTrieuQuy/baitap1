package com.example.baitap1;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textStatus;
    private Button btnDownloadImage, btnDownloadJson;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textStatus = findViewById(R.id.textStatus);
        btnDownloadImage = findViewById(R.id.btnDownloadImage);
        btnDownloadJson = findViewById(R.id.btnDownloadJson);

        btnDownloadImage.setOnClickListener(v -> downloadImage());
        btnDownloadJson.setOnClickListener(v -> downloadJson());
    }

    private void downloadImage() {
        String imageUrl = "https://developer.android.com/static/images/logos/android.svg"; // Đổi link ảnh nếu muốn
        Request request = new Request.Builder().url(imageUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> textStatus.setText("Download image failed!"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> textStatus.setText("Download image failed!"));
                    return;
                }
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(() -> {
                    imageView.setImageBitmap(bitmap);
                    textStatus.setText("Image downloaded!");
                });
            }
        });
    }

    private void downloadJson() {
        String jsonUrl = "https://jsonplaceholder.typicode.com/todos/1";
        Request request = new Request.Builder().url(jsonUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> textStatus.setText("Download JSON failed!"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> textStatus.setText("Download JSON failed!"));
                    return;
                }
                String json = response.body().string();
                runOnUiThread(() -> textStatus.setText("Network OK"));
            }
        });
    }
}
