package com.clearintentions.app.config

import com.clearintentions.app.services.CallHandlerService
import com.clearintentions.app.services.LocationService
import com.clearintentions.app.services.LoginService
import com.clearintentions.app.services.RegistrationService
import com.clearintentions.server.LocationControllerApi
import com.clearintentions.server.LoginControllerApi
import com.clearintentions.server.RegistrationControllerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltActivityInjectionConfig {
    @Provides
    @Singleton
    fun baseUrl() = URL("http://192.168.1.66:8081")

    @Provides
    @Singleton
    fun loginControllerApi(basePath: URL) = LoginControllerApi(basePath = basePath.toString())

    @Provides
    @Singleton
    fun registrationControllerApi(basePath: URL) = RegistrationControllerApi(basePath = basePath.toString())

    @Provides
    @Singleton
    fun callHandlerService() = CallHandlerService()

    @Provides
    @Singleton
    fun loginService(
        loginControllerApi: LoginControllerApi,
        callHandlerService: CallHandlerService
    ) = LoginService(
        loginControllerApi,
        callHandlerService
    )

    @Provides
    @Singleton
    fun registrationService(
        registrationControllerApi: RegistrationControllerApi,
        callHandlerService: CallHandlerService
    ) = RegistrationService(
        registrationControllerApi,
        callHandlerService
    )

    @Provides
    @Singleton
    fun locationService(
        locationControllerApi: LocationControllerApi,
        callHandlerService: CallHandlerService
    ) = LocationService(
        locationControllerApi,
        callHandlerService
    )
}
