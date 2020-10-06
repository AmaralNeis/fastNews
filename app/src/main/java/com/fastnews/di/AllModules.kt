package com.fastnews.di

import com.fastnews.repository.PostRepository
import com.fastnews.viewmodel.CommentViewModel
import com.fastnews.viewmodel.PostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postModule = module {
    factory { PostRepository }
    viewModel { PostViewModel(get()) }
}

val commentModule = module {
    viewModel { CommentViewModel() }
}

val allModules = listOf(postModule, commentModule)