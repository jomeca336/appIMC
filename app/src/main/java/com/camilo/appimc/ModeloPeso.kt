package com.camilo.appimc

 class ModeloPeso {

     var id: String = ""
    var edad:Int = 1

    lateinit var sexo:String
    lateinit var nombre:String
    lateinit var estadoPeso:String

    constructor(nombre: String,sexo: String,edad: Int,estadoPeso: String){
        this.nombre = nombre
        this.sexo = sexo
        this.edad = edad
        this.estadoPeso=estadoPeso
    }
     constructor()
}