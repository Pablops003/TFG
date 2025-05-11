package com.example.tfg.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.model.Tarea;
import com.example.tfg.util.AlarmaManager;
import com.example.tfg.util.PriorityColorUtil;
import com.example.tfg.viewModel.TareaViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {
    private List<Tarea> tareas;
    private OnItemClickListener listener;
    private TareaViewModel tareaViewModel;

    public interface OnItemClickListener {
        void onItemClick(Tarea tarea);
        void onDeleteClick(Tarea tarea);
    }

    public TareaAdapter(OnItemClickListener listener, TareaViewModel tareaViewModel1) {
        this.listener = listener;
        this.tareaViewModel= tareaViewModel1;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea currentTarea = tareas.get(position);
        holder.textViewTitulo.setText(currentTarea.getTitulo());
        holder.textViewDescripcion.setText(currentTarea.getDescripcion());
        holder.textViewPrioridad.setText(currentTarea.getPrioridad());


        // Cambiar color según prioridad
        int prioridadColor = PriorityColorUtil.getPriorityColor(currentTarea.getPrioridad(), holder.itemView.getContext());
        GradientDrawable prioridadfondo = (GradientDrawable) holder.textViewPrioridad.getBackground();
        prioridadfondo.setColor(prioridadColor);

        if (currentTarea.isCompletada()) {
            holder.textViewTitulo.setText("✅ " + currentTarea.getTitulo() + " (Completada)");
            holder.textViewFechaFin.setText("Tarea completada");
            holder.btnCompletar.setVisibility(View.GONE); // Oculta el botón si está completada
        } else {
            holder.textViewTitulo.setText(currentTarea.getTitulo());
            // Muestra el botón si no está completada )
            holder.btnCompletar.setVisibility(View.VISIBLE);
        }


        // Configurar la fecha de finalización (formateada)
        if (currentTarea.getFecha() != null) {
            String fechaFinFormateada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentTarea.getFecha());
            holder.textViewFechaFin.setText(fechaFinFormateada);
        } else {
            holder.textViewFechaFin.setText("Sin fecha");
        }

        holder.btnCompletar.setOnClickListener(v ->{
            if (currentTarea.isCompletada()) {
                return;
            }
            currentTarea.setCompletada(true); // Marca como completada
            tareaViewModel.update(currentTarea); // Actualiza la tarea en la base de datos

            holder.textViewTitulo.setText("✅ " + currentTarea.getTitulo() + " (Completada)");
            holder.textViewFechaFin.setText("Tarea completada");

            holder.btnCompletar.setVisibility(View.GONE);
            AlarmaManager.cancelarAlarma(v.getContext(), currentTarea);
            Toast.makeText(v.getContext(), "¡Tarea marcada como completada!", Toast.LENGTH_SHORT).show();

    });

    }

    @Override
    public int getItemCount() { return tareas != null ? tareas.size() : 0; }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
        notifyDataSetChanged();
    }

    class TareaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitulo, textViewDescripcion, textViewPrioridad, textViewFechaFin;
        private ImageButton btnDelete, btnCompletar;

        private CardView cardView;

        public TareaViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewPrioridad = itemView.findViewById(R.id.textViewPrioridad);
            textViewFechaFin = itemView.findViewById(R.id.textViewFechaFin);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnCompletar = itemView.findViewById(R.id.btnCompletar);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(tareas.get(position));
                }
            });

            btnDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(tareas.get(position));
                }
            });


        }

    }
}