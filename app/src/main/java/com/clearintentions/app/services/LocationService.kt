package com.clearintentions.app.services

import com.clearintentions.server.LocationControllerApi

class LocationService(
    val locationControllerApi: LocationControllerApi,
    val callHandlerService: CallHandlerService
) {
    fun getNearbyUsers(x: Double, y: Double, radius: Int) = callHandlerService.handle { locationControllerApi.findNearbyUsers(x, y, radius) }
}
