package com.example.tfg.util;

import android.content.Context;
import androidx.core.content.ContextCompat;

import com.example.tfg.R;

public class PriorityColorUtil {
    public static int getPriorityColor(String priority, Context context) {
        if (context == null) {
            return ContextCompat.getColor(context, R.color.priority_low); // Color por defecto
        }

        switch (priority) {
            case "Alta":
                return ContextCompat.getColor(context, R.color.priority_high);
            case "Media":
                return ContextCompat.getColor(context, R.color.priority_medium);
            case "Baja":
            default:
                return ContextCompat.getColor(context, R.color.priority_low);
        }
    }
}