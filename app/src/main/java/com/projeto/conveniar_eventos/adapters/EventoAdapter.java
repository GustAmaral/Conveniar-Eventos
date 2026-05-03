package com.projeto.conveniar_eventos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.models.Evento;
import java.util.List;

// adapter que preenche o RecyclerView dinamicamente com os eventos
public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {
    private List<Evento> listaEventos;

    // recebe a lista de eventos
    public EventoAdapter(List<Evento> lista) {
        this.listaEventos = lista;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // cria a interface de um item da lista → converte o xml em view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        // pega o evento da lista e coloca o texto nos TextViews
        Evento evento = listaEventos.get(position);
        holder.tvEvento.setText(evento.getCurso());
        holder.tvSituacao.setText(evento.getSituacao());
    }

    // retorna a qtd de itens na lista
    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    // classe que guarda as referências dos componentes pra não precisar de findViewByID sempre
    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvEvento, tvSituacao;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEvento = itemView.findViewById(R.id.tv_curso);
            tvSituacao = itemView.findViewById(R.id.tv_situacao);
        }
    }
}