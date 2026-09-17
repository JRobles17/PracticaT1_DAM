package com.example.veterinaria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.model.Venta;

import java.util.List;

public class VentaAdapter extends RecyclerView.Adapter<VentaAdapter.VH> {

    private List<Venta> lista;

    public VentaAdapter(List<Venta> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Venta ven = lista.get(position);
        h.tvItem.setText(ven.getNombreItem() + " x" + ven.getCantidad());
        h.tvMonto.setText("S/ " + String.format("%.2f", ven.getTotal()));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvItem, tvMonto;
        VH(View v) {
            super(v);
            tvItem = v.findViewById(R.id.tvItem);
            tvMonto = v.findViewById(R.id.tvMonto);
        }
    }
}
