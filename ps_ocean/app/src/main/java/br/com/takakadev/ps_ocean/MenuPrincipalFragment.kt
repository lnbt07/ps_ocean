package br.com.takakadev.ps_ocean

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import br.com.takakadev.ps_ocean.databinding.FragmentMenuPrincipalBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuPrincipalFragment : Fragment() {
    private var binding: FragmentMenuPrincipalBinding? = null
    var auth = Firebase.auth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btRelatorios?.setOnClickListener(){
            val action = MenuPrincipalFragmentDirections.actionMenuPrincipalFragmentToRelatoriosFragment()
            view.findNavController().navigate(action)
        }

        binding?.btSobre?.setOnClickListener(){
            val action = MenuPrincipalFragmentDirections.actionMenuPrincipalFragmentToSobreFragment()
            view.findNavController().navigate(action)
        }

        binding?.btLogOff?.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}