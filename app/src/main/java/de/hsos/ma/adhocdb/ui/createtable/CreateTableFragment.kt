package de.hsos.ma.adhocdb.ui.createtable

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.hsos.ma.adhocdb.R

class CreateTableFragment : Fragment() {

    companion object {
        fun newInstance() = CreateTableFragment()
    }

    private lateinit var viewModel: CreateTableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.create_table_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateTableViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
