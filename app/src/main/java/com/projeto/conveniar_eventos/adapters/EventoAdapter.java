package com.projeto.conveniar_eventos.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.models.Evento;
import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {
    private List<Evento> listaEventos;

    public EventoAdapter(List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = listaEventos.get(position);
        holder.tvCurso.setText(evento.getCurso());
        holder.tvSituacao.setText(evento.getSituacao().toUpperCase());

        // Configuração visual da Tag (Etiqueta)
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(15f);

        if (evento.getSituacao().equalsIgnoreCase("Em oferta")) {
            shape.setColor(Color.parseColor("#C8E6C9")); // Verde claro
        } else {
            shape.setColor(Color.parseColor("#FFF9C4")); // Amarelo claro
        }
        holder.tvSituacao.setBackground(shape);
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurso, tvSituacao;
        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurso = itemView.findViewById(R.id.tv_curso);
            tvSituacao = itemView.findViewById(R.id.tv_situacao);
        }
    }
}