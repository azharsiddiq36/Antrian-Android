package digtive.antrian.Activity;

import android.graphics.Point;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digtive.antrian.Model.AntrianResponse;
import digtive.antrian.Model.QueueResponse;
import digtive.antrian.R;
import digtive.antrian.Rest.QueueInterface;
import digtive.antrian.Util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText etPegawai;
    EditText etLoket;
    EditText etDomain;
    @BindView(R.id.tvCurrent)
    TextView tvCurrent;
    @BindView(R.id.tvSetting)
    TextView tvSetting;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvRest)
    TextView tvRest;
    QueueInterface queueInterface;
    @BindView(R.id.tvServicesName)
    TextView tvServicesName;
    @BindView(R.id.mainLayout)
    ConstraintLayout mainLayout;
    @BindView(R.id.alternativeLayout)
    LinearLayout alternativeLayout;
    HashMap<String,String> map;
    Handler handler;
    SessionManager sessionManager;
    int percobaan = 0;
    private static Retrofit retrofit = null;
    public static String BASE_URL= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(MainActivity.this);
        map = sessionManager.getDetailsLoggin();
        handler = new Handler();
        if (map.get(sessionManager.KEY_PENGGUNA_LOKET) != null){
            BASE_URL = "http://"+map.get(sessionManager.KEY_PENGGUNA_DOMAIN)+"/antrian/";
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            handler.postDelayed(m_Runnable,1000);
            queueInterface = retrofit.create(QueueInterface.class);
            //String name = map.get(sessionManager.KEY_PENGGUNA_LOKET);
            String name = "Poli Teknik";
            tvServicesName.setText(name);
            getData(map.get(sessionManager.KEY_PENGGUNA_LOKET));
            mainLayout.setVisibility(View.VISIBLE);
            alternativeLayout.setVisibility(View.GONE);

        }
        else{
            mainLayout.setVisibility(View.GONE);
            alternativeLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(m_Runnable);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(m_Runnable,1000);
    }

    @OnClick(R.id.btnCall)
    protected void btnCall(View view) {
//        callData(map.get(sessionManager.KEY_PENGGUNA_LOKET));
        restCall(map.get(sessionManager.KEY_PENGGUNA_LOKET));
    }

    private void restCall(String s) {
        Call<AntrianResponse> data = queueInterface.restCall(s);
        data.enqueue(new Callback<AntrianResponse>() {
            @Override
            public void onResponse(Call<AntrianResponse> call, Response<AntrianResponse> response) {
                if (response.body().getStatus().equals("200")){
                    Toast.makeText(MainActivity.this, "Memanggil Nomor Urut"+response.body().getData().getAntrianNomor(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Antrian Habis", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AntrianResponse> call, Throwable t) {
                Log.d("Kambing", "onFailure: "+t.toString());
            }
        });
    }

    private void callData(String id) {
        Call<QueueResponse> data = queueInterface.doCall(id);
        data.enqueue(new Callback<QueueResponse>() {
            @Override
            public void onResponse(Call<QueueResponse> call, Response<QueueResponse> response) {
                if (response.body().getStatus() == 200){
                    tvCurrent.setText(String.valueOf(response.body().getSekarang().getAntrianNomor()));
                    tvRest.setText(String.valueOf(response.body().getSisa()));
                    tvTotal.setText(String.valueOf(response.body().getTotal()));
                    Toast.makeText(MainActivity.this, "Nomor Antrian "+tvCurrent.getText(), Toast.LENGTH_SHORT).show();
                }
                if (response.body().getStatus() ==403){
                    Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QueueResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Tidak terhubung dengan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnRecall)
    protected void btnRecall(View view) {
        //recallData(map.get(sessionManager.KEY_PENGGUNA_LOKET));
    }

    private void recallData(String id) {
        Call<QueueResponse> data = queueInterface.doRecall(id);
        data.enqueue(new Callback<QueueResponse>() {
            @Override
            public void onResponse(Call<QueueResponse> call, Response<QueueResponse> response) {
                if (response.body().getStatus() == 200){
                    tvCurrent.setText(String.valueOf(response.body().getSekarang().getAntrianNomor()));
                    tvRest.setText(String.valueOf(response.body().getSisa()));
                    tvTotal.setText(String.valueOf(response.body().getTotal()));
                }
                else{
                    Toast.makeText(MainActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QueueResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Tidak terhubung dengan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @OnClick(R.id.btnSetting)
    protected void btnSetting(View view){
        getPopup(view);
        mainLayout.setVisibility(View.VISIBLE);
        alternativeLayout.setVisibility(View.GONE);
    }
    @OnClick(R.id.tvSetting)
    protected void tvSetting(View view){
        getPopup(view);
    }

    private void getPopup(View view) {

        WindowManager wm = (WindowManager) MainActivity.this.getSystemService(MainActivity.this.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LayoutInflater inflater = (LayoutInflater)
                MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.form_setting, null);
        Button terapkan = (Button) popupView.findViewById(R.id.btnTerapkan);
        etPegawai = (EditText) popupView.findViewById(R.id.etPegawai);
        etDomain = (EditText) popupView.findViewById(R.id.etDomain);
        etLoket = (EditText) popupView.findViewById(R.id.etLoket);
        final PopupWindow popupWindow = new PopupWindow(popupView);
        popupWindow.setWidth(width);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
        popupWindow.setHeight(height-200);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        if (sessionManager.KEY_PENGGUNA_LOKET!=null){

            etPegawai.setText(map.get(sessionManager.KEY_PENGGUNA_USERNAME));
            etDomain.setText(map.get(sessionManager.KEY_PENGGUNA_DOMAIN));
            etLoket.setText(map.get(sessionManager.KEY_PENGGUNA_LOKET));

        }
        terapkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.saveLogin("1",String.valueOf(etPegawai.getText()),String.valueOf(etDomain.getText()),String.valueOf(etLoket.getText()));
                Toast.makeText(MainActivity.this, "Berhasil Melakukan Konfigurasi Awal, Silahkan Restart Aplikasi", Toast.LENGTH_SHORT).show();
//                handler.postDelayed(m_Runnable,1000);
            }
        });
    }

    private void getData(String id) {
        Call<QueueResponse> data = queueInterface.getQueueList(id);
        data.enqueue(new Callback<QueueResponse>() {
            @Override
            public void onResponse(Call<QueueResponse> call, Response<QueueResponse> response) {
                if (response.body().getStatus() == 200){
                    tvCurrent.setText(String.valueOf(response.body().getSekarang().getAntrianNomor()));
                    tvRest.setText(String.valueOf(response.body().getSisa()));
                    tvTotal.setText(String.valueOf(response.body().getTotal()));
                    tvSetting.setText("Selamat Datang "+map.get(sessionManager.KEY_PENGGUNA_USERNAME));
                }
            }

            @Override
            public void onFailure(Call<QueueResponse> call, Throwable t) {

            }
        });
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            getData(map.get(sessionManager.KEY_PENGGUNA_LOKET));
            MainActivity.this.handler.postDelayed(m_Runnable, 1000);
        }

    };
}
