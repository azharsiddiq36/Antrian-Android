package digtive.antrian.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import digtive.antrian.Adapter.ServicesAdapter;
import digtive.antrian.Model.ServicesResponse;
import digtive.antrian.R;
import digtive.antrian.Rest.CombineApi;
import digtive.antrian.Rest.QueueInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesActivity extends AppCompatActivity {
    @BindView(R.id.rv_History)
    RecyclerView rvHistory;
    QueueInterface queueInterface;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager layoutManager;
    ServicesAdapter servicesAdapter;
    ArrayList service = new ArrayList<>();
    String TAG = "kumbang";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(ServicesActivity.this);
        queueInterface = CombineApi.getApiService();
        layoutManager = new LinearLayoutManager(ServicesActivity.this, LinearLayoutManager.VERTICAL, false);
        rvHistory.setLayoutManager(layoutManager);

        progressDialog.setMessage("Sedang Memuat Data ...");
        progressDialog.show();
        progressDialog.show();
        progressDialog.setCancelable(false);
        Call<ServicesResponse> data = queueInterface.getListServices();
        data.enqueue(new Callback<ServicesResponse>() {
            @Override
            public void onResponse(Call<ServicesResponse> call, Response<ServicesResponse> response) {
                progressDialog.hide();
                if (response.body().getStatus() == 200){
                    servicesAdapter = null;
                    service = (ArrayList) response.body().getData();
                    servicesAdapter = new ServicesAdapter(ServicesActivity.this,service);
                    rvHistory.setAdapter(servicesAdapter);
                    servicesAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ServicesActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServicesResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });

    }
}
