package com.example.veterinaria.vistas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.adapter.TextoAdapter;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Atencion;
import com.example.veterinaria.model.Venta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReportesActivity extends AppCompatActivity {

    EditText txtFechaInicio10, txtFechaFin10;
    Button btnFiltrar10;
    TextView txtTotalVentas10, txtAtenciones10;
    RecyclerView rvDetalles10;
    TextoAdapter adapter;
    List<String> detalle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        txtFechaInicio10 = findViewById(R.id.txtFechaInicio10);
        txtFechaFin10 = findViewById(R.id.txtFechaFin10);
        btnFiltrar10 = findViewById(R.id.btnFiltrar10);
        txtTotalVentas10 = findViewById(R.id.txtTotalVentas10);
        txtAtenciones10 = findViewById(R.id.txtAtenciones10);
        rvDetalles10 = findViewById(R.id.rvDetalles10);

        txtFechaInicio10.setFocusable(false);
        txtFechaInicio10.setOnClickListener(v -> elegirFecha(txtFechaInicio10));
        txtFechaFin10.setFocusable(false);
        txtFechaFin10.setOnClickListener(v -> elegirFecha(txtFechaFin10));

        rvDetalles10.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TextoAdapter(detalle);
        rvDetalles10.setAdapter(adapter);

        btnFiltrar10.setOnClickListener(v -> filtrar());
    }

    private void elegirFecha(EditText destino) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) ->
                destino.setText(String.format("%02d/%02d/%04d", day, month + 1, year)),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void filtrar() {
        String desde = txtFechaInicio10.getText().toString().trim();
        String hasta = txtFechaFin10.getText().toString().trim();
        if (desde.isEmpty() || hasta.isEmpty()) {
            Toast.makeText(this, "Elige ambas fechas (Desde / Hasta)", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Venta> ventas = Data.getInstance().ventasEntreFechas(desde, hasta);
        List<Atencion> atenciones = Data.getInstance().atencionesEntreFechas(desde, hasta);
        double total = Data.getInstance().totalRecaudadoEntreFechas(desde, hasta);

        txtTotalVentas10.setText(String.format(Locale.getDefault(), "Total Ventas: S/ %.2f", total));
        txtAtenciones10.setText("Atenciones: " + atenciones.size());

        detalle.clear();
        for (Venta v : ventas)
            detalle.add("Venta: " + v.getNombreItem() + " x" + v.getCantidad() + " · S/ " + String.format(Locale.getDefault(), "%.2f", v.getTotal()) + " (" + v.getFecha() + ")");
        for (Atencion a : atenciones)
            detalle.add("Atención: " + a.getMotivo() + " · " + a.getFecha() + " " + a.getHora() + " [" + a.getEstado() + "]");
        adapter.notifyDataSetChanged();

        if (detalle.isEmpty()) Toast.makeText(this, "Sin resultados en ese rango de fechas", Toast.LENGTH_SHORT).show();
    }
}
