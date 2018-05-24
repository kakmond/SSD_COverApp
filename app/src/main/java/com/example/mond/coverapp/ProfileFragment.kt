package com.example.mond.coverapp

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.mond.coverapp.adapter.DealAdapter
import com.example.mond.coverapp.model.Post
import com.example.mond.coverapp.model.User
import com.example.mond.coverapp.presenter.DealPresenter
import com.example.mond.coverapp.presenter.DealView
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfileFragment() : Fragment(), DealView {

    private lateinit var presenter: DealPresenter
    private lateinit var user: User
    private var dealClickedHandler: ClickHandler? = null
    private var postClickedHandler: ClickHandler? = null

    override fun setMoney(money: Double) {
        baht.text = money.toString() + " ฿"
    }

    override fun setPostList(posts: ArrayList<Post>) {
        mypost.adapter = DealAdapter(posts, postClickedHandler!!)
    }

    override fun setDealList(posts: ArrayList<Post>) {
        dealItem.adapter = DealAdapter(posts, dealClickedHandler!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments.getParcelable<User>("user")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addmoney.setOnClickListener(View.OnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.moneydialog, null)
            dialogBuilder.setView(dialogView)

            val editText = dialogView.findViewById<View>(R.id.editTextName) as EditText

            dialogBuilder.setTitle("Add money")
            dialogBuilder.setMessage("Enter money below")
            dialogBuilder.setPositiveButton("Add", DialogInterface.OnClickListener { dialog, whichButton ->
                try {
                    var amount: Double = editText.text.toString().toDouble()
                    user.addMoney(amount)
                } catch (e: NumberFormatException) {
                    val alertDialog = AlertDialog.Builder(
                            activity).create()
                    alertDialog.setTitle("Hold on!");
                    alertDialog.setMessage("field are blank or wrong");
                    alertDialog.show();
                }
            })
            dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton ->
                //pass
            })
            val b = dialogBuilder.create()
            b.show()
        })

        withdraw.setOnClickListener(View.OnClickListener {

            val dialogBuilder = AlertDialog.Builder(context)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.moneydialog, null)
            dialogBuilder.setView(dialogView)

            val editText = dialogView.findViewById<View>(R.id.editTextName) as EditText

            dialogBuilder.setTitle("Add money")
            dialogBuilder.setMessage("Enter money that you want to withdraw")
            dialogBuilder.setPositiveButton("Withdraw", DialogInterface.OnClickListener { dialog, whichButton ->
                try {
                    var amount: Double = editText.text.toString().toDouble()
                    if (user.getBalance() < amount) {
                        val alertDialog = AlertDialog.Builder(
                                activity).create()
                        alertDialog.setTitle("Hold on!");
                        alertDialog.setMessage("Not enough money to withdraw");
                        alertDialog.show();
                    } else
                        user.withdraw(amount)
                } catch (e: NumberFormatException) {
                    val alertDialog = AlertDialog.Builder(
                            activity).create()
                    alertDialog.setTitle("Hold on!");
                    alertDialog.setMessage("field are blank or wrong");
                    alertDialog.show();
                }
            })
            dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton ->
                //pass
            })
            val b = dialogBuilder.create()
            b.show()
        })

        dealClickedHandler = DealClickedHandler()
        postClickedHandler = PostClickedHandler()

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        dealItem.layoutManager = layoutManager

        val layoutManager2 = LinearLayoutManager(context)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        mypost.layoutManager = layoutManager2

        initializeUserProfile()
        presenter = DealPresenter(this, user)
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun initializeUserProfile() {
        name.text = user?.name
        email.text = user?.email
        tel.text = user?.tel
    }

    inner class DealClickedHandler : ClickHandler {

        val alertDialog = AlertDialog.Builder(
                context).create()

        init {
            initializeDialog()
        }

        fun initializeDialog() {
            alertDialog.setTitle("User's contact");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", DialogInterface.OnClickListener
            { dialog, which -> alertDialog?.dismiss() })
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "REPORT", DialogInterface.OnClickListener
            { dialog, which -> Toast.makeText(context, "Report submitted ,you will be refund if report is accepted ", Toast.LENGTH_LONG).show() })
        }

        override fun onClick(position: Post) {
            var post_head: Post = user.dealList.get(user.dealList.indexOf(position))
            var information: User = post_head.postBy
            var email = information.email
            var id = information.id
            var name = information.name
            var tel = information.tel
            alertDialog.setMessage("Post title: " + post_head.title + " (" + post_head.type + ")" + "\nName: " + name + "\n"
                    + "Email: " + email + "\n" + "Telephone number: " + tel + "\n" + "ID number " + id);
            alertDialog.show();
        }
    }

    inner class PostClickedHandler : ClickHandler {


        override fun onClick(position: Post) {
            val alertDialog = AlertDialog.Builder(
                    context).create()
            alertDialog.setTitle("User's contact");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", DialogInterface.OnClickListener
            { dialog, which -> alertDialog?.dismiss() })
            var txt: StringBuilder = StringBuilder()
            var post_head: Post = user.postList.get(user.postList.indexOf(position))
            var information: ArrayList<User> = post_head.dealBy
            for (users in information) {
                var email = users.email
                var id = users.id
                var name = users.name
                var tel = users.tel
                txt.append("Name: " + name + "\n"
                        + "Email: " + email + "\n" + "Telephone number: " + tel + "\n" + "ID number " + id + "\n")
            }
            if (txt.isEmpty())
                txt.append("Post title: " + post_head.title + " (" + post_head.type + ")\n" + "No dealer yet")
            else
                txt.append("Post title: " + post_head.title + " (" + post_head.type + ")\n" + txt)

            if (!post_head.isFull())
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "TURN OFF DEALING", DialogInterface.OnClickListener
                { dialog, which ->
                    if (post_head.type == "Request") {
                        var rest: Int = post_head.dealAmount - post_head.dealBy.size
                        var moneyBack: Double = rest * post_head.price
                        Toast.makeText(context, "DEALING CLOSED: receive money back  " + moneyBack + " Baht.", Toast.LENGTH_LONG).show()
                    }
                    user.unpost(user.postList.get(user.postList.indexOf(position)))
                })
            else
                txt.append("\nThis post has been closed (full)")
            alertDialog.setMessage(txt)
            alertDialog.show();
        }
    }
}
