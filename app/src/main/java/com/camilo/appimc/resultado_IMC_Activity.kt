package com.camilo.appimc

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View

import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camilo.appimc.provider.PesoProvider
import com.camilo.appimc.util.Util
import com.google.firebase.FirebaseApp


class resultado_IMC_Activity : AppCompatActivity() {

    private lateinit var mPesoProvider: PesoProvider
    private lateinit var adapter: adapterTabla
    private val db = FirebaseFirestore.getInstance()
    private lateinit var lista: MutableList<ModeloPeso>

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_resultado_imc)
        lista= mutableListOf()
        mPesoProvider = PesoProvider()
        val rv_lista= findViewById<RecyclerView>(R.id.listaTabla)

        mPesoProvider.getAllPeso().get().addOnSuccessListener {
            for (document in it) {
                var peso = document.toObject(ModeloPeso::class.java)
                lista.add(peso)
                Log.d("modelo2", peso.nombre.toString())
            }

            rv_lista.layoutManager= LinearLayoutManager(this)

            adapter = adapterTabla(lista)
            rv_lista.adapter=  adapter

            adapter.notifyDataSetChanged()
        }









    }

}



