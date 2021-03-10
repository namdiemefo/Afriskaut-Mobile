package com.naemo.afriskaut.views.fragments.home

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.naemo.afriskaut.BR
import com.naemo.afriskaut.R
import com.naemo.afriskaut.databinding.HomeFragmentBinding
import com.naemo.afriskaut.utils.AfricanCountry
import com.naemo.afriskaut.utils.AppUtils
import com.naemo.afriskaut.views.activities.pages.scout.ScoutActivity
import com.naemo.afriskaut.views.base.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>(), HomeNavigator {

    var homeViewModel: HomeViewModel? = null
        @Inject set

    var mBinder: HomeFragmentBinding? = null

    var appUtils: AppUtils? = null
        @Inject set

    private var mLayoutId = R.layout.home_fragment

    internal var position = arrayOf("--Select Position--", "Goal Keeper", "Defender", "Midfielder", "Attacker")
    internal var africanCountry: Array<AfricanCountry> = arrayOf(
        AfricanCountry("Algeria", "\uD83C\uDDE9\uD83C\uDDFF"),
        AfricanCountry("Angola", "\uD83C\uDDE6\uD83C\uDDF4"),
        AfricanCountry("Benin", "\uD83C\uDDE7\uD83C\uDDEF"),
        AfricanCountry("Botswana", "\uD83C\uDDE7\uD83C\uDDFC")
    )
    internal var countries = arrayOf("--Select Nation--",
        "Algeria \uD83C\uDDE9\uD83C\uDDFF",
        "Angola",
        "Benin",
        "Botswana",
        "Burkina Faso",
        "Burundi",
        "Cabo Verde",
        "Cameroon",
        "Central African Republic",
        "Chad",
        "Comoros",
        "Democratic Republic of the Congo",
        "Republic of the Congo",
        "Cote d'Ivoire",
        "Djibouti",
        "Egypt",
        "Equatorial Guinea",
        "Eritrea",
        "Eswatini",
        "Ethiopia",
        "Gabon",
        "Gambia",
        "Ghana",
        "Guinea",
        "Guinea-Bissau",
        "Kenya",
        "Lesotho",
        "Liberia",
        "Libya",
        "Madagascar",
        "Malawi",
        "Mali",
        "Mauritania",
        "Mauritius",
        "Morocco",
        "Mozambique",
        "Namibia",
        "Niger",
        "Nigeria",
        "Rwanda",
        "Sao Tome and Principe",
        "Senegal",
        "Seychelles",
        "Sierra Leone",
        "Somalia",
        "South Africa",
        "South Sudan",
        "Sudan",
        "Tanzania",
        "Togo",
        "Tunisia",
        "Uganda",
        "Zambia",
        "Zimbabwe")
    private lateinit var countryArrayAdapter: ArrayAdapter<String>
    private lateinit var positionArrayAdapter: ArrayAdapter<String>
    lateinit var nationSelected: String
    lateinit var positionSelected: String

    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doBinding()
        initViews()
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = homeViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    private fun initViews() {
        countryArrayAdapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line,
            countries
        ) {
            override fun isEnabled(position: Int): Boolean {
                return if (position == 0) {
                    return false
                } else true
            }

            @RequiresApi(Build.VERSION_CODES.O)
            @TargetApi(Build.VERSION_CODES.M)
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(requireContext().getColor(R.color.colorHomeText))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                    tv.textSize = 12f
                } else {
                    tv.setTextColor(requireContext().getColor(R.color.colorHomeText))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                    tv.textSize = 12f
                }
                return view
            }

            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(Build.VERSION_CODES.M)
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(requireContext().getColor(R.color.colorHint))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                    tv.textSize = 12f
                } else {
                    tv.setTextColor(requireContext().getColor(R.color.colorHomeText))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                    tv.textSize = 12f

                }
                return view
            }
        }

        nation.adapter = countryArrayAdapter
        nation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                nationSelected = if (p2 == 0) {
                    " "
                } else {
                    countries.let { it[p2] }
                }

            }

        }


        positionArrayAdapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, position) {
            override fun isEnabled(position: Int): Boolean {
                return if (position == 0) {
                    return false
                } else true
            }

            @RequiresApi(Build.VERSION_CODES.O)
            @TargetApi(Build.VERSION_CODES.M)
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(requireContext().getColor(R.color.colorHomeText))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                    tv.textSize = 12f
                } else {
                    tv.setTextColor(requireContext().getColor(R.color.colorHomeText))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                    tv.textSize = 12f
                }
                return view
            }

            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(Build.VERSION_CODES.M)
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(requireContext().getColor(R.color.colorHint))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                    tv.textSize = 12f
                } else {
                    tv.setTextColor(requireContext().getColor(R.color.colorHomeText))
                    tv.setTypeface(typeface, Typeface.ITALIC)
                    tv.textSize = 12f

                }
                return view
            }

        }

        player_position.adapter = positionArrayAdapter
        player_position.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                positionSelected = position[p2]
            }

        }

    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun getViewModel(): HomeViewModel? {
        return homeViewModel
    }



    override fun moveToScoutPage() {
        val intent = Intent(requireContext(), ScoutActivity::class.java)
        startActivity(intent)
    }

    override fun showSnackBarMessage(msg: String) {
        appUtils?.showSnackBar(requireContext().applicationContext, home_frame, msg)
    }

    override fun scout() {
        hideKeyBoard()
        getViewModel()?.scoutPlayer(nationSelected, positionSelected)
    }

    override fun showSpin() {
        appUtils?.showDialog(requireContext())
    }

    override fun hideSpin() {
        appUtils?.cancelDialog()
    }
}
