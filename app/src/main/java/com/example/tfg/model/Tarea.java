package com.example.tfg.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tareas")
public class Tarea {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String prioridad; // "Alta", "Media", "Baja"
    private boolean completada;
//    @ColumnInfo(name = "fecha_vencimiento")
//    private Long fechaVencimiento; // Almacenamos timestamp en milisegundos
//
//    @ColumnInfo(name = "notificada")
//    private boolean notificada = false;

    public Tarea(String titulo, String descripcion, String categoria, String prioridad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.prioridad = prioridad;
        this.completada = false;
      //  this.fechaVencimiento = fechaVencimiento;

    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
//    public Long getFechaVencimiento() { return fechaVencimiento; }
//    public void setFechaVencimiento(Long fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
//    public boolean isNotificada() { return notificada; }
//    public void setNotificada(boolean notificada) { this.notificada = notificada; }
}
