package com.adamcoding.prototype.di

import com.adamcoding.prototype.data.repository.ClassificationRepositoryImpl
import com.adamcoding.prototype.domain.repository.ClassificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideClassificationRepository(classificationRepositoryImpl: ClassificationRepositoryImpl): ClassificationRepository
}