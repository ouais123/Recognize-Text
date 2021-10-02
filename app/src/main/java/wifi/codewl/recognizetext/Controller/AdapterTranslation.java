package wifi.codewl.recognizetext.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import java.util.List;

import wifi.codewl.recognizetext.Model.Translation;
import wifi.codewl.recognizetext.Path.Download;
import wifi.codewl.recognizetext.Path.Status;
import wifi.codewl.recognizetext.R;
import wifi.codewl.recognizetext.View.MainActivity;

public class AdapterTranslation extends RecyclerView.Adapter<AdapterTranslation.ViewHolder> {

    private Context context;
    private List<Translation> list;
    public AdapterTranslation(Context context,List<Translation> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.translation,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Translation translation = list.get(position);

        holder.name.setText(translation.getName());

        if(translation.isExists())
            holder.download.setVisibility(View.INVISIBLE);
         else
            holder.download.setVisibility(View.VISIBLE);

        if (translation.getOCRName().equalsIgnoreCase(Status.language)) {
            holder.download.setVisibility(View.VISIBLE);
            holder.download.setBackgroundResource(R.drawable.ic_baseline_check_24);
            Status.chooses = holder.download;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            download = itemView.findViewById(R.id.download);
            final boolean[] status = {false};
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Translation translation = list.get(position);
                    if(translation.isExists()){
                        if(Status.chooses!=null)
                            Status.chooses.setVisibility(View.INVISIBLE);
                        Status.language = translation.getOCRName();
                        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                        editor.putString(MainActivity.LANGUAGE,Status.language);
                        editor.apply();
                        editor.commit();
                        download.setVisibility(View.VISIBLE);
                        download.setBackgroundResource(R.drawable.ic_baseline_check_24);
                        Status.chooses = download;
                    }else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("language "+translation.getName());
                        builder.setMessage("\n\n");
                        builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(status[0])
                                    return;
                                status[0] = true;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Download.download(context,translation.getLink(),Status.folder.getPath(),translation.getName(),translation.getOCRName(),"traineddata");
                                        status[0] = false;
                                    }
                                }).start();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                    }
                }
            });
        }
    }
}
