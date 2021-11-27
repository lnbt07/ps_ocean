package br.com.takakadev.ps_ocean

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.takakadev.ps_ocean.adapter.ItemAdapter
import br.com.takakadev.ps_ocean.databinding.FragmentRelatoriosBinding
import br.com.takakadev.ps_ocean.model.Meta
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RelatoriosFragment : Fragment(),ItemAdapter.OnItemClickListener {
    private var binding: FragmentRelatoriosBinding? = null
    private var listametas = mutableListOf<Meta>()
    private lateinit var  recyclerView: RecyclerView
    private var database: DatabaseReference = Firebase.database.reference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRelatoriosBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding!!.rvMetas
        recyclerView.layoutManager = LinearLayoutManager(context)

        binding?.fab?.setOnClickListener(){
            val action = RelatoriosFragmentDirections.actionRelatoriosFragmentToCadastroMetasFragment(null)
            view.findNavController().navigate(action)
        }
        database.child("metas").addValueEventListener(metasListener)
    }


    private fun atualizarTela() {
        recyclerView.adapter = ItemAdapter(listametas, this)
    }

    override fun onItemClick(item: Int, acao: String) {
        val meta = listametas.get(item)
        if(acao.equals("VIEW")) navegarVisualizar(meta) else apagarRegistro(meta)
    }

    private fun navegarVisualizar(meta: Meta) {
        val action =
            RelatoriosFragmentDirections.actionRelatoriosFragmentToVisualizarMetaFragment(meta.id)
        this.findNavController().navigate(action)
    }

    private fun apagarRegistro(meta: Meta) {
        CoroutineScope(Dispatchers.IO).launch {
            database.child("metas").child(meta.id).removeValue().await()
            withContext(Dispatchers.Main){
                atualizarTela()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        atualizarTela()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    val metasListener = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val typeIndicator = object : GenericTypeIndicator<HashMap<String, Meta>>(){}
            val messages = snapshot.getValue(typeIndicator)
            listametas = mutableListOf<Meta>()
            messages?.forEach {k,meta -> listametas.add(meta)}
            atualizarTela()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("ERRO_LEITURA",error.message)
        }

    }

}