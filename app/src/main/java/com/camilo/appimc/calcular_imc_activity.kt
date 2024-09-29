package com.camilo.appimc

import android.content.Context
import android.content.Intent
import android.graphics.Color

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.camilo.appimc.provider.PesoProvider
import com.camilo.appimc.util.Util
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


class calcular_imc_activity : AppCompatActivity() {

    private lateinit var mPesoProvider: PesoProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_calcular_imc)

        mPesoProvider = PesoProvider()


        var womanIsSelected = false
        var manIsSelected = false
        var peso = 60
        var edad = 26
        var altura: Int
        var estadoPeso: String

        val cardWoman = findViewById<CardView>(R.id.femenino)
        val cardMen = findViewById<CardView>(R.id.masculino)
        val textAltura = findViewById<TextView>(R.id.altura)
        val seekBarAltura = findViewById<SeekBar>(R.id.sliderAltura)
        val textEdad = findViewById<TextView>(R.id.textEdad)
        val textPeso = findViewById<TextView>(R.id.textPeso)
        val minPeso = findViewById<Button>(R.id.minPeso)
        val morPeso = findViewById<Button>(R.id.morPeso)
        val minEdad = findViewById<Button>(R.id.minEdad)
        val morEdad = findViewById<Button>(R.id.morEdad)
        val btnCalcular = findViewById<AppCompatButton>(R.id.btnCalcular)
        val btnResultados = findViewById<AppCompatButton>(R.id.btnResultados)
        val nombre = findViewById<EditText>(R.id.aquiVaNombre)

        minPeso.setOnClickListener {
            if (peso > 0) {
                peso--
                textPeso.text = "$peso"
            }
        }

        morPeso.setOnClickListener {
            peso++
            textPeso.text = "$peso"
        }

        minEdad.setOnClickListener {
            if (edad > 0) {
                edad--
                textEdad.text = "$edad"
            }
        }

        morEdad.setOnClickListener {
            edad++
            textEdad.text = "$edad"
        }

        altura = seekBarAltura.progress
        seekBarAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualizar el valor del TextView con la altura seleccionada
                textAltura.text = "$progress cm"
                altura = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No es necesario implementar aquí
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No es necesario implementar aquí
            }
        })

        cardWoman.setOnClickListener {
            womanIsSelected = true
            manIsSelected = false

            cardWoman.setCardBackgroundColor(Color.parseColor("#1f1d1d"))
            cardMen.setCardBackgroundColor(Color.parseColor("#898989"))
        }

        cardMen.setOnClickListener {
            womanIsSelected = false
            manIsSelected = true

            cardMen.setCardBackgroundColor(Color.parseColor("#1f1d1d"))
            cardWoman.setCardBackgroundColor(Color.parseColor("#898989"))
        }

        nombre.maxLines = 1
        nombre.inputType = InputType.TYPE_CLASS_TEXT

        nombre.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(nombre.windowToken, 0)
                true
            } else {
                false
            }
        }




        btnCalcular.setOnClickListener {


            if (nombre.text.isNotEmpty()) {
                val imc = calcularIMC(peso, altura)

                estadoPeso = comprobarPeso(imc)

                val persona = ModeloPeso(
                    nombre.text.toString(),
                    if (womanIsSelected == true) "Femenino" else "Masculino",
                    edad,
                    estadoPeso
                )

                Util.modelo_peso.LISTA_MODELO_PESO.add(persona)
                Log.d("modelo", persona.sexo.toString())
                mPesoProvider.crearPeso(persona).addOnSuccessListener {
                    Toast.makeText(this, "Peso creado correctamente", Toast.LENGTH_SHORT).show()
                }



                nombre.setText("")
                cardWoman.setCardBackgroundColor(Color.parseColor("#898989"))
                cardMen.setCardBackgroundColor(Color.parseColor("#898989"))
                womanIsSelected = false
                manIsSelected = false
                peso = 60
                edad = 26
                altura = 0
                textPeso.text = "$peso"

            }


        }

        btnResultados.setOnClickListener {
            val intent = Intent(this, resultado_IMC_Activity::class.java)

            startActivity(intent)
        }

    }
}

fun calcularIMC(peso: Int, altura: Int): Double {

    val alturaEnMetros = altura / 100.0
    val imc = peso / (alturaEnMetros * alturaEnMetros)

    return imc
}


class adapterTabla(val list: MutableList<ModeloPeso>) :
    RecyclerView.Adapter<adapterTabla.ViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nombre: TextView
        var sexo: TextView
        var edad: TextView
        var estadoPeso: TextView
        var borrar: ImageView

        init {
            nombre = view.findViewById(R.id.nombreTabla)
            sexo = view.findViewById(R.id.sexoTabla)
            edad = view.findViewById(R.id.edadTabla)
            estadoPeso = view.findViewById(R.id.estadoPesoTabla)
            borrar = view.findViewById(R.id.borrar)

        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.mensaje, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = list[position]
        holder.nombre.text = p.nombre
        holder.sexo.text = p.sexo
        holder.edad.text = p.edad.toString()
        holder.estadoPeso.text = p.estadoPeso
        Log.d("si entro", "si")
        holder.borrar.setOnClickListener {
            eliminarElemento(p.id,holder)
        }


    }


    override fun getItemCount(): Int {
        return list.size
    }



    private fun eliminarElemento(id: String, holder: ViewHolder) {

        var mPesoProvider = PesoProvider()
        mPesoProvider.deletePeso(id).addOnSuccessListener {
            Toast.makeText(holder.itemView.context, "Peso eliminado correctamente", Toast.LENGTH_SHORT).show()
        }

    }

}


fun comprobarPeso(imc: Double): String {

    var texto: String = ""

    when {
        imc < 18.5 -> {

            texto = "Bajo peso"
        }

        imc >= 18.5 && imc <= 24.9 -> {
            texto = "Peso normal"
        }

        imc >= 25.0 && imc <= 29.9 -> {
            texto = "Sobre peso"
        }

        imc >= 30.0 && imc <= 34.9 -> {
            texto = "Obesidad grado 1"
        }

        imc >= 35.0 && imc <= 39.9 -> {
            texto = "Obesidad grado 2"
        }

        imc >= 40.0 -> {
            texto = "Obesidad grado 3"
        }
    }


    return texto

}