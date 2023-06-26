package com.rian.barbershop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rian.barbershop.API.APIRequestData;
import com.rian.barbershop.API.RetroServer;
import com.rian.barbershop.Model.ModelResponse;
import com.rian.barbershop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private Button btnTambah;
    private EditText etNama, etFoto, etDeskripsi, etLokasi, etKoordinat;
    private String Nama, Foto, Deskripsi, Lokasi, Koordinat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        etNama = findViewById(R.id.et_nama);
        etFoto = findViewById(R.id.et_foto);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etLokasi = findViewById(R.id.et_lokasi);
        etKoordinat = findViewById(R.id.et_koordinat);
        btnTambah = findViewById(R.id.btn_tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Foto = etFoto.getText().toString();
                Deskripsi = etDeskripsi.getText().toString();
                Lokasi = etLokasi.getText().toString();
                Koordinat = etKoordinat.getText().toString();

                if (Nama.trim().isEmpty()){
                    etNama.setError("Nama harus di isi");
                }else if (Foto.trim().isEmpty()){
                    etFoto.setError("Link foto harus di isi");
                } else if (Deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Deskripsi harus di isi");
                } else if (Lokasi.trim().isEmpty()) {
                    etLokasi.setError("Lokasi harus di isi");
                } else if (Koordinat.trim().isEmpty()) {
                    etKoordinat.setError("Koordinat harus di isi");
                } else{
                    tambahBarbershop();
                }
            }
        });
    }

    private void tambahBarbershop(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(Nama, Foto, Deskripsi, Lokasi, Koordinat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();
                Toast.makeText(TambahActivity.this, "kode : " + kode + "Pesan" + pesan , Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal kirim data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}