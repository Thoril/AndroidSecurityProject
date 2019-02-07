package fr.isen.artru.androidtoolbox

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_cycle_de_vie.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CycleDeVieFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CycleDeVieFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CycleDeVieFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var lifeCycle: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        lifeCycle += "onCreate/"
        Log.i("LifeCycleFragment","onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("LifeCycleFragment","onCreateView")
        lifeCycle += "onCreateView/"
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cycle_de_vie, container, false)

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("LifeCycleFragment","onActivityCreated")
        lifeCycle += "onActivityCreated/"
    }

    override fun onStart() {
        super.onStart()
        Log.i("LifeCycleFragment","onStart")
        lifeCycle += "onStart/"
        lifeCycleFragment.text = lifeCycle
    }

    override fun onResume() {
        super.onResume()
        Log.i("LifeCycleFragment","onResume")
        lifeCycle += "onResume/"
        lifeCycleFragment.text = lifeCycle
    }

    override fun onPause() {
        super.onPause()
        Log.i("LifeCycleFragment","onPause")
        lifeCycle += "onPause/"
        lifeCycleFragment.text = lifeCycle
    }

    override fun onStop() {
        super.onStop()
        Log.i("LifeCycleFragment","onStop")
        lifeCycle += "onStop/"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("LifeCycleFragment","onDestroyView")
        lifeCycle += "onDestroyView/"
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LifeCycleFragment","onDestroy")
        lifeCycle += "onDestroy/"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        Log.i("LifeCycleFragment","onAttach")
        lifeCycle += "onAttach/"
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("LifeCycleFragment","onDetach")
        lifeCycle += "onDetach/"
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CycleDeVieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CycleDeVieFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
