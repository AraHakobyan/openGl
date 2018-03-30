package opengl.airhockeyexample.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Aro-PC on 3/21/2018.
 */

public class TextResourceReader {
    public static String readTextFileFromResource(Context context, int resId){
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null){
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException ex){
            throw new RuntimeException("Couldn't open resource: " + resId,ex);
        } catch (Resources.NotFoundException notFoundEx){
            throw new RuntimeException("Resource not found: " + resId, notFoundEx);
        }

        return body.toString();
    }

}
