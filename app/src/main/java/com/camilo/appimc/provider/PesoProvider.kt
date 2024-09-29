package com.camilo.appimc.provider

import android.util.Log
import com.camilo.appimc.ModeloPeso
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PesoProvider {
    private var collection: CollectionReference = FirebaseFirestore.getInstance().collection("peso")


    fun crearPeso(peso: ModeloPeso): Task<Void> {
        val id = collection.document().id
        peso.id = id
        return collection.document(id).set(peso)
    }
    public fun getCarrerasBuscando(): Query {
        return collection.whereEqualTo("estado", "Constantes.ESTADOS_CARRERAS.BUSCANDO")
            .orderBy("timeStamp", Query.Direction.ASCENDING)
    }
    public fun getAllPeso():Query{
        return collection
    }

    public fun deletePeso(id: String) : Task<Void> {
        return collection.document(id).delete()
    }

}