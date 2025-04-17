package com.example.tfg.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.patareas.model.Tarea;
import com.example.tfg.R;
import com.example.tfg.util.PriorityColorUtil;

import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {
    private List<Tarea> tareas;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Tarea tarea);
        void onDeleteClick(Tarea tarea);
    }

    public TareaAdapter(OnItemClickListener listener) {
        this.listener = listener;
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
    }

    @Override
    public int getItemCount() { return tareas != null ? tareas.size() : 0; }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
        notifyDataSetChanged();
    }

    class TareaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitulo, textViewDescripcion, textViewPrioridad;
        private ImageButton btnDelete;
        private CardView cardView;

        public TareaViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewPrioridad = itemView.findViewById(R.id.textViewPrioridad);
            btnDelete = itemView.findViewById(R.id.btnDelete);
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