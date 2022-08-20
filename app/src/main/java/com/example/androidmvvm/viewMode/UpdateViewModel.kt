package com.example.androidmvvm.viewMode

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.activity.UpdateActivity
import com.example.androidmvvm.adapter.PostAdapter
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateViewModel(val activity: UpdateActivity): ViewModel() {

    val updatedPost = MutableLiveData<Post>()

    fun updatePost(post: Post): LiveData<Post> {
        RetrofitHttp.postService.updatePost(post.id,post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                updatedPost.value = response.body()
                Toast.makeText(activity, "Post has been updated successfully! id: ${post.id}", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(activity, "Updating post failed!", Toast.LENGTH_SHORT).show()
                updatedPost.value = null
            }

        })
        return updatedPost
    }
}