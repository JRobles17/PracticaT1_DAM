package com.example.veterinaria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Atencion;
import com.example.veterinaria.model.Mascota;
import com.example.veterinaria.model.Usuario;

import java.util.List;

public class AtencionAdapter extends RecyclerView.Adapter<AtencionAdapter.VH> {

    private List<Atencion> lista;

    public AtencionAdapter(List<Atencion> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atencion, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Atencion a = lista.get(position);
        Mascota m = Data.getInstance().buscarMascota(a.getIdMascota());
        String nombreMascota = m != null ? m.getNombre() : "Mascota #" + a.getIdMascota();
        h.tvTitulo.setText(nombreMascota + " - " + a.getMotivo());
        h.tvFechaHora.setText(a.getFecha() + " · " + a.getHora());
        String estado = a.getEstado() != null ? a.getEstado() : Atencion.PENDIENTE;
        h.tvEstado.setText("Estado: " + estado);
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFechaHora, tvEstado;
        VH(View v) {
            super(v);
            tvTitulo = v.findViewById(R.id.tvTitulo);
            tvFechaHora = v.findViewById(R.id.tvFechaHora);
            tvEstado = v.findViewById(R.id.tvEstado);
        }
    }
}
