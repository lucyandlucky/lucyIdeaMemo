package com.example.lucyideamemo.hilt

import com.example.lucyideamemo.db.AppDatabase
import com.example.lucyideamemo.db.repo.TagNoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideNoteRepository(appDatabase: AppDatabase) =
        TagNoteRepo(appDatabase.getNoteDao(), appDatabase.getTagNoteDao())
}