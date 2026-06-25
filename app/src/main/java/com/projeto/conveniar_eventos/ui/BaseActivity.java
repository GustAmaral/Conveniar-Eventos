package com.projeto.conveniar_eventos.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
            getSupportActionBar().setTitle(titulo);
            getSupportActionBar().setDisplayHomeAsUpEnabled(exibirBotaoVoltar);
        }
    }

    protected void realizarLogout() {
        getSharedPreferences(PREFS, MODE_PRIVATE)
                .edit()
                .remove(KEY_USER_ID)
                .remove(KEY_USER_NOME)
                .apply();

        Intent it = new Intent(this, MainActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.menu_logout) {
            realizarLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
