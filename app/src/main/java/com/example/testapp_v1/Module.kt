package com.example.testapp_v1

import com.example.testapp_v1.model.api.ApiService
import com.example.testapp_v1.model.api.ConnectivityInterceptor
import com.example.testapp_v1.model.api.ConnectivityInterceptorImpl
import com.example.testapp_v1.model.database.DB
import com.example.testapp_v1.repositories.Repository
import com.example.testapp_v1.repositories.RepositoryImpl
import com.example.testapp_v1.repositories.documentRepository.DocumentRepository
import com.example.testapp_v1.repositories.documentRepository.DocumentRepositoryImpl
import com.example.testapp_v1.repositories.tdsRepository.TdsRepository
import com.example.testapp_v1.repositories.tdsRepository.TdsRepositoryImpl
import com.example.testapp_v1.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        RepositoryImpl(
            DB(context = androidContext()).dataDao(),
            DB(context = androidContext()).getBranchDataDao(),
            DB(context = androidContext()).getTdsDataDao(),
            ApiService(get())
        ) as Repository
    }
    single {
        DocumentRepositoryImpl(
            DB(context = androidContext()).getInventoryDao(),
            DB(context = androidContext()).detailsDao()
        ) as DocumentRepository
    }
    single {
        TdsRepositoryImpl(
            DB(context = androidContext()).getTdsDataDao(),
            DB(context = androidContext()).detailsDao(),
            DB(context = androidContext()).getInventoryDao()
        ) as TdsRepository
    }
    single { ApiService(get()) }
    single { ConnectivityInterceptorImpl(context = androidContext()) as ConnectivityInterceptor }

    viewModel { SettingsViewModel(get(),get()) }
    viewModel { OCDataViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SelectFilialViewModel(get()) }

}