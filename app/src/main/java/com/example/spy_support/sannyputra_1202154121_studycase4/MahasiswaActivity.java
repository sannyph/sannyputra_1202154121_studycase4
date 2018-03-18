package com.example.spy_support.sannyputra_1202154121_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MahasiswaActivity extends AppCompatActivity {
    //Deklarasi private variabel
    private ListView mListView;
    private ProgressBar mProgressBar;

    //menyimpan daftar nama maahasiswa
    private String [] mUsers= {
            "Audi","Nuzuli","Riza","Krisna","Rangga","Ataya",
            "Patra","Wendy","Kamila"
    };

    //deklarasi private variabel untuk menambahkan item ke listview
    private AddItemToListView mAddItemToListView;
    private Button mStartAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        //Menset fungsi yang akan digunakan
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.listView);
        mStartAsyncTask = (Button) findViewById(R.id.button_startAsyncTask);

        //membuat progress bar visible saat aplikasi di jalankan
        mListView.setVisibility(View.GONE);

        //Setup adapter
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));


        //Memulai async task ketika button diklik
        mStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //process adapter with asyncTask
                mAddItemToListView = new AddItemToListView();
                mAddItemToListView.execute();
            }
        });
    }

    /**
     * inner class for asynctask process
     */
    public class AddItemToListView  extends AsyncTask<Void, String, Void> {

        private ArrayAdapter<String> mAdapter;
        private int counter=1;
        ProgressDialog mProgressDialog = new ProgressDialog(MahasiswaActivity.this);

        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) mListView.getAdapter(); //casting suggestion

            //Pesan yang akan muncul saat proses dijalankan
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setMessage("Please wait....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgress(0);

            //Aktivitas yang akan dilakukan ketika membatalkan proses
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAddItemToListView.cancel(true);
                    mProgressBar.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            });
            mProgressDialog.show();
        }

        //Durasi tiap data yang diproses
        @Override
        protected Void doInBackground(Void... params) {
            for (String item : mUsers){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    mAddItemToListView.cancel(true);
                }
            }
            return null;
        }

        //Perhitungan persen yang akan ditampilkan saat loading
        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);

            Integer current_status = (int) ((counter/(float)mUsers.length)*100);
            mProgressBar.setProgress(current_status);

            //set progress only working for horizontal loading
            mProgressDialog.setProgress(current_status);

            //set message will not working when using horizontal loading
            mProgressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++;
        }

        //Dijalankan ketika proses sudah selesai dan akan menampilkan data
        @Override
        protected void onPostExecute(Void aVoid) {
            //menyembunyikan progreebar
            mProgressBar.setVisibility(View.GONE);

            //menghapus progress dialog
            mProgressDialog.dismiss();
            mListView.setVisibility(View.VISIBLE);
        }
    }
}