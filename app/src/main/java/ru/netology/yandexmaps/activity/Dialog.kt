package ru.netology.yandexmaps.activity

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.dto.Place
import ru.netology.yandexmaps.viewmodel.MapViewModel

class Dialog : DialogFragment() {

    companion object {
        private const val ID_KEY = "ID_KEY"
        private const val LENGTH_KEY = "LENGTH_KEY"
        private const val WIDTH_KEY = "WIDTH_KEY"
        fun newInstance(id: Long? = null, length: Double, width: Double) = Dialog().apply {
            arguments = bundleOf(ID_KEY to id, LENGTH_KEY to length, WIDTH_KEY to width)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {

        val viewModel by viewModels<MapViewModel>()
        val view = AppCompatEditText(requireContext())
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle(getString(R.string.name_place))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val text = view.text?.toString()?.takeIf { it.isNotBlank() } ?: run {
                    Toast.makeText(requireContext(), "Enter the name!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                viewModel.insertPlace(
                    Place(
                        id = requireArguments().getLong(ID_KEY),
                        name = text,
                        length = requireArguments().getDouble(LENGTH_KEY),
                        width = requireArguments().getDouble(WIDTH_KEY)
                    )
                )
                //findNavController().navigate(R.id.action_mapFragment_to_placesFragment)
                dismiss() // Закрыть диалог
            }.create()
    }
}