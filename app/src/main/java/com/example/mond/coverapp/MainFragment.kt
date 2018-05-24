package com.example.mond.coverapp

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.mond.coverapp.adapter.PostAdapter
import com.example.mond.coverapp.model.MockPostRepository
import com.example.mond.coverapp.model.Post
import com.example.mond.coverapp.model.User
import com.example.mond.coverapp.presenter.PostPresenter
import com.example.mond.coverapp.presenter.PostView
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), PostView {

    private lateinit var user: User

    private lateinit var presenter: PostPresenter
    private lateinit var mock: MockPostRepository

    private var adapter: PostAdapter? = null
    private var clickedHandler: ClickHandler? = null

    private var dialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun setPostList(posts: ArrayList<Post>) {
        adapter = PostAdapter(posts, user!!, clickedHandler as ClickedHandler)
        listView.adapter = adapter
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        clickedHandler = ClickedHandler()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = layoutManager

        user = arguments.getParcelable<User>("user")

        loadNewPostDialog()

        addPost.setOnClickListener(View.OnClickListener {
            dialog?.show()
        })

        mock = MockPostRepository()
        presenter = PostPresenter(this, mock)
        presenter.start()

        refresh.setOnClickListener(View.OnClickListener {
            presenter.update(null, null)
        })
    }

    fun loadNewPostDialog() {

        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        var inflater: LayoutInflater = layoutInflater
        var view: View = inflater.inflate(R.layout.newpostlayout, null)
        builder.setView(view)

        val type_item = arrayOf("Offer", "Request")
        val adapter_sortby = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, type_item)
        view.findViewById<Spinner>(R.id.spinner).adapter = adapter_sortby

        builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })
        builder.setPositiveButton("Post", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                var title: String? = view.findViewById<TextInputEditText>(R.id.title)?.text?.toString()
                var description: String? = view.findViewById<TextInputEditText>(R.id.des)?.text?.toString()
                var limit: Int? = null
                var price: Double? = null
                try {
                    limit = view.findViewById<EditText>(R.id.limit)?.text?.toString()?.toInt()
                    price = view.findViewById<EditText>(R.id.price)?.text?.toString()?.toDouble()
                } catch (e: NumberFormatException) {

                }
                var type: String? = view.findViewById<Spinner>(R.id.spinner)?.selectedItem?.toString()
                if (title != null && description != null && limit != null && price != null && type != null) {
                    if (type == "Request")
                        if (user.getBalance() < price * limit) {
                            val alertDialog = AlertDialog.Builder(
                                    activity).create()
                            alertDialog.setTitle("Hold on!");
                            alertDialog.setMessage("Not enough money :(\n(Total require " + (price * limit) + ")");
                            alertDialog.show();
                            return;
                        }
                    presenter.newPost(title, type, description, limit, price, user)
                } else {
                    val alertDialog = AlertDialog.Builder(
                            activity).create()
                    alertDialog.setTitle("Hold on!");
                    alertDialog.setMessage("Some fields are blank or wrong");
                    alertDialog.show();
                    return;
                }
            }
        })
        dialog = builder.create()
    }

    inner class ClickedHandler : ClickHandler {

        val alertDialog = AlertDialog.Builder(
                activity).create()

        init {
            alertDialog.setTitle("Hold on!");
            alertDialog.setMessage("Not enough money :(");
        }


        override fun onClick(post: Post) {
            if ((post.type == "Offer" && user.getBalance() >= post.price) || post.type == "Request")
                presenter.deal(post, user)
            else {
                alertDialog.show();
            }
        }
    }
}
