package br.com.takakadev.ps_ocean

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import br.com.takakadev.ps_ocean.databinding.FragmentVisualizarMetaBinding
import br.com.takakadev.ps_ocean.model.Meta
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class VisualizarMetaFragment : Fragment() {
    private var binding: FragmentVisualizarMetaBinding? = null
    private lateinit var metaId: String
    private var database: DatabaseReference = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            metaId = it.getString("idMeta").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVisualizarMetaBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultar()

        binding?.btEditar?.setOnClickListener() {
            val action =
                VisualizarMetaFragmentDirections.actionVisualizarMetaFragmentToCadastroMetasFragment(
                    metaId
                )
            view.findNavController().navigate(action)
        }
    }

    private fun consultar() {
        CoroutineScope(Dispatchers.IO).launch {
            val dataSnapshot = database.child("metas").child(metaId).get().await()
            val meta = dataSnapshot.getValue<Meta>()

            withContext(Dispatchers.Main){
                atualizarTela(meta!!)
            }
        }
    }

    private fun atualizarTela(meta: Meta) {
        binding?.tvNMeta?.text = meta.nmeta
        binding?.tvAlteracaoBrasil?.text = meta.brasil
        binding?.tvDescricao?.text = meta.descricao
        binding?.tvIndicadores?.text = meta.indicadores
        binding?.tvNacoesUnidas?.text = meta.nacoesunidas
    }
}