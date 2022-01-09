package com.example.formulario

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.formulario.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var name:String = ""
    private var last:String = ""
    private var height:String = ""
    private var borned:String = ""
    private var country:String = ""
    private var origin:String = ""
    private var notes:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // binding.tvSaludo.setText("Hola Mackenzie")

        val countries = arrayOf("Argentina", "Bolivia", "Colombia",
            "Ecuador", "España", "Estadops Unidos",
            "Mexico", "Panama", "Peru", "Uruguay")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        binding.acCountry.setAdapter(adapter)
        binding.acCountry.setOnItemClickListener { adapterView, view, i, l ->
            binding.tietOrigen.requestFocus()
            Toast.makeText(this, "You Selected ${countries.get(i)}", Toast.LENGTH_SHORT).show()
        }

        binding.tietBorn.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()
            picker.addOnPositiveButtonClickListener { timeInMiliSeconds ->
                val dateStr = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }.format(timeInMiliSeconds)
                binding.tietBorn.setText(dateStr)
            }

            picker.show(supportFragmentManager, picker.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_send){
            if (validFields()) {
                name = binding.tietName.text.toString().trim()
                last = binding.tietLast.text.toString().trim()
                height = binding.tietHeight.text.toString().trim()
                borned = binding.tietBorn.text.toString().trim()
                country = binding.acCountry.text.toString().trim()
                origin = binding.tietOrigen.text.toString().trim()
                notes = binding.tietNotes.text.toString().trim()
                // Toast.makeText(this, "$name $last", Toast.LENGTH_SHORT).show()

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.dialog_title))
                builder.setMessage(joinData(name, last, height, borned, country, origin, notes))
                builder.setPositiveButton(getString(R.string.dialog_ok), { dialogInterface, i ->
                    with(binding) {
                        tietName.text?.clear()
                        tietLast.text?.clear()
                        tietHeight.text?.clear()
                        tietBorn.text?.clear()
                        acCountry.text?.clear()
                        tietOrigen.text?.clear()
                        tietNotes.text?.clear()
                    }
                    Toast.makeText(this, "Data Cleared!", Toast.LENGTH_SHORT).show()
                })
                builder.setNegativeButton(getString(R.string.dialog_cancel), null)

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

        } else if (item.itemId == R.id.action_exit){
            Toast.makeText(this, "Saliendo de la aplicacion..", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun joinData(vararg fields: String): String {
        var result = ""

        fields.forEach { field ->
            if (field.isNotEmpty()) {
                result += "$field\n"
            }

        }


        return result
    }

    private fun validFields(): Boolean {
        var isValid = true
        binding.tilName.error = null
        binding.tilLast.error = null
        binding.tilHeight.error = null
        binding.tilHeight.error = null

        if (binding.tietName.text.isNullOrEmpty()){
            binding.tilName.run {
                error = getString(R.string.help_required)
                requestFocus()
            }
            isValid = false
            Toast.makeText(this, "Falta el nombre", Toast.LENGTH_SHORT).show()
        } else if (binding.tietLast.text.isNullOrEmpty()) {
            binding.tilLast.run {
                error = getString(R.string.help_required)
                requestFocus()
            }
            isValid = false
            Toast.makeText(this, "Falta el apellido", Toast.LENGTH_SHORT).show()
        } else if(binding.tietHeight.text.isNullOrEmpty()) {
            binding.tietHeight.run {
                error = getString(R.string.help_required)
                requestFocus()
            }
            isValid = false
            Toast.makeText(this, "Falta La Estatura", Toast.LENGTH_SHORT).show()
        } else if(binding.tietHeight.text.toString().toInt() < 50) {
            binding.tietHeight.run {
                error = getString(R.string.help_min_valid_height)
                requestFocus()
            }
            isValid = false
            Toast.makeText(this, "Se Requiere Estatura Minima", Toast.LENGTH_SHORT).show()
        }

        return isValid
    }

}