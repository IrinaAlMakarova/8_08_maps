package ru.netology.yandexmaps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import ru.netology.yandexmaps.databinding.MapFragmentBinding

@Suppress("DEPRECATION")
class MapFragment : AppCompatActivity(){
    private lateinit var mapView: MapView
    private lateinit var map: Map

    // Отобразим метку на карте
    //val mapObjectTapListener = object : MapObjectTapListener {
    //    override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
    //        Toast.makeText(
    //            applicationContext,
    //            "Вы нажали на маркер",
    //            Toast.LENGTH_SHORT
    //        ).show()
    //        return true
    //    }
    //}

        companion object {
        private const val ZOOM_STEP =
            0.5F // Шаг смены масштаба. Поменяйте, если слишком резко или недостаточно
            const val LEN_KEY = "LEN_KEY"
            const val WID_KEY = "WID_KEY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey("MAPKIT_API_KEY")
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this) // Инициализация библиотеки для загрузки необходимых нативных библиотек.

        val binding = MapFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root) //устанавливаем содержимое Activity из layout-файла
        mapView = binding.mapview
        map = mapView.map

        // масштаб (увеличение)
        binding.plus.setOnClickListener {
            map.move(
                with(map.cameraPosition) { // берём текущую позицию
                    CameraPosition(target, zoom + ZOOM_STEP, azimuth, tilt) // Увеличиваем
                }
            )
        }

        // масштаб (уменьшение)
        binding.minus.setOnClickListener {
            map.move(
                with(map.cameraPosition) { // берём текущую позицию
                    CameraPosition(target, zoom - ZOOM_STEP, azimuth, tilt) // Уменьшаем
                }
            )
        }

        // добавить точку
        binding.locatoin.setOnClickListener{
            //todo
        }



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

        // Отобразим метку на карте
        //установка маркера
        //val marker = R.drawable.ic_pin_red
        //val mapObjects = mapView.map.mapObjects
        //val markObject = mapObjects.addPlacemark(
        //    startLocation,
        //    ImageProvider.fromResource(this, marker)
        //)
        //markObject.opacity = 0.5f
        //markObject.setText("Сity")
        //markObject.addTapListener(mapObjectTapListener)
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