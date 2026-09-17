package com.example.veterinaria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.model.Mascota;

import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.VH> {
    public interface Listener {
        void onEditar(Mascota m);
        void onEliminar(Mascota m);
    }

    private List<Mascota> lista;
    private Listener listener;

    public MascotaAdapter(List<Mascota> lista, Listener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascota, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Mascota m = lista.get(position);
        h.tvNombre.setText(m.toString());
        h.tvInfo.setText("Raza: " + m.getRaza() + " · Edad: " + m.getEdad());
        h.btnEditar.setOnClickListener(v -> listener.onEditar(m));
        h.btnEliminar.setOnClickListener(v -> listener.onEliminar(m));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNombre, tvInfo;
        Button btnEditar, btnEliminar;
        VH(View v) {
            super(v);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvInfo = v.findViewById(R.id.tvInfo);
            btnEditar = v.findViewById(R.id.btnEditar);
            btnEliminar = v.findViewById(R.id.btnEliminar);
        }
    }
}
