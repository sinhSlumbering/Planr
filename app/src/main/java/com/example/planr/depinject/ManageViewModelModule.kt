package com.example.planr.depinject

import com.example.planr.data.repositories.TaskRepository
import com.example.planr.viewmodels.ManageViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ManageViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideManageViewModel(repository: TaskRepository): ManageViewModel {
        return ManageViewModel(repository)
    }
}
