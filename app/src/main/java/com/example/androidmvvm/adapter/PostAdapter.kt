package com.example.androidmvvm.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvvm.activity.MainActivity
import com.example.androidmvvm.databinding.ItemPostListBinding
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.utils.Utils
import com.example.androidmvvm.utils.Utils.toast

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(ItemPostListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var post = items[position]
        if (holder is PostViewHolder) {
            holder.binding.apply {
                tvTitle.text = post.title
                tvBold.text = post.body
                tvTitle.setTypeface(tvTitle.typeface, Typeface.BOLD_ITALIC)

                llView.setOnLongClickListener(object : View.OnLongClickListener {
                    override fun onLongClick(p0: View?): Boolean {
                        deletePostDialog(post)
                        return true
                    }

                })

            }
        }
    }

    fun deletePostDialog(post: Post) {
        val title = "Delete"
        val body = "Do you want to delete?"

        Utils.customDialog(activity, title, body, object : Utils.DialogListener {
            override fun onPositiveClick() {
                activity.viewModel.apiPostDelete(post)
                activity.toast("Post which id is: ${post.id} has been deleted!")
                activity.viewModel.deletedPost.observe(activity,{
                    activity.viewModel.apiGetPostsList()
                })
            }

            override fun onNegativeClick() {
                activity.toast("Deleting post failed!")
            }

        })
    }

    override fun getItemCount(): Int = items.size

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: Post, position: Int) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun getData(): ArrayList<Post> {
        return items
    }

    class PostViewHolder(var binding: ItemPostListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}