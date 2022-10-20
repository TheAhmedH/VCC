package com.veryable.android.di

import android.content.Context
import com.veryable.android.VeryableApplication
import com.veryable.android.adapters.AccountAdapter
import com.veryable.android.network.AccountsService
import com.veryable.android.repository.AccountsRepository
import com.veryable.android.repository.IAccountsRepository
import com.veryable.utils.Utils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getApplication(@ApplicationContext app: Context): VeryableApplication {
        return app as VeryableApplication
    }

    //Provide Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Provide AccountsService
    @Provides
    @Singleton
    fun provideAccountsService(retrofit: Retrofit): AccountsService {
        return retrofit.create(AccountsService::class.java)
    }

    //Provide Adapter
    @Provides
    @Singleton
    fun provideAllAdapter(): AccountAdapter {
        return AccountAdapter()
    }

    //Provide Repository
    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {
        @Binds
        @Singleton
        fun provideAccountsRepository(repository: AccountsRepository): IAccountsRepository
    }

}