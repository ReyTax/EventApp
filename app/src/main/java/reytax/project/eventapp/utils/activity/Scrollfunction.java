package reytax.project.eventapp.utils.activity;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Handler;

public abstract class Scrollfunction extends Activity {

    public static void scrollDown(ScrollView scrollView) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scrollView.smoothScrollTo(0, 2000);
            }
        };
        thread.start();
    }

    public static void scrollFocus(ScrollView scrollView, View view) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scrollView.scrollTo(0, (int) view.getY() - 100);
            }
        };
        thread.start();
    }
}
