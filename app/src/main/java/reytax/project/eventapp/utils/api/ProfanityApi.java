package reytax.project.eventapp.utils.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class ProfanityApi extends AsyncTask<String, Void, Void> {

    private static boolean isProfane;
    private static boolean isDone;

    @Override
    protected Void doInBackground(String... args) {
        sendProfanityCheckRequest(args[0]);
        return null;
    }

    private void sendProfanityCheckRequest(String text) {


        Thread thread = new Thread() {
            @Override
            public void run() {
                isProfane = false;
                isDone = false;
                Response response = null;

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://community-purgomalum.p.rapidapi.com/containsprofanity?text=" + text)
                        .get()
                        .addHeader("x-rapidapi-key", "f89b6a42cfmsh3172608a8149296p11b47ejsne61bfc597a0a")
                        .addHeader("x-rapidapi-host", "community-purgomalum.p.rapidapi.com")
                        .build();

                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if(response.body().string().equals("true"))
                        isProfane = true;
                    isDone = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }

    public static boolean getIsProfane() {
        return isProfane;
    }

    public static boolean getIsDone() {
        return isDone;
    }
}
