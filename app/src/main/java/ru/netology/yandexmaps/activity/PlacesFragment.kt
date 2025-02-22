package ru.netology.yandexmaps.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.MapFragment
import kotlinx.coroutines.flow.collectLatest
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.adapter.OnInteractionListener
import ru.netology.yandexmaps.adapter.PlacesAdapter
import ru.netology.yandexmaps.databinding.PlacesFragmentBinding
import ru.netology.yandexmaps.dto.Place
import ru.netology.yandexmaps.viewmodel.MapViewModel

class PlacesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PlacesFragmentBinding.inflate(inflater, container, false)
        val viewModel by viewModels<MapViewModel>()

        val adapter = PlacesAdapter(onInteractionListener = object : OnInteractionListener {

            override fun onClick(place: Place) {
                findNavController().navigate(
                    R.id.action_placesFragment_to_mapFragment, bundleOf(
                        MapFragment.LEN_KEY to place.length,
                        MapFragment.WID_KEY to place.width
                    )
                )
            }

            override fun onDelete(place: Place) {
                viewModel.deletePlaceById(place.id)
            }

            override fun onEdit(place: Place) {
                //TODO
            }
        })

        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.places.collectLatest { places ->
                adapter.submitList(places)
                binding.empty.isVisible = places.isEmpty()
            }
        }

        return binding.root
    }
}