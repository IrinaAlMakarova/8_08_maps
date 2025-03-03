package ru.netology.yandexmaps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.yandex.mapkit.MapKitFactory
import ru.netology.yandexmaps.BuildConfig
import ru.netology.yandexmaps.R

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this) // Инициализация библиотеки для загрузки необходимых нативных библиотек.
    }

}