package reytax.project.eventapp.utils.functions;

import android.content.Context;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class DataConvert {


    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int sizeValue = 1024;
        byte[] bytes = new byte[sizeValue];

        int length = 0;
        while ((length = inputStream.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, length);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
