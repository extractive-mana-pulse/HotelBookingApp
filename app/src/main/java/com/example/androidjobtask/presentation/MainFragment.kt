package com.example.androidjobtask.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.androidjobtask.R
import com.example.androidjobtask.databinding.FragmentMainBinding
import com.example.androidjobtask.presentation.api.API
import com.example.androidjobtask.presentation.data.date
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding

    val imageList = ArrayList<SlideModel>()

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        getData()

        binding.apply {

            roomSelection.setBackgroundColor(Color.parseColor("#0D72FF"))

            roomSelection.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_numberFragment)
            }

        }
    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://run.mocky.io/")
            .build()
            .create(API::class.java)
        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<date?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<date?>, response: Response<date?>) {
                val responseBody = response.body()!!
//                Glide.with(context!!)
//                    .load(repeat(responseBody.url.size) { }) // image url
//                    .placeholder(R.drawable.ic_launcher_foreground) // any placeholder to load at start
//                    .error(R.drawable.baseline_error_24)  // any image in case of error
//                    .override(200, 200) // resizing
//                    .centerCrop()
//                    .into(binding.imgSlider);  // imageview object

                val img = responseBody.url[0]
                val img1 = responseBody.url[1]
                val img2 = responseBody.url[2]

                imageList.add(SlideModel(img))
                imageList.add(SlideModel(img1))
                imageList.add(SlideModel(img2))

                binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

                binding.apply {
                    rating.text = responseBody.rating.toString()

                    ratingName.text = responseBody.rating_name

                    val y = responseBody.name

                    sharedViewModel.sharedPreferencesData.value = y

                    nameOfHotel.text = responseBody.name
                    address.text = responseBody.adress
                    price.text = "from" + " " + responseBody.minimal_price.toString() + " " + "₽"
                    priceForIt.text = responseBody.price_for_it

                    val textViews: List<TextView> = listOf(peculiarities1, peculiarities2, peculiarities3, peculiarities4)

                    responseBody.about_the_hotel.peculiarities.forEachIndexed { index, value ->
                        if (index < textViews.size) {
                            textViews[index].text = value
                        }
                    }

                    description.text = responseBody.about_the_hotel.description
                }
            }

            override fun onFailure(call: Call<date?>, t: Throwable) {
                Log.d("main", "onFailure"+t.message)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}