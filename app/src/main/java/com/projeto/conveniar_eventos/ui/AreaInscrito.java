package com.projeto.conveniar_eventos.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.data.DatabaseHelper;
import com.projeto.conveniar_eventos.data.MockRepository;
import com.projeto.conveniar_eventos.models.Evento;

import java.util.ArrayList;
import java.util.List;

public class AreaInscrito extends AppCompatActivity {

    private ArrayList<String> stringsExibicao = new ArrayList<>();
    private ArrayList<Integer> idsEventosInscritos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_inscrito);

        TextView tvBoasVindas = findViewById(R.id.tv_boas_vindas);
        ListView lvInscricoes = findViewById(R.id.lv_inscricoes);
        TextView tvVazio       = findViewById(R.id.tv_lista_vazia);

        String nome = getSharedPreferences("conveniar_prefs", MODE_PRIVATE).getString("usuario_nome", "Usuário");
        long userId = getSharedPreferences("conveniar_prefs", MODE_PRIVATE).getLong("usuario_id", -1);

        tvBoasVindas.setText("Painel de: " + nome);

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Cursor c = db.getInscricoesUsuario(userId);

        // Carrega os eventos mockados para fazer a busca do nome em memória
        List<Evento> todosEventos = MockRepository.getEventos(this);

        if (c != null) {
            while (c.moveToNext()) {
                int evId = c.getInt(0);
                String data = c.getString(1);

                // Busca o objeto Evento real para extrair o nome inteligível do curso
                String nomeCurso = "Curso Desconhecido (ID: " + evId + ")";
                for (Evento e : todosEventos) {
                    if (e.getId() == evId) {
                        nomeCurso = e.getCurso();
                        break;
                    }
                }

                stringsExibicao.add(nomeCurso + "\nInscrito em: " + data);
                idsEventosInscritos.add(evId); // Mantém o ID guardado na mesma ordem
            }
            c.close();
        }

        if (stringsExibicao.isEmpty()) {
            tvVazio.setVisibility(View.VISIBLE);
            lvInscricoes.setVisibility(View.GONE);
        } else {
            tvVazio.setVisibility(View.GONE);
            lvInscricoes.setVisibility(View.VISIBLE);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_inscricao, R.id.textViewItem, stringsExibicao);
            lvInscricoes.setAdapter(adapter);

            // Ação de clique para redirecionar de volta aos detalhes do evento real
            lvInscricoes.setOnItemClickListener((parent, view, position, id) -> {
                int eventoIdSelecionado = idsEventosInscritos.get(position);
                Intent it = new Intent(AreaInscrito.this, DetalhesEvento.class);
                it.putExtra("EVENTO_ID", eventoIdSelecionado); // Repassa o ID mapeado
                startActivity(it);
            });
        }
    }
}