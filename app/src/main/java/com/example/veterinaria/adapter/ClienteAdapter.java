package com.example.veterinaria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.model.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.VH> {
    public interface Listener {
        void onEditar(Cliente c);
        void onEliminar(Cliente c);
    }

    private List<Cliente> lista;
    private Listener listener;

    public ClienteAdapter(List<Cliente> lista, Listener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Cliente c = lista.get(position);
        h.tvNombre.setText(c.getNombre());
        h.tvTelefono.setText(c.getTelefono() + " · " + c.getDireccion());
        h.btnEditar.setOnClickListener(v -> listener.onEditar(c));
        h.btnEliminar.setOnClickListener(v -> listener.onEliminar(c));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTelefono;
        Button btnEditar, btnEliminar;
        VH(View v) {
            super(v);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvTelefono = v.findViewById(R.id.tvTelefono);
            btnEditar = v.findViewById(R.id.btnEditar);
            btnEliminar = v.findViewById(R.id.btnEliminar);
        }
    }
}
