package com.example.tfg.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.model.Tarea;

import java.util.ArrayList;
import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private List<Tarea> listaTareas = new ArrayList<>();

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tareaActual = listaTareas.get(position);
        holder.textViewTitulo.setText(tareaActual.getTitulo());
        holder.textViewDescripcion.setText(tareaActual.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public void setTareas(List<Tarea> tareas) {
        this.listaTareas = tareas;
        notifyDataSetChanged();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitulo;
        private TextView textViewDescripcion;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
        }
    }
}

