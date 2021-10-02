package wifi.codewl.recognizetext.Path;

import android.widget.TextView;

import java.io.File;
import java.util.LinkedList;

import wifi.codewl.recognizetext.Model.Image;

public class Status {
    public static boolean send;
    public static File folder;
    public static File data;
    public static LinkedList<Image> list;
    static {
        list = new LinkedList<>();
    }
    public static String language;
    public static TextView chooses;
}
