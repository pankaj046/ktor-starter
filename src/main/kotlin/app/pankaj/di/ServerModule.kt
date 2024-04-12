package app.pankaj.di

import io.ktor.server.application.Application
import org.koin.dsl.module

val serverModule = module {
    factory { get<Application>().environment }

}