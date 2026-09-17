package com.example.veterinaria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.VH> {
    public interface Listener {
        void onEditar(Usuario u);
        void onEliminar(Usuario u);
    }

    private List<Usuario> lista;
    private Listener listener;

    public UsuarioAdapter(List<Usuario> lista, Listener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Usuario u = lista.get(position);
        h.tvNombre.setText(u.toString() + (u.isActivo() ? "" : " [Inactivo]"));
        h.tvUsuario.setText("Usuario: " + u.getUsuario());
        h.btnEditar.setOnClickListener(v -> listener.onEditar(u));
        h.btnEliminar.setOnClickListener(v -> listener.onEliminar(u));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNombre, tvUsuario;
        Button btnEditar, btnEliminar;
        VH(View v) {
            super(v);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvUsuario = v.findViewById(R.id.tvUsuario);
            btnEditar = v.findViewById(R.id.btnEditar);
            btnEliminar = v.findViewById(R.id.btnEliminar);
        }
    }
}
