package dimitris.pallas.stoiximan.stoiximanapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dimitris.pallas.stoiximan.stoiximanapp.R
import dimitris.pallas.stoiximan.stoiximanapp.SportsModel
import dimitris.pallas.stoiximan.stoiximanapp.presentation.SportViewModel
import dimitris.pallas.stoiximan.stoiximanapp.presentation.SportViewModelFactory
import dimitris.pallas.stoiximan.stoiximanapp.presentation.adapter.SportsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_sport.*
import kotlinx.android.synthetic.main.fragment_sport.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SportFragment : Fragment() {
    private lateinit var viewModel: SportViewModel

    @Inject
    lateinit var viewModelFactory: SportViewModelFactory
    private lateinit var parentAdapter: SportsRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sport, container, false)
        view.parent_recycler_view.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        parentAdapter = SportsRecyclerViewAdapter()
        view.parent_recycler_view.adapter = parentAdapter
        setUpViewModel()
        observeLoader()
        observeWork()
        return view
    }


    private fun observeWork() {
        viewModel.sports.observe(
            this as LifecycleOwner
        ) { result ->
            if (result.getOrNull() != null) {
                renderData(result.getOrNull()!!)
            } else {
                Snackbar.make(
                    constraintLayout_profile,
                    R.string.generic_error,
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }


    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> loader.visibility = View.VISIBLE
                else -> loader.visibility = View.GONE
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SportViewModel::class.java)
    }

    private fun renderData(sports: List<SportsModel>) {
        parentAdapter.addData(sports)
        parentAdapter.notifyDataSetChanged()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SportFragment().apply { }
    }
}

