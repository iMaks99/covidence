package com.sr.covidence.profile.pass

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.sr.covidence.R
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_template.*

class TemplateFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_template, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        template_toolbar.addView(v)
        v.profile_title.text = "Шаблон для заказа пропуска"
        v.profile_back_btn.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
        v.profile_settings_image.setImageDrawable(context!!.getDrawable(R.drawable.ic_done))
        v.profile_settings_image.setOnClickListener {
            saveData()
            fragmentManager!!.popBackStack()
        }

        buildTemplate()
    }

    private fun buildTemplate() {

        val passCode = pref.getString("passpord_code", "1")!!
        if (passCode == "1")
            type_passport_rf.isChecked = true
        if (passCode == "2")
            type_passport_foreign.isChecked = true
        if (passCode == "3")
            type_not_found.isChecked = true

        serial_pass_input.setText(pref.getString("serialPass", "")!!, TextView.BufferType.EDITABLE)

        number_pass_input.setText(pref.getString("numberPass", "")!!, TextView.BufferType.EDITABLE)

        val movingType = pref.getString("moveTypeButtonState", "1")!!
        if (movingType == "1")
            type_moving_car.isChecked = true
        if (movingType == "2")
            type_moving_bus.isChecked = true

        number_car_input.setText(pref.getString("number_car", "")!!, TextView.BufferType.EDITABLE)

        number_troika_input.setText(
            pref.getString("number_troika", "")!!,
            TextView.BufferType.EDITABLE
        )

        number_strelka_input.setText(
            pref.getString("number_strelka", "")!!,
            TextView.BufferType.EDITABLE
        )

        number_inn_input.setText(pref.getString("inn", "")!!, TextView.BufferType.EDITABLE)

        title_org_input.setText(pref.getString("titleOrg", "")!!, TextView.BufferType.EDITABLE)

    }

    private fun saveData() {
        val aim_code = "1"
        var passpord_code = ""
        if (type_passport_rf.isChecked)
            passpord_code = "1"
        else if (type_passport_foreign.isChecked)
            passpord_code = "2"
        else if (type_not_found.isChecked)
            passpord_code = "3"

        val serialPass = serial_pass_input.text.toString()

        val numberPass = number_pass_input.text.toString()

        var movingType = ""
        if (type_moving_car.isChecked)
            movingType = number_car_input.text.toString()
        if (type_moving_bus.isChecked)
            movingType =
                number_troika_input.text.toString() + "+" + number_strelka_input.text.toString()

        var moveTypeButtonState = ""
        if (type_moving_car.isChecked)
            moveTypeButtonState = "1"
        else if (type_moving_bus.isChecked)
            moveTypeButtonState = "2"

        val inn = number_inn_input.text.toString()

        val titleOrg = title_org_input.text.toString()

        val result = "Пропуск*" + aim_code + "*" + passpord_code + "*" + serialPass + "*" +
                numberPass + "*" + movingType + "*" + inn + "*" + titleOrg

        pref.edit().putString("templateForPass", result).apply()
        pref.edit().putBoolean("templateDone", true).apply()

        pref.edit().putString("passpord_code", passpord_code).apply()
        pref.edit().putString("serialPass", serialPass).apply()
        pref.edit().putString("numberPass", numberPass).apply()
        pref.edit().putString("moveTypeButtonState", moveTypeButtonState).apply()
        pref.edit().putString("number_car", number_car_input.text.toString()).apply()
        pref.edit().putString("number_troika", number_troika_input.text.toString()).apply()
        pref.edit().putString("number_strelka", number_strelka_input.text.toString()).apply()
        pref.edit().putString("inn", inn).apply()
        pref.edit().putString("titleOrg", titleOrg).apply()
    }

}
