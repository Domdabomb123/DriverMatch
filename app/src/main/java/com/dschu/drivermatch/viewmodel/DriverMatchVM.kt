package com.dschu.drivermatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dschu.drivermatch.model.DataAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DriverMatchVM : ViewModel() {

    private val vowels = setOf('a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'y', 'Y', 'u', 'U')
    private val locationList: MutableLiveData<List<String>?> = MutableLiveData()
    val driverList: MutableLiveData<List<String>?> = MutableLiveData()
    val locationMatch: MutableLiveData<String?> = MutableLiveData(null)

    fun readJsonData(jsonString: String) {
        val dataAssetType = object : TypeToken<DataAsset>() {}.type
        val dataAsset: DataAsset = Gson().fromJson(jsonString, dataAssetType)

        val drivers = ArrayList<String>()
        val locations = ArrayList<String>()

        for (location in dataAsset.shipments) {
            var highestScore = 0
            var bestDriver = ""
            driverCheck@ for (driver in dataAsset.drivers) {
                if (drivers.contains(driver)) continue@driverCheck
                var locationScore = if (location.length % 2 == 0) { //even
                    (driver.count { char -> char in vowels } * 1.5).toInt()
                } else { //odd
                    driver.length - driver.count { char -> char in vowels }
                }
                if (location.length == driver.length) {
                    locationScore = (locationScore * 1.5).toInt()
                }
                if (highestScore < locationScore) {
                    highestScore = locationScore
                    bestDriver = driver
                }
            }
            drivers.add(bestDriver)
            locations.add(location)
        }

        //Added to list in same order as best match
        driverList.value = drivers
        locationList.value = locations
    }

    fun matchDriver(driver: String) {
        driverList.value?.indexOf(driver)?.let {
            locationMatch.value = locationList.value?.get(it)
        }
    }
}