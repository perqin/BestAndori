package io.github.bestandori.ui.widget

import androidx.fragment.app.Fragment

/**
 * A Fragment that can be regarded as a Navigation Destination.
 */
abstract class DestFragment : Fragment() {
    abstract val title: String
}
