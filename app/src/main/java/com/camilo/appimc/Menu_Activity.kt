package com.camilo.appimc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val etnombre = findViewById<EditText>(R.id.campoNombre)
        val btnEntrar = findViewById<AppCompatButton>(R.id.btnEntrar)

        btnEntrar.setOnClickListener{
            val nombre= etnombre.text.toString()

            if(nombre.isNotEmpty()){
                val intent = Intent(this, calcular_imc_activity::class.java)
                intent.putExtra("EXTRA_NOMBRE",nombre)
                startActivity(intent)

            }
        }
    }
}