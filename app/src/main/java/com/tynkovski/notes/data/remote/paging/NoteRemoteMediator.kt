package com.tynkovski.notes.data.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tynkovski.notes.data.local.databases.AppDatabase
import com.tynkovski.notes.data.remote.services.NoteApi
import com.tynkovski.notes.domain.models.Note

@OptIn(ExperimentalPagingApi::class)
class NoteRemoteMediator(
    api: NoteApi,
    database: AppDatabase,
) : RemoteMediator<Int, Note>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Note>
    ): MediatorResult {
        TODO("com.tynkovski.notes.data.remote.paging.NoteRemoteMediator is not finished.")
    }
}