package com.example.androidjobtask.presentation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.androidjobtask.R
import com.example.androidjobtask.databinding.FragmentNumberBinding
import com.example.androidjobtask.presentation.api.API
import com.example.androidjobtask.presentation.data.NumberPageDate
import com.example.androidjobtask.presentation.data.Room
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
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://run.mocky.io/")
            .build()
            .create(API::class.java)
        val retrofitData = retrofitBuilder.getDataN()

        retrofitData.enqueue(object : Callback<NumberPageDate?>{

            override fun onResponse(call: Call<NumberPageDate?>, response: Response<NumberPageDate?>) {
                val responseBody = response.body()!!

                    binding.apply {

//                        val img = responseBody.url[0]
//                        val img1 = responseBody.url[1]
//                        val img2 = responseBody.url[2]
//
//                        imageList.add(SlideModel(img))
//                        imageList.add(SlideModel(img1))
//                        imageList.add(SlideModel(img2))
//
//                        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
//
//                        explanationOfHotel.text = responseBody.name
//
//                        price.text = responseBody.price.toString()
//
//                        priceInfo.text = responseBody.price_per
//
//                        val textViews: List<TextView> = listOf(peculiaritiesTV, peculiaritiesTV1)
//
//                        responseBody.peculiarities.forEachIndexed { index, value ->
//                            if (index < textViews.size) {
//                                textViews[index].text = value
//                            }
//                        }
                    }
            }

            override fun onFailure(call: Call<NumberPageDate?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = NumberFragment()
    }
}