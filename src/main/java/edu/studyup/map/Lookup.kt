package edu.studyup.map

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

import org.json.JSONArray
import org.json.JSONObject

import edu.studyup.entity.Location

object Lookup {
    fun lookupPlace(query: String): Location? {
        val results = queryURL(query)
        if (results.isEmpty)
            return null
        // For now, simply return the location for the top result
        val best = results.getJSONObject(0)
        return getLocation(best)
    }

    private fun getLocation(location: JSONObject): Location {
        val lat = location.getDouble("lat")
        val lon = location.getDouble("lon")
        val bounds = DoubleArray(4)
        val boundingBox = location.getJSONArray("boundingbox")
        // Convert Nominatim API format (minLon, maxLon, minLat, maxLat) to OpenLayers
        // format (minLat, minLon, maxLat, maxLon)
        bounds[0] = boundingBox.getDouble(2)
        bounds[1] = boundingBox.getDouble(0)
        bounds[2] = boundingBox.getDouble(3)
        bounds[3] = boundingBox.getDouble(1)
        return Location(lat, lon, bounds)
    }

    private fun queryURL(query: String): JSONArray {
        var results = JSONArray()
        try {
            val urlString = ("https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(query, "UTF-8")
                    + "&format=json")
            val url = URL(urlString)
            try {
                BufferedReader(InputStreamReader(url.openStream())).use { `in` ->
                    val sb = StringBuilder()
                    var cp = `in`.read()

                    while (cp != -1) {
                        sb.append(cp.toChar())
                        cp = `in`.read()
                    }
                    `in`.close()
                    val content = sb.toString()
                    results = JSONArray(content)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        return results
    }
}
