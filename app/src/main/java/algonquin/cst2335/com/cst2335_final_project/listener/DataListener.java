package algonquin.cst2335.com.cst2335_final_project.listener;

import java.util.ArrayList;

/**
 * Created by badal on 2018-04-17.
 */

public abstract class DataListener<T> {
    public abstract void onDataReceived(ArrayList<T> data);
    public abstract void onDataReceived(T data);
    public abstract void onError(String message);
}
