package wifi.codewl.recognizetext.Controller;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.googlecode.tesseract.android.TessBaseAPI;


import java.util.LinkedList;


import wifi.codewl.recognizetext.Model.Image;
import wifi.codewl.recognizetext.R;
import wifi.codewl.recognizetext.Path.Status;
import wifi.codewl.recognizetext.View.MainActivity;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder>{

    private Context context;
    private LinkedList<Image> list;
    private int operations=0;

    public AdapterImage(Context context, LinkedList<Image> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = list.get(position);
        holder.image.setImageBitmap(image.getBitmap());
        holder.text.setText(image.getText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAt(Image image){
        list.push(image);
        Status.list.push(image);
        notifyDataSetChanged();
    }

    public void  removeAt(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size() );
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            text = itemView.findViewById(R.id.editTextTextMultiLine);
            text.setTextIsSelectable(true);
            final boolean[] status = {false};
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(status[0])
                        return;
                    status[0] = true;
                    operations++;
                    image.post(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.refreshLayout.setEnabled(true);
                            MainActivity.refreshLayout.setRefreshing(true);
                        }
                    });
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final TessBaseAPI tessBaseAPI = new TessBaseAPI();
                            tessBaseAPI.setDebug(true);
                            tessBaseAPI.init(Status.data.getPath(),  Status.language);
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                            tessBaseAPI.setImage(bitmapDrawable.getBitmap());
                            final String s = tessBaseAPI.getUTF8Text();
                            Image image = list.get(getAdapterPosition());
                            image.setText(s);
                            status[0] = false;
                            operations--;
                            text.post(new Runnable() {
                                @Override
                                public void run() {
                                    text.setText(s);
                                    if(operations==0){
                                        MainActivity.refreshLayout.setRefreshing(false);
                                        MainActivity.refreshLayout.setEnabled(false);
                                    }
                                }
                            });
                            tessBaseAPI.end();
                        }
                    }).start();
                }
            });

        }
    }
}
