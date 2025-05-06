package com.example.tfg.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tfg.R;
import com.example.tfg.model.Tarea;
import com.example.tfg.viewModel.TareaViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    private TareaViewModel tareaViewModel;
    private PieChart pieChart;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_stats);

        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
        Button btnBack = findViewById(R.id.btn_back);

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        configgraficos();
        datos();

        btnBack.setOnClickListener(v -> finish());
    }

    private void configgraficos() {
        // gráfico de pastel
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(android.R.color.transparent);
        pieChart.setTransparentCircleRadius(61f);

        //  gráfico de barras
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
    }

    private void datos() {
        tareaViewModel.getAllTareas().observe(this, tareas -> {
            if (tareas != null && !tareas.isEmpty()) {
                int completadas = 0;
                int pendientes = 0;

                int alta = 0, media = 0, baja = 0;

                for (Tarea tarea : tareas) {
                    if (tarea.isCompletada()) {
                        completadas++;
                    } else {
                        pendientes++;
                    }

                    switch (tarea.getPrioridad()) {
                        case "Alta": alta++; break;
                        case "Media": media++; break;
                        case "Baja": baja++; break;
                    }
                }

                setupPieChartData(completadas, pendientes);
                setupBarChartData(alta, media, baja);
            }
        });
    }

    private void setupPieChartData(int completadas, int pendientes) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(completadas, "Completadas"));
        entries.add(new PieEntry(pendientes, "Pendientes"));

        PieDataSet dataSet = new PieDataSet(entries, "Estado de Tareas");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));

        pieChart.setData(data);
        pieChart.invalidate(); // Refrescar
    }

    private void setupBarChartData(int alta, int media, int baja) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, alta));
        entries.add(new BarEntry(1, media));
        entries.add(new BarEntry(2, baja));

        BarDataSet dataSet = new BarDataSet(entries, "Tareas por Prioridad");
        dataSet.setColors(new int[]{
                getResources().getColor(R.color.priority_high),
                getResources().getColor(R.color.priority_medium),
                getResources().getColor(R.color.priority_low)
        });
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);

        barChart.setData(data);
        barChart.invalidate(); // Refrescar otra vez
    }
}