package digtive.antrian.Activity;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digtive.antrian.Model.QueueResponse;
import digtive.antrian.R;
import digtive.antrian.Rest.CombineApi;
import digtive.antrian.Rest.QueueInterface;
import digtive.antrian.Util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tvCurrent)
    TextView tvCurrent;
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
    String id;
    HashMap<String,String> map;
    Handler handler;

    SessionManager sessionManager;
    int percobaan = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(MainActivity.this);
        map = sessionManager.getDetailsLoggin();
        queueInterface = CombineApi.getApiService();

        if (map.get(sessionManager.KEY_PENGGUNA_LOKET) != null){
            String name = "poli gigi";
            this.id = "1";
            tvServicesName.setText(name);
            getData(id);
            mainLayout.setVisibility(View.VISIBLE);
            alternativeLayout.setVisibility(View.GONE);
        }
        else{
            mainLayout.setVisibility(View.GONE);
            alternativeLayout.setVisibility(View.VISIBLE);
        }


    }

    private void getData(String id) {
        Call<QueueResponse> data = queueInterface.getQueueList(id);
        data.enqueue(new Callback<QueueResponse>() {
            @Override
            public void onResponse(Call<QueueResponse> call, Response<QueueResponse> response) {
                if (response.body().getStatus() == 200 && response.body().getSekarang() != null){
                    tvCurrent.setText(String.valueOf(response.body().getSekarang().getAntrianNomor()));
                    tvRest.setText(String.valueOf(response.body().getSisa()));
                    tvTotal.setText(String.valueOf(response.body().getTotal()));
                }
                else if (response.body().getStatus() == 200 && response.body().getSekarang() == null){
                    Toast.makeText(MainActivity.this, "Antrian Belum Tersedia", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QueueResponse> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btnCall)
    protected void btnCall(View view) {
        callData(id);
        Toast.makeText(this, "ini button call", Toast.LENGTH_SHORT).show();
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

            }
        });
    }

    @OnClick(R.id.btnRecall)
    protected void btnRecall(View view) {
        recallData(id);

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
                    Toast.makeText(MainActivity.this, "Nomor Antrian "+tvCurrent.getText(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QueueResponse> call, Throwable t) {

            }
        });
    }
    @OnClick(R.id.btnSetting)
    protected void btnSetting(View view){

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
        final PopupWindow popupWindow = new PopupWindow(popupView);
        popupWindow.setWidth(width);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
//                popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod); dari bawah
//                popupWindow.setAnimationStyle(android.R.style.Animation_Toast); fadein,bounce
//                popupWindow.setAnimationStyle(android.R.style.Animation_Dialog); fadein,fadeout
//                popupWindow.setAnimationStyle(android.R.style.Animation_Translucent); dari samping kanan
        popupWindow.setHeight(height-200);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        terapkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler = new Handler();
                handler.postDelayed(m_Runnable,5000);
            }
        });


    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            percobaan +=1;
            Toast.makeText(MainActivity.this,"Ke - "+percobaan,Toast.LENGTH_SHORT).show();
            MainActivity.this.handler.postDelayed(m_Runnable, 5000);
        }

    };
}
