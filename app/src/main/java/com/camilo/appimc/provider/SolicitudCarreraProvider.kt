package com.camilo.appimc.provider

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class SolicitudCarreraProvider {
    private var mCollectionReference: CollectionReference = FirebaseFirestore.getInstance().collection("solicitudCarrera")

    constructor()


   /* public fun getCarrerasBuscando(): Query {
        return mCollectionReference.whereEqualTo("estado", Constantes.ESTADOS_CARRERAS.BUSCANDO)
            .orderBy("timeStamp", Query.Direction.ASCENDING)
    }
    public fun getCArrerasByMyPosition(): Query {
        return mCollectionReference.orderBy("timeStamp",Query.Direction.ASCENDING)
    }

    public fun getSolicitudCarrera(id: String): Task<DocumentSnapshot> {
        return mCollectionReference.document(id).get()
    }
    public fun deleteSolicitud(id: String) : Task<Void> {
        return mCollectionReference.document(id).delete()
    }*/

}