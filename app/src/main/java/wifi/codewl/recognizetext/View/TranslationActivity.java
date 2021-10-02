package wifi.codewl.recognizetext.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import wifi.codewl.recognizetext.Controller.AdapterTranslation;
import wifi.codewl.recognizetext.Model.Translation;
import wifi.codewl.recognizetext.Path.Status;
import wifi.codewl.recognizetext.R;

public class TranslationActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

    private AdapterTranslation adapterTranslation;

    private List<Translation> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Translation");
        setContentView(R.layout.activity_translation);

        BroadcastReceiver onComplete=new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                File []files = Status.folder.listFiles();
                for (File file : files) {
                    if (file.getName().length() != 15)
                        continue;
                    for (int j = 0; j < list.size(); j++)
                        if (file.getName().equalsIgnoreCase(list.get(j).getOCRName() + ".traineddata"))
                            list.get(j).setExists(true);
                }
                adapterTranslation.notifyDataSetChanged();
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        list = new ArrayList<>();
        list.add(new Translation("Afrikaans","afr","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/afr.traineddata",false));
        list.add(new Translation("Amharic","amh","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/amh.traineddata",false));
        list.add(new Translation("Arabic","ara","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ara.traineddata",false));
        list.add(new Translation("Assamese","asm","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/asm.traineddata",false));
        list.add(new Translation("Azerbaijani","aze","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/aze.traineddata",false));
        list.add(new Translation("Azerbaijani - Cyrillic","aze_cyrl","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/aze_cyrl.traineddata",false));
        list.add(new Translation("Belarusian","bel","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/bel.traineddata",false));
        list.add(new Translation("Bengali","ben","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ben.traineddata",false));
        list.add(new Translation("Tibetan","bod","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/bod.traineddata",false));
        list.add(new Translation("Bosnian","bos","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/bos.traineddata",false));
        list.add(new Translation("Bulgarian","bul","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/bul.traineddata",false));
        list.add(new Translation("Catalan; Valencian","cat","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/cat.traineddata",false));
        list.add(new Translation("Cebuano","ceb","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ceb.traineddata",false));
        list.add(new Translation("Czech","ces","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ces.traineddata",false));
        list.add(new Translation("Chinese - Simplified","chi_sim","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/chi_sim.traineddata",false));
        list.add(new Translation("Chinese - Traditional","chi_tra","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/chi_tra.traineddata",false));
        list.add(new Translation("Cherokee","chr","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/chr.traineddata",false));
        list.add(new Translation("Welsh","cym","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/cym.traineddata",false));
        list.add(new Translation("Danish","dan","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/dan.traineddata",false));
        list.add(new Translation("German","deu","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/deu.traineddata",false));
        list.add(new Translation("Dzongkha","dzo","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/dzo.traineddata",false));
        list.add(new Translation("Greek, Modern (1453-)","ell","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ell.traineddata",false));
        list.add(new Translation("English","eng","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/eng.traineddata",false));
        list.add(new Translation("English, Middle (1100-1500)","enm","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/enm.traineddata",false));
        list.add(new Translation("Esperanto","epo","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/epo.traineddata",false));
        list.add(new Translation("Estonian","est","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/est.traineddata",false));
        list.add(new Translation("Basque","eus","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/eus.traineddata",false));
        list.add(new Translation("Persian","fas","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/fas.traineddata",false));
        list.add(new Translation("Finnish","fin","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/fin.traineddata",false));
        list.add(new Translation("French","fra","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/fra.traineddata",false));
        list.add(new Translation("Frankish","frk","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/frk.traineddata",false));
        list.add(new Translation("French, Middle (ca. 1400-1600)","frm","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/frm.traineddata",false));
        list.add(new Translation("Irish","gle","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/gle.traineddata",false));
        list.add(new Translation("Galician","glg","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/glg.traineddata",false));
        list.add(new Translation("Greek, Ancient (-1453)","grc","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/grc.traineddata",false));
        list.add(new Translation("Gujarati","guj","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/guj.traineddata",false));
        list.add(new Translation("Haitian; Haitian Creole","hat","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/hat.traineddata",false));
        list.add(new Translation("Hebrew","heb","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/heb.traineddata",false));
        list.add(new Translation("Hindi","hin","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/hin.traineddata",false));
        list.add(new Translation("Croatian","hrv","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/hrv.traineddata",false));
        list.add(new Translation("Hungarian","hun","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/hun.traineddata",false));
        list.add(new Translation("Inuktitut","iku","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/iku.traineddata",false));
        list.add(new Translation("Indonesian","ind","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ind.traineddata",false));
        list.add(new Translation("Icelandic","isl","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/isl.traineddata",false));
        list.add(new Translation("Italian","ita","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ita.traineddata",false));
        list.add(new Translation("Italian - Old","ita_old","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ita_old.traineddata",false));
        list.add(new Translation("Javanese","jav","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/jav.traineddata",false));
        list.add(new Translation("Japanese","jpn","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/jpn.traineddata",false));
        list.add(new Translation("Kannada","kan","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/kan.traineddata",false));
        list.add(new Translation("Georgian","kat","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/kat.traineddata",false));
        list.add(new Translation("Georgian - Old","kat_old","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/kat_old.traineddata",false));
        list.add(new Translation("Kazakh","kaz","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/kaz.traineddata",false));
        list.add(new Translation("Central Khmer","khm","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/khm.traineddata",false));
        list.add(new Translation("Kirghiz; Kyrgyz","kir","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/kir.traineddata",false));
        list.add(new Translation("Korean","kor","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/kor.traineddata",false));
        list.add(new Translation("Kurdish","kur","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/kur.traineddata",false));
        list.add(new Translation("Lao","lao","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/lao.traineddata",false));
        list.add(new Translation("Latin","lat","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/lat.traineddata",false));
        list.add(new Translation("Latvian","lav","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/lav.traineddata",false));
        list.add(new Translation("Lithuanian","lit","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/lit.traineddata",false));
        list.add(new Translation("Malayalam","mal","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/mal.traineddata",false));
        list.add(new Translation("Marathi","mar","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/mar.traineddata",false));
        list.add(new Translation("Macedonian","mkd","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/mkd.traineddata",false));
        list.add(new Translation("Maltese","mlt","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/mlt.traineddata",false));
        list.add(new Translation("Malay","msa","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/msa.traineddata",false));
        list.add(new Translation("Burmese","mya","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/mya.traineddata",false));
        list.add(new Translation("Nepali","nep","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/nep.traineddata",false));
        list.add(new Translation("Dutch; Flemish","nld","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/nld.traineddata",false));
        list.add(new Translation("Norwegian","nor","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/nor.traineddata",false));
        list.add(new Translation("Oriya","ori","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ori.traineddata",false));
        list.add(new Translation("Panjabi; Punjabi","pan","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/pan.traineddata",false));
        list.add(new Translation("Polish","pol","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/pol.traineddata",false));
        list.add(new Translation("Portuguese","por","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/por.traineddata",false));
        list.add(new Translation("Pushto; Pashto","pus","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/pus.traineddata",false));
        list.add(new Translation("Romanian; Moldavian; Moldovan","ron","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ron.traineddata",false));
        list.add(new Translation("Russian","rus","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/rus.traineddata",false));
        list.add(new Translation("Sanskrit","san","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/san.traineddata",false));
        list.add(new Translation("Sinhala; Sinhalese","sin","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/sin.traineddata",false));
        list.add(new Translation("Slovak","slk","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/slk.traineddata",false));
        list.add(new Translation("Slovenian","slv","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/slv.traineddata",false));
        list.add(new Translation("Spanish; Castilian","spa","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/spa.traineddata",false));
        list.add(new Translation("Spanish; Castilian - Old","spa_old","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/spa_old.traineddata",false));
        list.add(new Translation("Albanian","sqi","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/sqi.traineddata",false));
        list.add(new Translation("Serbian","srp","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/srp.traineddata",false));
        list.add(new Translation("srp_latn","Serbian - Latin","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/srp_latn.traineddata",false));
        list.add(new Translation("Swahili","swa","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/swa.traineddata",false));
        list.add(new Translation("Swedish","swe","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/swe.traineddata",false));
        list.add(new Translation("Syriac","syr","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/syr.traineddata",false));
        list.add(new Translation("Tamil","tam","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/tam.traineddata",false));
        list.add(new Translation("Telugu","tel","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/tel.traineddata",false));
        list.add(new Translation("Tajik","tgk","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/tgk.traineddata",false));
        list.add(new Translation("Tagalog","tgl","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/tgl.traineddata",false));
        list.add(new Translation("Thai","tha","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/tha.traineddata",false));
        list.add(new Translation("Tigrinya","tir","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/tir.traineddata",false));
        list.add(new Translation("Turkish","tur","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/tur.traineddata",false));
        list.add(new Translation("Uighur; Uyghur","uig","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/uig.traineddata",false));
        list.add(new Translation("Ukrainian","ukr","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/ukr.traineddata",false));
        list.add(new Translation("Urdu","urd","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/urd.traineddata",false));
        list.add(new Translation("Uzbek","uzb","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/uzb.traineddata",false));
        list.add(new Translation("Uzbek - Cyrillic","uzb_cyrl","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/uzb_cyrl.traineddata",false));
        list.add(new Translation("Vietnamese","vie","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/vie.traineddata",false));
        list.add(new Translation("Yiddish","yid","https://github.com/tesseract-ocr/tessdata/raw/3.04.00/yid.traineddata",false));

        File []files = Status.folder.listFiles();
        for (File file : files) {
            if (file.getName().length() != 15)
                continue;
            for (int j = 0; j < list.size(); j++)
                if (file.getName().equalsIgnoreCase(list.get(j).getOCRName() + ".traineddata"))
                    list.get(j).setExists(true);
        }

        recyclerView = findViewById(R.id.recyclerView_FFFF);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(list.size());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterTranslation = new AdapterTranslation(this,list);
        recyclerView.setAdapter(adapterTranslation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}