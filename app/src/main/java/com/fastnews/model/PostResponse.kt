package com.fastnews.model

data class PostResponse(val data: PostDataChild)

data class PostDataChild(val children: List<PostChildren>, val after: String)

data class PostChildren(val data: DataResponse)

data class Preview(val images: List<PreviewImage>?)

data class PreviewImage(val id: String, val source: PreviewImageSource?)

data class PreviewImageSource(val url: String?, val width: Int, val height: Int)




