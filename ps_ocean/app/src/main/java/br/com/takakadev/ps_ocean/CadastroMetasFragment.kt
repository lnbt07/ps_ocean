package br.com.takakadev.ps_ocean

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.takakadev.ps_ocean.databinding.FragmentCadastroMetasBinding
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

class CadastroMetasFragment : Fragment() {
    private var binding: FragmentCadastroMetasBinding? = null
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
        binding = FragmentCadastroMetasBinding.inflate(inflater, container, false)

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!metaId.equals("null")) consultar()

        binding?.btCadastrar?.setOnClickListener() {
            salvar()
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

    private fun salvar() {
        CoroutineScope(Dispatchers.IO).launch {
            val id = if(!metaId.equals("null")) metaId else database.child("meta").push().key

            val meta = Meta(
                id = id!!,
                nmeta = binding?.etNMeta?.text.toString(),
                nacoesunidas =  binding?.etNacoesUnidas?.text.toString(),
                descricao = binding?.etDescricao?.text.toString(),
                brasil = binding?.etBrasil?.text.toString(),
                indicadores = binding?.etIndicadores?.text.toString()
            )

            database.child("metas").child(id).setValue(meta).await()
            database.child("metasDetalhe").child(id).setValue(meta.descricao).await()
        }
    }


    private fun atualizarTela(meta: Meta) {
        binding?.etNMeta?.text?.append(meta.nmeta)
        binding?.etNacoesUnidas?.text?.append(meta.nacoesunidas)
        binding?.etDescricao?.text?.append(meta.descricao)
        binding?.etBrasil?.text?.append(meta.brasil)
        binding?.etIndicadores?.text?.append(meta.indicadores)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}