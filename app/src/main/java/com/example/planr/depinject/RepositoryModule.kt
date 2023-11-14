package com.example.planr.depinject

import com.example.planr.data.repositories.TaskRepoImpl
import com.example.planr.data.repositories.TaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepo(
        firebaseFirestore: FirebaseFirestore,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): TaskRepository {
        return TaskRepoImpl (
            firebaseFirestore,
            ioDispatcher
        )
    }
}