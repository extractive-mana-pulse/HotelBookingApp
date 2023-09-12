package com.example.androidjobtask.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.androidjobtask.R
import com.example.androidjobtask.databinding.FragmentNumberBinding
import com.example.androidjobtask.presentation.api.API
import com.example.androidjobtask.presentation.data.NumberPageDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NumberFragment : Fragment() {

    private lateinit var binding : FragmentNumberBinding

    val imageList = ArrayList<SlideModel>()

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNumberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getData()

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        binding.apply {

            back.setOnClickListener{
                requireActivity().onBackPressed()
            }

            selectingNumber.setBackgroundColor(Color.parseColor("#0D72FF"))

            selectingNumber.setOnClickListener {
                findNavController().navigate(R.id.action_numberFragment_to_bookingFragment)
            }

            sharedViewModel.sharedPreferencesData.observe(viewLifecycleOwner) { data ->
                nameOfSelectedHotel.text = data
            }
        }
    }


    private fun getData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(API::class.java)

        apiService.getRooms().enqueue(object : Callback<NumberPageDate> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<NumberPageDate>, response: Response<NumberPageDate>) {
                if (response.isSuccessful) {
                    val roomsResponse = response.body()
                    if (roomsResponse != null) {
                        val rooms = roomsResponse.rooms
                        for (room in rooms) {
                            binding.apply {

                                val img = room.url[0]
                                val img1 = room.url[1]
                                val img2 = room.url[2]

                                imageList.add(SlideModel(img))
                                imageList.add(SlideModel(img1))
                                imageList.add(SlideModel(img2))

                                imageSlider.setImageList(imageList, ScaleTypes.FIT)

                                explanationOfHotel.text = room.name
                                val textViews: List<TextView> = listOf(peculiaritiesTV, peculiaritiesTV1)

                                room.peculiarities.forEachIndexed { index, value ->
                                    if (index < textViews.size) {
                                        textViews[index].text = value
                                    }
                                }
                                price.text = room.price.toString() + " ₽"
                                priceInfo.text = room.price_information
                            }
                        }
                    }
                } else {
                    //...
                }
            }

            override fun onFailure(call: Call<NumberPageDate>, t: Throwable) {
                Log.d("main", "onFailure"+t.message)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = NumberFragment()
    }
}