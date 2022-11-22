package com.example.ecarchargeinfo.info.presentation.viewmodel

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class InfoViewModel @Inject constructor(private val infoUseCase: InfoUseCase) : ViewModel() {


}


class InfoUseCase @Inject constructor(
    private val repository: InfoRepositoryImpl
) {
    operator fun invoke() {
        repository.test()
    }
}

interface InfoRepository {
    fun test()
}

class InfoRepositoryImpl @Inject constructor() : InfoRepository {
    override fun test() {
        println("asd")
        println("asd")
        println("asd")
        println("asd")
        println("asd")
        println("asd")
        println("asd")
        println("asd")
        println("asd")
        println("asd")
    }

}
