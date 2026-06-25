package com.projeto.conveniar_eventos.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.data.DatabaseHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Tela unificada de Login / Cadastro.
 *
 * Modo inicial: LOGIN (CPF + senha).
 * Botão "Não tenho cadastro" alterna para o formulário de CADASTRO.
 *
 * Após autenticação bem-sucedida, salva usuario_id nas SharedPreferences
 * e redireciona para InscricaoEvento (se veio de DetalhesEvento) ou
 * para AreaInscrito (se veio de MainActivity).
 */
public class CadastroUsuario extends BaseActivity {

    private static final String PREFS      = "conveniar_prefs";
    private static final String KEY_USER_ID = "usuario_id";
    private static final String KEY_USER_NOME = "usuario_nome";

    // Modo atual da tela
    private boolean modoLogin = true;

    // Views — Login
    private LinearLayout layoutLogin;
    private EditText etLoginCpf, etLoginSenha;
    private Button   btnLogin;
    private TextView tvAlternarCadastro;

    // Views — Cadastro
    private LinearLayout layoutCadastro;
    private EditText etCadNome, etCadCpf, etCadEmail,
            etCadTelefone, etCadOrgao, etCadCargo,
            etCadSenha, etCadSenhaConf;
    private Button   btnCadastrar;
    private TextView tvAlternarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        configurarToolbar("Identificação", true);

        // ── Se já há sessão ativa, não precisa ficar aqui ─────────────
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        long userId = prefs.getLong(KEY_USER_ID, -1);
        if (userId != -1) {
            redirecionarAposLogin();
            return;
        }

        // ── Vincula views ─────────────────────────────────────────────
        layoutLogin         = findViewById(R.id.layout_login);
        layoutCadastro      = findViewById(R.id.layout_cadastro);

        etLoginCpf          = findViewById(R.id.et_login_cpf);
        etLoginSenha        = findViewById(R.id.et_login_senha);
        btnLogin            = findViewById(R.id.btn_login);
        tvAlternarCadastro  = findViewById(R.id.tv_alternar_cadastro);

        etCadNome           = findViewById(R.id.et_cad_nome);
        etCadCpf            = findViewById(R.id.et_cad_cpf);
        etCadEmail          = findViewById(R.id.et_cad_email);
        etCadTelefone       = findViewById(R.id.et_cad_telefone);
        etCadOrgao          = findViewById(R.id.et_cad_orgao);
        etCadCargo          = findViewById(R.id.et_cad_cargo);
        etCadSenha          = findViewById(R.id.et_cad_senha);
        etCadSenhaConf      = findViewById(R.id.et_cad_senha_conf);
        btnCadastrar        = findViewById(R.id.btn_cadastrar);
        tvAlternarLogin     = findViewById(R.id.tv_alternar_login);

        // ── Listeners ─────────────────────────────────────────────────
        btnLogin.setOnClickListener(v -> realizarLogin());
        btnCadastrar.setOnClickListener(v -> realizarCadastro());

        tvAlternarCadastro.setOnClickListener(v -> alternarModo(false));
        tvAlternarLogin.setOnClickListener(v -> alternarModo(true));

        // Começa no modo login
        alternarModo(true);
    }

    private void alternarModo(boolean login) {
        modoLogin = login;
        layoutLogin.setVisibility(login ? View.VISIBLE : View.GONE);
        layoutCadastro.setVisibility(login ? View.GONE : View.VISIBLE);
    }

    // ── Login ─────────────────────────────────────────────────────────

    private void realizarLogin() {
        String cpf   = etLoginCpf.getText().toString().trim().replaceAll("[^0-9]", "");
        String senha = etLoginSenha.getText().toString().trim();

        if (cpf.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha CPF e senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        String hash = sha256(senha);
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Cursor c = db.loginUsuario(cpf, hash);

        if (c.moveToFirst()) {
            long id   = c.getLong(c.getColumnIndexOrThrow(DatabaseHelper.COL_USR_ID));
            String nome = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COL_USR_NOME));
            c.close();
            salvarSessao(id, nome);
            Toast.makeText(this, "Bem-vindo(a), " + nome + "!", Toast.LENGTH_SHORT).show();
            redirecionarAposLogin();
        } else {
            c.close();
            Toast.makeText(this, "CPF ou senha incorretos.", Toast.LENGTH_LONG).show();
        }
    }

    // ── Cadastro ──────────────────────────────────────────────────────

    private void realizarCadastro() {
        String nome     = etCadNome.getText().toString().trim();
        String cpf      = etCadCpf.getText().toString().trim().replaceAll("[^0-9]", "");
        String email    = etCadEmail.getText().toString().trim();
        String telefone = etCadTelefone.getText().toString().trim();
        String orgao    = etCadOrgao.getText().toString().trim();
        String cargo    = etCadCargo.getText().toString().trim();
        String senha    = etCadSenha.getText().toString().trim();
        String senhaC   = etCadSenhaConf.getText().toString().trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios (*).", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cpf.length() != 11) {
            Toast.makeText(this, "CPF inválido (informe apenas os 11 dígitos).", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!senha.equals(senhaC)) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.length() < 6) {
            Toast.makeText(this, "A senha deve ter no mínimo 6 caracteres.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        if (db.cpfExiste(cpf)) {
            Toast.makeText(this, "CPF já cadastrado. Faça login.", Toast.LENGTH_LONG).show();
            alternarModo(true);
            return;
        }

        long id = db.cadastrarUsuario(nome, cpf, email, telefone, orgao, cargo, sha256(senha));
        if (id > 0) {
            salvarSessao(id, nome);
            Toast.makeText(this, "Cadastro realizado! Bem-vindo(a), " + nome + "!", Toast.LENGTH_SHORT).show();
            redirecionarAposLogin();
        } else {
            Toast.makeText(this, "Erro ao cadastrar. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private void salvarSessao(long userId, String nome) {
        getSharedPreferences(PREFS, MODE_PRIVATE)
                .edit()
                .putLong(KEY_USER_ID, userId)
                .putString(KEY_USER_NOME, nome)
                .apply();
    }

    private void redirecionarAposLogin() {
        int eventoIdAposLogin = getIntent().getIntExtra("EVENTO_ID_APOS_LOGIN", -1);
        Intent it;
        if (eventoIdAposLogin != -1) {
            it = new Intent(this, InscricaoEvento.class);
            it.putExtra("EVENTO_ID", eventoIdAposLogin);
        } else {
            it = new Intent(this, AreaInscrito.class);
        }
        startActivity(it);
        finish();
    }

    private static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return input; // fallback (não deve ocorrer)
        }
    }
}
