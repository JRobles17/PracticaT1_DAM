package com.example.veterinaria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;

import java.util.List;

/** Adapter genérico de solo lectura, usado en Reportes para mostrar el detalle. */
public class TextoAdapter extends RecyclerView.Adapter<TextoAdapter.VH> {

    private List<String> lista;

    public TextoAdapter(List<String> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_texto, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        h.tvTexto.setText(lista.get(position));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTexto;
        VH(View v) {
            super(v);
            tvTexto = v.findViewById(R.id.tvTexto);
        }
    }
}
