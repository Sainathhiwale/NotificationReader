package dlrvis.sai.notificationreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    CustomListAdapter adapter;
    ArrayList<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelList = new ArrayList<Model>();
        adapter = new CustomListAdapter(getApplicationContext(), modelList);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
    }


    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
             String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            //int id = intent.getIntExtra("icon",0);

            Context remotePackageContext = null;
            try {
//                remotePackageContext = getApplicationContext().createPackageContext(pack, 0);
//                Drawable icon = remotePackageContext.getResources().getDrawable(id);
//                if(icon !=null) {
//                    ((ImageView) findViewById(R.id.imageView)).setBackground(icon);
//                }
                byte[] byteArray =intent.getByteArrayExtra("icon");
                Bitmap bmp = null;
                if(byteArray !=null) {
                    bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
                Model model = new Model();
                model.setName(title +" " +text);
                model.setImage(bmp);

                if(modelList !=null) {
                    modelList.add(model);
                    adapter.notifyDataSetChanged();
                }else {
                    modelList = new ArrayList<Model>();
                    modelList.add(model);
                    adapter = new CustomListAdapter(getApplicationContext(), modelList);
                    list=(ListView)findViewById(R.id.list);
                    list.setAdapter(adapter);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
