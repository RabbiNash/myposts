package com.mtinashe.myposts.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load

import com.mtinashe.myposts.R
import com.mtinashe.myposts.data.entities.Comment
import com.mtinashe.myposts.data.entities.joins.JoinPostData
import com.mtinashe.myposts.ui.adapters.CommentsAdapter
import com.mtinashe.myposts.ui.viewmodels.ArticleViewModel
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel
import com.mtinashe.myposts.ui.viewmodels.factories.PostsViewModelFactory
import com.mtinashe.myposts.utils.StringUtils
import kotlinx.android.synthetic.main.article_fragment.*
import kotlinx.android.synthetic.main.article_fragment.tv_post_author
import kotlinx.android.synthetic.main.item_posts_layout.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ArticleFragment : Fragment(R.layout.article_fragment), KodeinAware {

    override val kodein: Kodein by kodein()

    private lateinit var viewModel: PostsViewModel
    private val viewModelFactory : PostsViewModelFactory by instance()
    private lateinit var commentsAdapter : CommentsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(PostsViewModel::class.java)

        //recycler view
        commentsAdapter = CommentsAdapter(requireContext())
        rv_comments.layoutManager = LinearLayoutManager(requireContext())
        rv_comments.adapter = commentsAdapter

        iv_back.setOnClickListener{
            findNavController().popBackStack()
        }

        arguments?.let {
            val postId = ArticleFragmentArgs.fromBundle(it).id
            viewModel.setPostId(postId)

            viewModel.post.observe(viewLifecycleOwner, Observer{ post ->
                bindUi(post)
            })

            viewModel.comments.observe(viewLifecycleOwner, Observer {comments ->
                setRecyclerItems(comments)
            })
        }
    }

    private fun bindUi(post : JoinPostData){
        tv_post_author.text = post.authorName
        tv_title.text = StringUtils.capitalize(post.postTitle)
        tv_post_body.text = StringUtils.capitalize(post.postBody)

        iv_post_image.load("https://via.placeholder.com/600x200"){
            this.placeholder(R.drawable.ic_launcher_foreground)
        }

        iv_avatar.load("https://api.adorable.io/avatars/111/abott@adorable.png")
    }

    private fun setRecyclerItems(comments : List<Comment>){
        if(::commentsAdapter.isInitialized) commentsAdapter.setItems(comments)
    }
}
