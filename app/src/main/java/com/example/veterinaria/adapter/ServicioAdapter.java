package com.example.veterinaria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.model.Servicio;

import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.VH> {
    public interface Listener {
        void onEditar(Servicio s);
        void onEliminar(Servicio s);
    }

    private List<Servicio> lista;
    private Listener listener;

    public ServicioAdapter(List<Servicio> lista, Listener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Servicio s = lista.get(position);
        h.tvNombre.setText(s.getNombre());
        h.tvInfo.setText("S/ " + s.getPrecio() + " · " + s.getDescripcion());
        h.btnEditar.setOnClickListener(v -> listener.onEditar(s));
        h.btnEliminar.setOnClickListener(v -> listener.onEliminar(s));
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
