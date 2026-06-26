package com.projeto.conveniar_eventos.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Encapsula a seleção de documentos via sistema de arquivos do Android e a cópia
 * do arquivo escolhido para o diretório privado do app, expondo-o via FileProvider.
 *
 * Uso:
 *   1. Instanciar em onCreate() da Activity, passando o callback onDocumentSelecionado.
 *   2. Chamar open() quando o usuário tocar em "Selecionar arquivo".
 *   3. O callback devolve (tipoDocumento, nomeExibicao, caminhoLocal).
 */
public class DocumentPickerHelper {

    public interface Callback {
        void onDocumentSelecionado(String tipoDocumento, String nomeExibicao, String caminhoLocal);
        void onErro(String mensagem);
    }

    private final AppCompatActivity activity;
    private final Callback callback;
    private final ActivityResultLauncher<Intent> launcher;

    private String tipoDocumentoPendente;

    public DocumentPickerHelper(AppCompatActivity activity, Callback callback) {
        this.activity = activity;
        this.callback = callback;
        this.launcher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) processar(uri);
                    }
                }
        );
    }

    /** Abre o seletor de arquivos para o tipo de documento informado. */
    public void open(String tipoDocumento) {
        this.tipoDocumentoPendente = tipoDocumento;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"application/pdf", "image/*"});
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        launcher.launch(Intent.createChooser(intent, "Selecionar documento"));
    }

    private void processar(Uri uri) {
        String nome = resolverNome(uri);
        String destino = copiarParaDiretorioPrivado(uri, nome);
        if (destino != null) {
            callback.onDocumentSelecionado(tipoDocumentoPendente, nome, destino);
        } else {
            callback.onErro("Não foi possível processar o arquivo selecionado.");
        }
    }

    /** Devolve o URI de conteúdo (FileProvider) para um caminho local — usado por Retrofit multipart. */
    public static Uri obterUriParaEnvio(Context context, String caminhoLocal) {
        File file = new File(caminhoLocal);
        return FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                file
        );
    }

    // ── Helpers privados ─────────────────────────────────────────────

    private String resolverNome(Uri uri) {
        String nome = null;
        try (Cursor cursor = activity.getContentResolver()
                .query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (idx >= 0) nome = cursor.getString(idx);
            }
        } catch (Exception ignored) {}
        if (nome == null) {
            String path = uri.getPath();
            nome = path != null ? path.substring(path.lastIndexOf('/') + 1) : "documento";
        }
        return nome;
    }

    private String copiarParaDiretorioPrivado(Uri uri, String nomeArquivo) {
        File dir = new File(activity.getFilesDir(), "documentos");
        if (!dir.exists() && !dir.mkdirs()) return null;

        // Garante nome único para evitar colisão entre documentos do mesmo tipo
        File destino = new File(dir, System.currentTimeMillis() + "_" + nomeArquivo);
        try (InputStream in  = activity.getContentResolver().openInputStream(uri);
             FileOutputStream out = new FileOutputStream(destino)) {
            if (in == null) return null;
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            return destino.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}