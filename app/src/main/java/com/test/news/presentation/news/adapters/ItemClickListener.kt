package com.test.news.presentation.news.adapters

interface ItemClickListener<in T>{
    fun click(item: T)
}