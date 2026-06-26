package com.projeto.conveniar_eventos.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "conveniar.db";
    private static final int    DB_VERSION = 3;

    public static final String TB_USUARIOS           = "usuarios";
    public static final String COL_USR_ID            = "id";
    public static final String COL_USR_NOME          = "nome";
    public static final String COL_USR_CPF           = "cpf";
    public static final String COL_USR_EMAIL         = "email";
    public static final String COL_USR_TELEFONE      = "telefone";
    public static final String COL_USR_ORGAO         = "orgao";
    public static final String COL_USR_CARGO         = "cargo";
    public static final String COL_USR_SENHA_HASH    = "senha_hash";

    public static final String TB_INSCRICOES         = "inscricoes";
    public static final String COL_INS_ID            = "id";
    public static final String COL_INS_USUARIO_ID    = "usuario_id";
    public static final String COL_INS_EVENTO_ID     = "evento_id";
    public static final String COL_INS_DATA          = "data_inscricao";
    public static final String COL_INS_MATRICULA     = "matricula";
    public static final String COL_INS_NIVEL_TI      = "nivel_ti";
    public static final String COL_INS_LINKEDIN      = "linkedin";
    public static final String COL_INS_AREA_ATUACAO  = "area_atuacao";
    public static final String COL_INS_OAB           = "oab";
    public static final String COL_INS_MOTIVACAO      = "motivacao";
    public static final String COL_INS_ACEITE_TERMOS  = "aceite_termos";

    public static final String TB_VAGAS              = "vagas_controle";
    public static final String COL_VAG_EVENTO_ID     = "evento_id";
    public static final String COL_VAG_VAGAS         = "vagas";

    public static final String TB_DOCUMENTOS         = "documentos";
    public static final String COL_DOC_ID            = "id";
    public static final String COL_DOC_INSCRICAO_ID  = "inscricao_id";
    public static final String COL_DOC_TIPO          = "tipo_documento";
    public static final String COL_DOC_NOME_ARQUIVO  = "nome_arquivo";
    public static final String COL_DOC_CAMINHO_LOCAL = "caminho_local";
    public static final String COL_DOC_STATUS        = "status";
    public static final String COL_DOC_DATA_UPLOAD   = "data_upload";

    public static final String DOC_STATUS_PENDENTE  = "PENDENTE";
    public static final String DOC_STATUS_ENVIADO   = "ENVIADO";
    public static final String DOC_STATUS_REJEITADO = "REJEITADO";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context ctx) {
        if (instance == null)
            instance = new DatabaseHelper(ctx.getApplicationContext());
        return instance;
    }

    private DatabaseHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TB_USUARIOS + " (" +
                COL_USR_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USR_NOME       + " TEXT NOT NULL, " +
                COL_USR_CPF        + " TEXT NOT NULL UNIQUE, " +
                COL_USR_EMAIL      + " TEXT NOT NULL, " +
                COL_USR_TELEFONE   + " TEXT, " +
                COL_USR_ORGAO      + " TEXT, " +
                COL_USR_CARGO      + " TEXT, " +
                COL_USR_SENHA_HASH + " TEXT NOT NULL" +
                ");");

        db.execSQL("CREATE TABLE " + TB_INSCRICOES + " (" +
                COL_INS_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_INS_USUARIO_ID  + " INTEGER NOT NULL, " +
                COL_INS_EVENTO_ID   + " INTEGER NOT NULL, " +
                COL_INS_DATA        + " TEXT NOT NULL, " +
                COL_INS_MATRICULA   + " TEXT, " +
                COL_INS_NIVEL_TI    + " TEXT, " +
                COL_INS_LINKEDIN    + " TEXT, " +
                COL_INS_AREA_ATUACAO+ " TEXT, " +
                COL_INS_OAB          + " TEXT, " +
                COL_INS_MOTIVACAO    + " TEXT, " +
                COL_INS_ACEITE_TERMOS+ " TEXT, " +
                "FOREIGN KEY(" + COL_INS_USUARIO_ID + ") REFERENCES " + TB_USUARIOS + "(" + COL_USR_ID + "), " +
                "UNIQUE(" + COL_INS_USUARIO_ID + ", " + COL_INS_EVENTO_ID + ")" +
                ");");

        db.execSQL("CREATE TABLE " + TB_VAGAS + " (" +
                COL_VAG_EVENTO_ID + " INTEGER PRIMARY KEY, " +
                COL_VAG_VAGAS     + " INTEGER NOT NULL" +
                ");");

        criarTabelaDocumentos(db);
    }

    private void criarTabelaDocumentos(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_DOCUMENTOS + " (" +
                COL_DOC_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DOC_INSCRICAO_ID  + " INTEGER NOT NULL, " +
                COL_DOC_TIPO          + " TEXT NOT NULL, " +
                COL_DOC_NOME_ARQUIVO  + " TEXT, " +
                COL_DOC_CAMINHO_LOCAL + " TEXT, " +
                COL_DOC_STATUS        + " TEXT NOT NULL DEFAULT '" + DOC_STATUS_PENDENTE + "', " +
                COL_DOC_DATA_UPLOAD   + " TEXT, " +
                "FOREIGN KEY(" + COL_DOC_INSCRICAO_ID + ") REFERENCES " + TB_INSCRICOES + "(" + COL_INS_ID + ")" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            criarTabelaDocumentos(db);
        }
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TB_INSCRICOES +
                    " ADD COLUMN " + COL_INS_ACEITE_TERMOS + " TEXT");
        }
    }

    public long cadastrarUsuario(String nome, String cpf, String email, String telefone, String orgao, String cargo, String senhaHash) {
        ContentValues cv = new ContentValues();
        cv.put(COL_USR_NOME,       nome);
        cv.put(COL_USR_CPF,        cpf);
        cv.put(COL_USR_EMAIL,      email);
        cv.put(COL_USR_TELEFONE,   telefone);
        cv.put(COL_USR_ORGAO,      orgao);
        cv.put(COL_USR_CARGO,      cargo);
        cv.put(COL_USR_SENHA_HASH, senhaHash);
        return getWritableDatabase().insert(TB_USUARIOS, null, cv);
    }

    public Cursor loginUsuario(String cpf, String senhaHash) {
        return getReadableDatabase().query(
                TB_USUARIOS, null,
                COL_USR_CPF + "=? AND " + COL_USR_SENHA_HASH + "=?",
                new String[]{cpf, senhaHash},
                null, null, null);
    }

    public boolean cpfExiste(String cpf) {
        Cursor c = getReadableDatabase().query(
                TB_USUARIOS, new String[]{COL_USR_ID},
                COL_USR_CPF + "=?", new String[]{cpf},
                null, null, null);
        boolean existe = c.getCount() > 0;
        c.close();
        return existe;
    }

    public boolean jaInscrito(long usuarioId, int eventoId) {
        Cursor c = getReadableDatabase().query(
                TB_INSCRICOES, new String[]{COL_INS_ID},
                COL_INS_USUARIO_ID + "=? AND " + COL_INS_EVENTO_ID + "=?",
                new String[]{String.valueOf(usuarioId), String.valueOf(eventoId)},
                null, null, null);
        boolean inscrito = c.getCount() > 0;
        c.close();
        return inscrito;
    }

    public boolean inscrever(long usuarioId, int eventoId, String dataHoje,
                             String matricula, String nivelTi, String linkedin,
                             String areaAtuacao, String oab, String motivacao,
                             String aceiteTermos) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            int vagas = getVagasDisponiveis(eventoId);
            if (vagas <= 0) return false;

            ContentValues cv = new ContentValues();
            cv.put(COL_INS_USUARIO_ID,    usuarioId);
            cv.put(COL_INS_EVENTO_ID,     eventoId);
            cv.put(COL_INS_DATA,          dataHoje);
            cv.put(COL_INS_MATRICULA,     matricula);
            cv.put(COL_INS_NIVEL_TI,      nivelTi);
            cv.put(COL_INS_LINKEDIN,      linkedin);
            cv.put(COL_INS_AREA_ATUACAO,  areaAtuacao);
            cv.put(COL_INS_OAB,           oab);
            cv.put(COL_INS_MOTIVACAO,     motivacao);
            cv.put(COL_INS_ACEITE_TERMOS, aceiteTermos);
            long insId = db.insert(TB_INSCRICOES, null, cv);
            if (insId < 0) return false;

            db.execSQL("UPDATE " + TB_VAGAS +
                    " SET " + COL_VAG_VAGAS + " = " + COL_VAG_VAGAS + " - 1" +
                    " WHERE " + COL_VAG_EVENTO_ID + " = " + eventoId);

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public int getVagasDisponiveis(int eventoId) {
        Cursor c = getReadableDatabase().query(
                TB_VAGAS, new String[]{COL_VAG_VAGAS},
                COL_VAG_EVENTO_ID + "=?", new String[]{String.valueOf(eventoId)},
                null, null, null);
        if (c.moveToFirst()) {
            int v = c.getInt(0);
            c.close();
            return v;
        }
        c.close();
        return 0;
    }

    public void inicializarVagasSeNecessario(int eventoId, int vagasIniciais) {
        Cursor c = getReadableDatabase().query(
                TB_VAGAS, new String[]{COL_VAG_VAGAS},
                COL_VAG_EVENTO_ID + "=?", new String[]{String.valueOf(eventoId)},
                null, null, null);
        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            cv.put(COL_VAG_EVENTO_ID, eventoId);
            cv.put(COL_VAG_VAGAS,     vagasIniciais);
            getWritableDatabase().insert(TB_VAGAS, null, cv);
        }
        c.close();
    }

    public Cursor getInscricoesUsuario(long usuarioId) {
        return getReadableDatabase().query(
                TB_INSCRICOES, new String[]{COL_INS_EVENTO_ID, COL_INS_DATA},
                COL_INS_USUARIO_ID + "=?", new String[]{String.valueOf(usuarioId)},
                null, null, null);
    }

    public long getInscricaoId(long usuarioId, int eventoId) {
        Cursor c = getReadableDatabase().query(
                TB_INSCRICOES, new String[]{COL_INS_ID},
                COL_INS_USUARIO_ID + "=? AND " + COL_INS_EVENTO_ID + "=?",
                new String[]{String.valueOf(usuarioId), String.valueOf(eventoId)},
                null, null, null);
        long id = -1;
        if (c.moveToFirst()) id = c.getLong(0);
        c.close();
        return id;
    }

    public long salvarDocumento(long inscricaoId, String tipo, String nomeArquivo, String caminhoLocal) {
        ContentValues cv = new ContentValues();
        cv.put(COL_DOC_INSCRICAO_ID,  inscricaoId);
        cv.put(COL_DOC_TIPO,          tipo);
        cv.put(COL_DOC_NOME_ARQUIVO,  nomeArquivo);
        cv.put(COL_DOC_CAMINHO_LOCAL, caminhoLocal);
        cv.put(COL_DOC_STATUS,        DOC_STATUS_PENDENTE);
        return getWritableDatabase().insert(TB_DOCUMENTOS, null, cv);
    }

    public Cursor getDocumentosDaInscricao(long inscricaoId) {
        return getReadableDatabase().query(
                TB_DOCUMENTOS, null,
                COL_DOC_INSCRICAO_ID + "=?", new String[]{String.valueOf(inscricaoId)},
                null, null, null);
    }

    public boolean atualizarStatusDocumento(long documentoId, String novoStatus) {
        ContentValues cv = new ContentValues();
        cv.put(COL_DOC_STATUS, novoStatus);
        return getWritableDatabase().update(
                TB_DOCUMENTOS, cv,
                COL_DOC_ID + "=?", new String[]{String.valueOf(documentoId)}) > 0;
    }

    public int contarDocumentosPorStatus(long inscricaoId, String status) {
        Cursor c = getReadableDatabase().query(
                TB_DOCUMENTOS, new String[]{COL_DOC_ID},
                COL_DOC_INSCRICAO_ID + "=? AND " + COL_DOC_STATUS + "=?",
                new String[]{String.valueOf(inscricaoId), status},
                null, null, null);
        int total = c.getCount();
        c.close();
        return total;
    }

    public int contarDocumentosDaInscricao(long inscricaoId) {
        Cursor c = getReadableDatabase().query(
                TB_DOCUMENTOS, new String[]{COL_DOC_ID},
                COL_DOC_INSCRICAO_ID + "=?",
                new String[]{String.valueOf(inscricaoId)},
                null, null, null);
        int total = c.getCount();
        c.close();
        return total;
    }

    public boolean todosDocumentosEnviados(long inscricaoId) {
        Cursor c = getReadableDatabase().query(
                TB_DOCUMENTOS, new String[]{COL_DOC_ID},
                COL_DOC_INSCRICAO_ID + "=? AND " + COL_DOC_STATUS + " != ?",
                new String[]{String.valueOf(inscricaoId), DOC_STATUS_ENVIADO},
                null, null, null);
        boolean todos = c.getCount() == 0;
        c.close();
        return todos;
    }

    public String getAceiteTermos(long inscricaoId) {
        Cursor c = getReadableDatabase().query(
                TB_INSCRICOES, new String[]{COL_INS_ACEITE_TERMOS},
                COL_INS_ID + "=?", new String[]{String.valueOf(inscricaoId)},
                null, null, null);
        String aceite = null;
        if (c.moveToFirst()) aceite = c.getString(0);
        c.close();
        return aceite;
    }
}