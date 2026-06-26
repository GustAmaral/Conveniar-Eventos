package com.projeto.conveniar_eventos.worker;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.projeto.conveniar_eventos.data.DatabaseHelper;

import java.io.File;

/**
 * Worker que processa documentos pendentes de uma inscrição.
 *
 * Atualmente opera em modo local: valida que o arquivo existe no dispositivo
 * e marca o documento como ENVIADO no banco. Quando a API estiver disponível,
 * substituir o corpo de processarDocumento() pela chamada Retrofit multipart.
 *
 * Inputs obrigatórios via Data:
 *   KEY_INSCRICAO_ID — long: ID da inscrição na TB_INSCRICOES
 */
public class DocumentUploadWorker extends Worker {

    public static final String KEY_INSCRICAO_ID = "inscricao_id";
    public static final String KEY_BEARER_TOKEN = "bearer_token";

    public DocumentUploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        long inscricaoId = getInputData().getLong(KEY_INSCRICAO_ID, -1);
        if (inscricaoId < 0) return Result.failure();

        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        boolean algumFalhou = false;

        try (Cursor cursor = db.getDocumentosDaInscricao(inscricaoId)) {
            int colId      = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DOC_ID);
            int colNome    = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DOC_NOME_ARQUIVO);
            int colCaminho = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DOC_CAMINHO_LOCAL);
            int colStatus  = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DOC_STATUS);

            while (cursor.moveToNext()) {
                if (DatabaseHelper.DOC_STATUS_ENVIADO.equals(cursor.getString(colStatus))) continue;

                long   docId   = cursor.getLong(colId);
                String nome    = cursor.getString(colNome);
                String caminho = cursor.getString(colCaminho);

                if (!processarDocumento(db, docId, nome, caminho)) {
                    algumFalhou = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry();
        }

        return algumFalhou ? Result.retry() : Result.success();
    }

    /**
     * Processa um único documento.
     * Modo local: confirma que o arquivo existe e marca como ENVIADO.
     * Para integrar com a API, substituir este método pela chamada Retrofit.
     */
    private boolean processarDocumento(DatabaseHelper db, long docId, String nome, String caminho) {
        if (caminho == null || !new File(caminho).exists()) {
            db.atualizarStatusDocumento(docId, DatabaseHelper.DOC_STATUS_REJEITADO);
            return false;
        }
        db.atualizarStatusDocumento(docId, DatabaseHelper.DOC_STATUS_ENVIADO);
        return true;
    }
}
