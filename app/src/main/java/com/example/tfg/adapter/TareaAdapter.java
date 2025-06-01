package com.example.tfg.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.model.Tarea;
import com.example.tfg.util.PriorityColorUtil;
import com.example.tfg.viewModel.TareaViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private List<Tarea> tareas;
    private OnItemClickListener listener;
    private TareaViewModel tareaViewModel;
    private UUID usuarioId;

    public interface OnItemClickListener {
        void onItemClick(Tarea tarea);
        void onDeleteClick(Tarea tarea);
        void onCompleteClick(Tarea tarea);
    }

    public TareaAdapter(UUID usuarioId, OnItemClickListener listener, TareaViewModel tareaViewModel) {
        this.usuarioId = usuarioId;
        this.listener = listener;
        this.tareaViewModel = tareaViewModel;
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

        int prioridadColor = PriorityColorUtil.getPriorityColor(currentTarea.getPrioridad(), holder.itemView.getContext());
        GradientDrawable prioridadFondo = (GradientDrawable) holder.textViewPrioridad.getBackground();
        prioridadFondo.setColor(prioridadColor);

        if (currentTarea.isCompletada()) {
            holder.textViewTitulo.setText("✅ " + currentTarea.getTitulo() + " (Completada)");
            holder.textViewFechaFin.setText("Tarea completada");
            holder.btnCompletar.setVisibility(View.GONE);
        } else {
            holder.textViewTitulo.setText(currentTarea.getTitulo());
            holder.btnCompletar.setVisibility(View.VISIBLE);
        }

        if (currentTarea.getFecha() != null) {
            String fechaFinFormateada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentTarea.getFecha());
            holder.textViewFechaFin.setText(fechaFinFormateada);

            if (holder.estaVencida(currentTarea)) {
                holder.textViewFechaFin.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.priority_high));
            } else {
                holder.textViewFechaFin.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_primary));
            }
        }

        // Cuando se pulsa el botón de completar, avisamos al listener
        holder.btnCompletar.setOnClickListener(v -> {
            if (!currentTarea.isCompletada() && listener != null) {
                listener.onCompleteClick(currentTarea);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareas != null ? tareas.size() : 0;
    }

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

        private boolean estaVencida(Tarea tarea) {
            if (tarea.isCompletada() || tarea.getFecha() == null) {
                return false;
            }
            return tarea.getFecha().before(new Date());
        }
    }
}
