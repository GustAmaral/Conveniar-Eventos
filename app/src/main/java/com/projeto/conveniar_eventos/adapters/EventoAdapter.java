package com.projeto.conveniar_eventos.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

        // Criar o fundo colorido (Pílula) para o status
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setCornerRadius(20f);

        if (evento.getSituacao().equalsIgnoreCase("Em oferta")) {
            holder.tvSituacao.setTextColor(android.graphics.Color.parseColor("#2E7D32")); // Verde escuro
            gd.setColor(android.graphics.Color.parseColor("#C8E6C9")); // Verde claro
        } else {
            holder.tvSituacao.setTextColor(android.graphics.Color.parseColor("#F57F17")); // Laranja escuro
            gd.setColor(android.graphics.Color.parseColor("#FFF9C4")); // Amarelo claro
        }
        holder.tvSituacao.setBackground(gd);

        // Clique para abrir detalhes
        // Dentro do onBindViewHolder do seu EventoAdapter:
        holder.itemView.setOnClickListener(v -> {
            Intent it = new Intent(v.getContext(), DetalhesEvento.class);
            it.putExtra("NOME_CURSO", evento.getCurso());
            it.putExtra("DATA_INICIO", evento.getDataInicio()); // ESSENCIAL
            it.putExtra("URL_DETALHES", evento.getUrlDetalhes());
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