package com.example.tfg.view;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.viewModel.TareaViewModel;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.*;
import com.example.tfg.R;
import com.example.patareas.model.Tarea;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import java.util.*;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_stats);

        // Configurar gráfico de pastel
        setupPieChart();

        // Configurar gráfico de barras
        setupBarChart();

        // Cargar datos
        loadChartData();
    }

    private void setupPieChart() {
        PieChart pieChart = findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleRadius(61f);
    }

    private void setupBarChart() {
        BarChart barChart = findViewById(R.id.barChart);

        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(3);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Alta", "Media", "Baja"}));
    }

    private void loadChartData() {
        // Obtener ViewModel
        TareaViewModel tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        tareaViewModel.getEstadisticas().observe(this, stats -> {
            if (stats != null) {
                // 1. Datos para gráfico de pastel
                setupPieChartData(
                        stats.get("completadas"),
                        stats.get("pendientes")
                );

                // 2. Datos para gráfico de barras
                setupBarChartData(
                        stats.get("alta"),
                        stats.get("media"),
                        stats.get("baja")
                );
            }
        });
    }

    private void setupPieChartData(int completadas, int pendientes) {
        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(completadas, "Completadas"));
        entries.add(new PieEntry(pendientes, "Pendientes"));

        PieDataSet dataSet = new PieDataSet(entries, "Estado Tareas");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));

        pieChart.setData(data);
        pieChart.invalidate(); // Refrescar el gráfico
    }

    private void setupBarChartData(int alta, int media, int baja) {
        BarChart barChart = findViewById(R.id.barChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, alta));
        entries.add(new BarEntry(1, media));
        entries.add(new BarEntry(2, baja));

        BarDataSet dataSet = new BarDataSet(entries, "Tareas por Prioridad");
        dataSet.setColors(new int[]{Color.RED, Color.YELLOW, Color.GREEN});
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);

        barChart.setData(data);
        barChart.invalidate(); // Refrescar el gráfico
    }
}