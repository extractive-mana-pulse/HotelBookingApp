package com.example.androidjobtask.presentation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidjobtask.R
import com.example.androidjobtask.databinding.FragmentBookingBinding
import com.example.androidjobtask.presentation.adapters.TouristAdapter
import com.example.androidjobtask.presentation.api.API
import com.example.androidjobtask.presentation.data.BookingDate
import com.example.androidjobtask.presentation.data.Item
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class BookingFragment : Fragment() {

    private lateinit var binding: FragmentBookingBinding

    private val items = mutableListOf<Item>()

    private lateinit var adapter: TouristAdapter

    private var g = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookingBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val dateOfBirthLayout = view.findViewById<TextInputLayout>(R.id.textInputLayoutDateOfBirth)

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)

        }

        getData()

        binding.apply {

            phoneNumber.addTextWatcherForButton(pay)

            emailAddress.addTextWatcherForButton(pay)

//            phoneNumber.doOnTextChanged { text, _, _, _ ->
//                if (text!!.length > 10) {
//                    textInputLayout.error = "incorrect phone number!"
//                } else if (text.length < 10) {
//                    textInputLayout.error = null
//                }
//            }

            val clear: ImageButton = view.findViewById(R.id.clear)

            clear.setOnClickListener {
                if (items.isNotEmpty()) {
                    adapter.removeItem(items.size - 1)
                }
            }

            dateOfBirthLayout.setEndIconOnClickListener {
                Toast.makeText(requireContext(), "end icon", Toast.LENGTH_SHORT).show()
                DatePickerDialog(requireActivity(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            }

            adapter = TouristAdapter(items)
            rcView.layoutManager = LinearLayoutManager(requireActivity())
            rcView.adapter = adapter

            buttonAdd.setOnClickListener {
                val newItem = Item("Tourist ${items.size + 1}")
                adapter.addItem(newItem)
            }

            expandable.setOnClickListener {
                if (g == 0){
                    g=1
                    expandable.expand()
                }else{
                    g=0
                    expandable.collapse()
                }
            }

            back.setOnClickListener {
                requireActivity().onBackPressed()
            }

            pay.setBackgroundColor(Color.parseColor("#0D72FF"))

            pay.setOnClickListener {

                val phone = phoneNumber.text.toString().trim()
                val email = emailAddress.text.toString().trim()

                if (phone.isEmpty() && email.isEmpty()){
                    textInputLayout.error = "Field can't be empty"
                    textInputLayout1.error = "Field can't be empty"
                }
                findNavController().navigate(R.id.action_bookingFragment_to_payedFragment)
            }
        }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val birthday = requireView().findViewById<TextInputEditText>(R.id.DateOfBirth)
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        birthday.setText(sdf.format(myCalendar.time))
    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://run.mocky.io/")
            .build()
            .create(API::class.java)
        val retrofitData = retrofitBuilder.getDataB()

        retrofitData.enqueue(object : Callback<BookingDate?> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<BookingDate?>, response: Response<BookingDate?>) {
                val responseBody = response.body()!!

                binding.apply {

                    rating.text = responseBody.horating.toString()

                    ratingName.text = responseBody.rating_name

                    nameOfHotel.text = responseBody.hotel_name

                    address.text = responseBody.hotel_adress

                    departure.text = responseBody.departure

                    arrivalCountry.text = responseBody.arrival_country

                    startDateEndDate.text =
                        responseBody.tour_date_start + " - " + responseBody.tour_date_start

                    nightsAmount.text = responseBody.number_of_nights.toString()

                    hotelName.text = responseBody.hotel_name

                    typeOfHotel.text = responseBody.room

                    nutritionOfHotel.text = responseBody.nutrition

                    forTour.text = responseBody.tour_price.toString() + " ₽"

                    fuelSurcharge.text = responseBody.fuel_charge.toString() + " ₽"

                    serviceSurcharge.text = responseBody.service_charge.toString() + " ₽"

                    val sumAll =
                        responseBody.tour_price + responseBody.fuel_charge + responseBody.service_charge

                    toPay.text = "$sumAll ₽"

                    pay.text = "Pay " + toPay.text.toString()

                }
            }

            override fun onFailure(call: Call<BookingDate?>, t: Throwable) {
                Log.d("main", "onFailure"+t.message)
            }
        })
    }

    private fun EditText.addTextWatcherForButton(button: Button) {
        this.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                button.isEnabled = !TextUtils.isEmpty(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookingFragment()
    }
}

