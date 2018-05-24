package com.example.mond.coverapp

import com.example.mond.coverapp.model.Post

interface ClickHandler {
    fun onClick(position: Post)
}