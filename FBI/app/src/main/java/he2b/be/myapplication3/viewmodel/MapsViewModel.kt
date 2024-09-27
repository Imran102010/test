package he2b.be.myapplication3.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsViewModel: ViewModel() {

    private fun fieldOfficeToLatLng(fieldOffice: String): LatLng {
        return when (fieldOffice) {
            "albany" -> LatLng(42.651167, -73.754968)
            "albuquerque" -> LatLng(35.084103, -106.650985)
            "anchorage" -> LatLng(61.217381, -149.863129)
            "atlanta" -> LatLng(33.748547, -84.391502)
            "baltimore" -> LatLng(39.290386, -76.612190)
            "birmingham" -> LatLng(33.520682, -86.802433)
            "boston" -> LatLng(42.360081, -71.058884)
            "buffalo" -> LatLng(42.886448, -78.878372)
            "charlotte" -> LatLng(35.227085, -80.843124)
            "chicago" -> LatLng(41.878113, -87.629799)
            "cincinnati" -> LatLng(39.103119, -84.512016)
            "cleveland" -> LatLng(41.499321, -81.694359)
            "columbia" -> LatLng(34.000710, -81.034814)
            "dallas" -> LatLng(32.776665, -96.796989)
            "denver" -> LatLng(39.739236, -104.990251)
            "detroit" -> LatLng(42.331429, -83.045753)
            "elpaso" -> LatLng(31.761878, -106.485022)
            "honolulu" -> LatLng(21.306944, -157.858337)
            "houston" -> LatLng(29.760427, -95.369804)
            "indianapolis" -> LatLng(39.768403, -86.158068)
            "jackson" -> LatLng(32.298756, -90.184810)
            "jacksonville" -> LatLng(30.332184, -81.655651)
            "kansascity" -> LatLng(39.099728, -94.578568)
            "knoxville" -> LatLng(35.960638, -83.920739)
            "lasvegas" -> LatLng(36.169941, -115.139832)
            "littlerock" -> LatLng(34.746481, -92.289595)
            "losangeles" -> LatLng(34.052235, -118.243683)
            "louisville" -> LatLng(38.252665, -85.758456)
            "memphis" -> LatLng(35.149532, -90.048981)
            "miami" -> LatLng(25.761680, -80.191790)
            "milwaukee" -> LatLng(43.038902, -87.906474)
            "minneapolis" -> LatLng(44.977753, -93.265011)
            "mobile" -> LatLng(30.695366, -88.039891)
            "newhaven" -> LatLng(41.308274, -72.927883)
            "neworleans" -> LatLng(29.951065, -90.071533)
            "newyork" -> LatLng(40.712776, -74.005974)
            "newark" -> LatLng(40.735657, -74.172366)
            "norfolk" -> LatLng(36.850769, -76.285873)
            "oklahomacity" -> LatLng(35.467560, -97.516428)
            "omaha" -> LatLng(41.256537, -95.934502)
            "philadelphia" -> LatLng(39.952583, -75.165222)
            "phoenix" -> LatLng(33.448377, -112.074037)
            "pittsburgh" -> LatLng(40.440625, -79.995886)
            "portland" -> LatLng(45.515459, -122.679346)
            "richmond" -> LatLng(37.540725, -77.436048)
            "sacramento" -> LatLng(38.581572, -121.494400)
            "saltlakecity" -> LatLng(40.760780, -111.891045)
            "sanantonio" -> LatLng(29.424122, -98.493629)
            "sandiego" -> LatLng(32.715738, -117.161084)
            "sanfrancisco" -> LatLng(37.774929, -122.419416)
            "sanjuan" -> LatLng(18.465539, -66.105735)
            "seattle" -> LatLng(47.606209, -122.332071)
            "springfieldil" -> LatLng(39.781721, -89.650148)
            "stlouis" -> LatLng(38.627003, -90.199404)
            "tampa" -> LatLng(27.950575, -82.457178)
            "washington" -> LatLng(38.907192, -77.036871)
            else -> LatLng(0.0, 0.0)
        }
    }

    fun setupMap(googleMap: GoogleMap, fieldOffice: String) {
        val coordinates = fieldOfficeToLatLng(fieldOffice)
        googleMap.apply {
            addMarker(MarkerOptions().position(coordinates).title(fieldOffice))
            moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 10f))
        }
    }
}