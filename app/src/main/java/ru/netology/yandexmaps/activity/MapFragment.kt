package ru.netology.yandexmaps.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import kotlinx.coroutines.flow.collectLatest
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.databinding.MapFragmentBinding
import ru.netology.yandexmaps.databinding.PlaceBinding
import ru.netology.yandexmaps.viewmodel.MapViewModel


class MapFragment : Fragment() {

    private val viewModel by viewModels<MapViewModel>()
    private val placeTapListener = MapObjectTapListener { mapObject, _ ->
        viewModel.deletePlaceById(mapObject.userData as Long)
        true
    }

    // Добавляем точку при нажатии на карту (тап)
    private val inputListener = object : InputListener {
        // Handle single tap
        override fun onMapTap(map: Map, point: Point) {
        }

        // long tap
        override fun onMapLongTap(map: Map, point: Point) {
            Dialog.newInstance(length = point.latitude, width = point.longitude)
                .show(childFragmentManager, null)
        }
    }

    companion object {
        private const val ZOOM_STEP = 0.5F // Шаг смены масштаба
        const val LENGTH_KEY = "LENGTH_KEY"
        const val WIDTH_KEY = "WIDTH_KEY"
        private val start_animation = Animation(Animation.Type.SMOOTH, 5f)
        private val start_position = CameraPosition(Point(55.753544, 37.621202), 10f, 0f, 0f)

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
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_placesFragment)
        }

        // Переходим к точке на карте при выботе точки из списке или при добавлении точки на карте
        val arguments = arguments
        if (arguments != null && arguments.containsKey(LENGTH_KEY) && arguments.containsKey(
                WIDTH_KEY
            )
        ) {
            val cameraPosition = map.cameraPosition
            map.move(
                CameraPosition(
                    Point(
                        arguments.getDouble(LENGTH_KEY),
                        arguments.getDouble(WIDTH_KEY)
                    ), //точка с координатами
                    /* zoom = */ 17.0f, //величина необходимого приближения
                    cameraPosition.azimuth, //* azimuth = */ 0f, //азимут
                    cameraPosition.tilt //* tilt = */ 0f //наклон
                ),
                Animation(Animation.Type.SMOOTH, 5f), //плавное перемещение к точке
                null
            )

            arguments.remove(LENGTH_KEY)
            arguments.remove(WIDTH_KEY)
        } else {
            map.move(
                start_position,
                start_animation,
                null
            )
        }

        //  Проставляем метку на карте
        val collection = map.mapObjects.addCollection()
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.places.collectLatest { places ->
                collection.clear()
                places.forEach { place ->
                    val placeBinding = PlaceBinding.inflate(layoutInflater)
                    placeBinding.title.text = place.name
                    collection.addPlacemark(
                        Point(place.length, place.width),
                        ViewProvider(placeBinding.root)
                    ).apply {
                        userData = place.id
                    }
                }
            }
        }
        collection.addTapListener(placeTapListener)

        // Добавляем точку при нажатии на карту (тап)
        map.addInputListener(inputListener)

        return binding.root
    }

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //    MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    //    MapKitFactory.initialize(requireContext()) // Инициализация библиотеки для загрузки необходимых нативных библиотек.
    //}

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