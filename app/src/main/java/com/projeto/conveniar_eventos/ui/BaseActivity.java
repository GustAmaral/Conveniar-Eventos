package com.projeto.conveniar_eventos.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.projeto.conveniar_eventos.R;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String PREFS        = "conveniar_prefs";
    private static final String KEY_USER_ID  = "usuario_id";
    private static final String KEY_USER_NOME = "usuario_nome";

    protected void configurarToolbar(String titulo, boolean exibirBotaoVoltar) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            // O layout do toolbar usa um TextView próprio (tv_toolbar_titulo)
            // em vez do título nativo da ActionBar. É preciso desativar o
            // título nativo (senão ele fica escondido/duplicado) e preencher
            // o TextView manualmente — senão o título nunca aparece.
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(exibirBotaoVoltar);
        }

        TextView tvTitulo = toolbar.findViewById(R.id.tv_toolbar_titulo);
        if (tvTitulo != null) {
            tvTitulo.setText(titulo);
        }
    }

    protected void configurarBottomNav(BottomNavigationView bottomNav, int itemSelecionado) {
        bottomNav.setSelectedItemId(itemSelecionado);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == itemSelecionado) return false;

            if (id == R.id.nav_home) {
                Intent it = new Intent(this, MainActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(it);
                return false;
            }

            if (id == R.id.nav_eventos) {
                Intent it = new Intent(this, MenuSelecione.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(it);
                return false;
            }

            if (id == R.id.nav_inscrito) {
                long userId = getSharedPreferences(PREFS, MODE_PRIVATE)
                        .getLong(KEY_USER_ID, -1);
                Intent it;
                if (userId != -1) {
                    it = new Intent(this, AreaInscrito.class);
                } else {
                    it = new Intent(this, CadastroUsuario.class);
                }
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(it);
                return false;
            }

            return false;
        });
    }

    protected void realizarLogout() {
        getSharedPreferences(PREFS, MODE_PRIVATE)
                .edit()
                .remove(KEY_USER_ID)
                .remove(KEY_USER_NOME)
                .apply();

        Intent it = new Intent(this, CadastroUsuario.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            voltarOuIrParaHome();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void voltarOuIrParaHome() {
        if (isTaskRoot()) {
            Intent it = new Intent(this, MainActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
            finish();
        } else {
            onBackPressed();
        }
    }
}