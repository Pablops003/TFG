package com.example.tfg.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "tareas")
public class Tarea implements Serializable {
    // @PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @NonNull
    private UUID id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String prioridad; // "Alta", "Media", "Baja"
    private boolean completada;
    @SerializedName("usuarioId")
    private UUID usuarioId;
    @ColumnInfo(name = "fecha")
    private Date fecha;


//    public Tarea(String titulo, String descripcion, String categoria, String prioridad, Date fecha) {
//        this.titulo = titulo;
//        this.descripcion = descripcion;
//        this.categoria = categoria;
//        this.prioridad = prioridad;
//        this.completada = false;
//        this.fecha=fecha;
//
//    }

    public Tarea() {
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public UUID getUsuarioId() {return usuarioId;}
    public void setUsuarioId(UUID usarioId) {this.usuarioId = usarioId;}
}

