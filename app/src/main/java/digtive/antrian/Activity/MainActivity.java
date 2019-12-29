package digtive.antrian.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digtive.antrian.Model.QueueResponse;
import digtive.antrian.R;
import digtive.antrian.Rest.CombineApi;
import digtive.antrian.Rest.QueueInterface;
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
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("services_id");
        String name = getIntent().getStringExtra("services_name");
        tvServicesName.setText(name);
        queueInterface = CombineApi.getApiService();
        getData(id);

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
}
