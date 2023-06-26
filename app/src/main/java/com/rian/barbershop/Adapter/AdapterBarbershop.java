package com.rian.barbershop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rian.barbershop.API.APIRequestData;
import com.rian.barbershop.API.RetroServer;
import com.rian.barbershop.Activity.DetailActivity;
import com.rian.barbershop.Activity.MainActivity;
import com.rian.barbershop.Activity.UbahActivity;
import com.rian.barbershop.Model.ModelBarbershop;
import com.rian.barbershop.Model.ModelResponse;
import com.rian.barbershop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterBarbershop extends RecyclerView.Adapter<AdapterBarbershop.VHBarbershop>{
    private Context ctx;
    private List<ModelBarbershop> listBarbershop;

    public AdapterBarbershop(Context ctx, List<ModelBarbershop> listBarbershop) {
        this.ctx = ctx;
        this.listBarbershop = listBarbershop;
    }

    @NonNull
    @Override
    public VHBarbershop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_barbershop,parent,false);
        return new VHBarbershop(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHBarbershop holder, int position) {
        ModelBarbershop MBS = listBarbershop.get(position);
        holder.tvId.setText(MBS.getId());
        holder.tvNama.setText(MBS.getNama());
        holder.tvFoto.setText(MBS.getFoto());
        holder.tvDeskripsi.setText(MBS.getDeskripsi());
        holder.tvLokasi.setText(MBS.getLokasi());
        holder.tvKoordinat.setText(MBS.getKoordinat());
        Glide
                .with(ctx)
                .load(MBS.getFoto())
                .into(holder.ivFoto);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xNama, xFoto,xDeskripsi, xLokasi, xKoordinat;
                xNama = MBS.getNama();
                xFoto = MBS.getFoto();
                xDeskripsi = MBS.getDeskripsi();
                xLokasi = MBS.getLokasi();
                xKoordinat = MBS.getKoordinat();

                Intent kirim = new Intent(ctx, DetailActivity.class);
                kirim.putExtra("xNama",xNama);
                kirim.putExtra("xFoto",xFoto);
                kirim.putExtra("xDeskripsi",xDeskripsi);
                kirim.putExtra("xLokasi",xLokasi);
                kirim.putExtra("xKoordinat",xKoordinat);
                ctx.startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBarbershop.size();
    }

    public class VHBarbershop extends RecyclerView.ViewHolder{
        private TextView tvId,tvNama, tvFoto,tvDeskripsi, tvLokasi, tvKoordinat;
        private Button btnHapus, btnUbah, btnDetail;
        private ImageView ivFoto;

        public VHBarbershop(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvFoto = itemView.findViewById(R.id.tv_foto);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvKoordinat = itemView.findViewById(R.id.tv_koordinat);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            btnUbah = itemView.findViewById(R.id.btn_ubah);
            btnDetail = itemView.findViewById(R.id.btn_detail);

            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteBarbershop(tvId.getText().toString());
                }
            });

            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, UbahActivity.class);
                    kirim.putExtra("xId", tvId.getText().toString());
                    kirim.putExtra("xNama", tvNama.getText().toString());
                    kirim.putExtra("xFoto", tvFoto.getText().toString());
                    kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                    kirim.putExtra("xLokasi", tvLokasi.getText().toString());
                    kirim.putExtra("xKoordinat", tvKoordinat.getText().toString());
                    ctx.startActivity(kirim);
                }
            });
        }

        void deleteBarbershop(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : "+kode+"Pesan :"+ pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity)ctx).retriveBarbershop();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal terhubung", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
