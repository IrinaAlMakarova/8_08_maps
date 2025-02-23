package ru.netology.yandexmaps.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import ru.netology.yandexmaps.databinding.MapFragmentBinding

class MapFragment : Fragment() {
    companion object {
        private const val ZOOM_STEP = 0.5F // Шаг смены масштаба
        const val LEN_KEY = "LEN_KEY"
        const val WID_KEY = "WID_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MapFragmentBinding.inflate(layoutInflater, container, false)
        val mapView = binding.mapview
        val map = mapView.mapWindow.map

        viewLifecycleOwner.lifecycle.addObserver(
            object : LifecycleEventObserver {
                override fun onStateChanged(
                    source: LifecycleOwner,
                    event: Lifecycle.Event
                ) {
                    when (event) {
                        Lifecycle.Event.ON_START -> startMap(mapView)
                        Lifecycle.Event.ON_STOP -> stopMap(mapView)
                        else -> Unit
                    }
                }
            }
        )

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
        binding.locatoin.setOnClickListener {
            //todo
        }


        // положение или масштаб карты
        map.move(
            CameraPosition(
                Point(55.753544, 37.621202), //точка с координатами
                /* zoom = */ 17.0f, //величина необходимого приближения
                /* azimuth = */ 0f, //азимут
                /* tilt = */ 0f //наклон
            ),
            Animation(Animation.Type.SMOOTH, 5f), //плавное перемещение к точке
            null
        )

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext()) // Инициализация библиотеки для загрузки необходимых нативных библиотек.
    }


    // Отображаем карты перед моментом, когда активити с картой станет видимой пользователю:
    private fun startMap(mapView: MapView) {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    // Останавливаем обработку карты, когда активити с картой становится невидимым для пользователя:
    private fun stopMap(mapView: MapView) {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }
}