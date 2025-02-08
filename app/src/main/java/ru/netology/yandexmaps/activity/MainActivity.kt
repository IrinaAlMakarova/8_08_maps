package ru.netology.yandexmaps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.maps.mobile.BuildConfig
import ru.netology.yandexmaps.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(R.layout.activity_main){
    private lateinit var mapView: MapView
    private lateinit var map: Map

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this) // Инициализация библиотеки для загрузки необходимых нативных библиотек.

        setContentView(R.layout.activity_main) //устанавливаем содержимое Activity из layout-файла
        mapView = findViewById(R.id.mapview)


        // положение или масштаб карты
        mapView.map.move(
            CameraPosition(
                Point(55.753544, 37.621202), //точка с координатами
                /* zoom = */ 17.0f, //величина необходимого приближения
                /* azimuth = */ 0f, //азимут
                /* tilt = */ 0f //наклон
            ),
            Animation(Animation.Type.SMOOTH, 5f), //плавное перемещение к точке
        null
        )
    }

    // Отображаем карты перед моментом, когда активити с картой станет видимой пользователю:
    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    // Останавливаем обработку карты, когда активити с картой становится невидимым для пользователя:
    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }
}