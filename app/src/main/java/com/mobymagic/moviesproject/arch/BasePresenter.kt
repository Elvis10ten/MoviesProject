package com.mobymagic.moviesproject.arch

interface BasePresenter<in V: BaseView> {

    fun takeView(view: V)
    fun dropView()

}