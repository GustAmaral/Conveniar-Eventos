package com.projeto.conveniar_eventos.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.conveniar_eventos.R;

import java.util.List;

public class InscricaoAdapter extends RecyclerView.Adapter<InscricaoAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int eventoId);
    }

    public static class ItemInscricao {
        public final int    eventoId;
        public final String nomeCurso;
        public final String dataInscricao;
        public final int    totalDocs;
        public final int    docsEnviados;
        public final int    docsRejeitados;

        public ItemInscricao(int eventoId, String nomeCurso, String dataInscricao,
                             int totalDocs, int docsEnviados, int docsRejeitados) {
            this.eventoId       = eventoId;
            this.nomeCurso      = nomeCurso;
            this.dataInscricao  = dataInscricao;
            this.totalDocs      = totalDocs;
            this.docsEnviados   = docsEnviados;
            this.docsRejeitados = docsRejeitados;
        }
    }

    private final List<ItemInscricao>  items;
    private final OnItemClickListener  listener;

    public InscricaoAdapter(List<ItemInscricao> items, OnItemClickListener listener) {
        this.items    = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inscricao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemInscricao item = items.get(position);
        Context ctx = holder.itemView.getContext();

        holder.tvCurso.setText(item.nomeCurso);
        holder.tvData.setText("Inscrito em: " + item.dataInscricao);

        configurarBadge(holder, item, ctx);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item.eventoId));
    }

    @Override
    public int getItemCount() { return items.size(); }

    private void configurarBadge(ViewHolder holder, ItemInscricao item, Context ctx) {
        if (item.totalDocs == 0) {
            holder.tvBadge.setVisibility(View.GONE);
            return;
        }

        holder.tvBadge.setVisibility(View.VISIBLE);

        String texto;
        int cor;

        if (item.docsRejeitados > 0) {
            texto = "Doc. rejeitado";
            cor   = 0xFFB71C1C; // vermelho escuro
        } else if (item.docsEnviados < item.totalDocs) {
            int pendentes = item.totalDocs - item.docsEnviados;
            texto = pendentes + " doc. pendente" + (pendentes > 1 ? "s" : "");
            cor   = 0xFFE65100; // laranja
        } else {
            texto = "Docs. completos";
            cor   = 0xFF2E7D32; // verde
        }

        holder.tvBadge.setText(texto);
        GradientDrawable bg = (GradientDrawable) ctx.getDrawable(R.drawable.badge_background);
        if (bg != null) {
            bg.setColor(cor);
            holder.tvBadge.setBackground(bg);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvCurso;
        final TextView tvData;
        final TextView tvBadge;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurso = itemView.findViewById(R.id.tv_inscricao_curso);
            tvData  = itemView.findViewById(R.id.tv_inscricao_data);
            tvBadge = itemView.findViewById(R.id.tv_inscricao_badge_docs);
        }
    }
}