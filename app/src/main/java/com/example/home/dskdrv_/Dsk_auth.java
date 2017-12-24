package com.example.home.dskdrv_;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class Dsk_auth extends AppCompatActivity {

    TextView tv;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsk_auth);
        tv = (TextView) findViewById(R.id.textView);
        bt = (Button) findViewById(R.id.button);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    tv.setText(get_code());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


    }

        public String get_code() throws IOException {
            String s = "";
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);


                OkHttpClient client = new OkHttpClient();
                HttpUrl.Builder httpul = HttpUrl.parse("https://oauth.yandex.ru/authorize?").newBuilder();
                httpul.addQueryParameter("response_type", "code");
                httpul.addQueryParameter("client_id", "fc3985e6de824b35a95e56b00dd21685");
                httpul.addQueryParameter("login_hint", "DskDrv");
                httpul.addQueryParameter("force_confirm", "yes");
                httpul.addQueryParameter("state", "true");

                Request req = new Request.Builder().url(httpul.build()).build();
                /*
        Request req = new Request.Builder().url("https://oauth.yandex.ru/authorize?" +
                "response_type=code" +
                "&client_id=fc3985e6de824b35a95e56b00dd21685" +
                 "&login_hint=DskDrv" +
                 "&force_confirm=yes" +
                "&state=true").build();
*/

                Response resp = client.newCall(req).execute();
                s=String.valueOf(resp.code());
                Log.i("Dsk_auth_resp", resp.body().toString());
            }
                return s;
    }
}
