package app.pankaj.di

import app.pankaj.api.auth.data.UserDataSource
import app.pankaj.api.auth.data.UserDataSourceImp
import app.pankaj.api.auth.domain.UserRepository
import app.pankaj.api.auth.domain.UserRepositoryImp
import io.ktor.server.application.Application
import org.koin.dsl.module

val serverModule = module {
    factory { get<Application>().environment }
    single<UserDataSource> { UserDataSourceImp() }
    single<UserRepository> { UserRepositoryImp(get()) }

}