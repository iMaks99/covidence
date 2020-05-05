package com.sr.covidence.advice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sr.covidence.models.dto.AdviceDto
import com.sr.covidence.models.dto.AdviceListResponseDto
import com.sr.covidence.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdviceViewModel : ViewModel() {
    private val mBotRepository = NetworkService.instance!!.adviceEndpoint

    private var advice = MutableLiveData<AdviceDto>()

    fun getGeneralAdvice(): LiveData<AdviceDto> {
        mBotRepository!!.getAdviceGeneral()
            .enqueue(object : Callback<AdviceListResponseDto> {
                override fun onFailure(call: Call<AdviceListResponseDto>, t: Throwable) {
                    Log.w(this::class.java.name, t.message.toString())
                }

                override fun onResponse(
                    call: Call<AdviceListResponseDto>,
                    response: Response<AdviceListResponseDto>
                ) {
                    if (response.isSuccessful) {
                        advice.postValue(response.body()!!.data)
                    } else
                        Log.w(this::class.java.name, "Error in advice request")

                }
            })

        return advice
    }

}