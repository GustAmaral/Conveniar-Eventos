package com.projeto.conveniar_eventos.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.models.Evento;
import com.projeto.conveniar_eventos.ui.DetalhesEvento;
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

        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setCornerRadius(20f);

        if (evento.getSituacao().equalsIgnoreCase("Em oferta")) {
            holder.tvSituacao.setTextColor(android.graphics.Color.parseColor("#2E7D32"));
            gd.setColor(android.graphics.Color.parseColor("#C8E6C9"));
        } else {
            holder.tvSituacao.setTextColor(android.graphics.Color.parseColor("#F57F17"));
            gd.setColor(android.graphics.Color.parseColor("#FFF9C4"));
        }
        holder.tvSituacao.setBackground(gd);

        holder.itemView.setOnClickListener(v -> {
            Intent it = new Intent(v.getContext(), DetalhesEvento.class);
            it.putExtra("EVENTO_ID", evento.getId()); // CORRIGIDO: Passando ID correto
            v.getContext().startActivity(it);
        });
    }

    @Override
    public int getItemCount() { return listaEventos.size(); }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurso, tvSituacao;
        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurso = itemView.findViewById(R.id.tv_curso);
            tvSituacao = itemView.findViewById(R.id.tv_situacao);
        }
    }
}