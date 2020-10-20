package com.example.choresapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.choresapp.Data.ChoreListAdapter
import com.example.choresapp.Data.ChoresDatabaseHandler
import com.example.choresapp.Model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {
    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var dialogBuilder: androidx.appcompat.app.AlertDialog.Builder? = null
    private var dialog: androidx.appcompat.app.AlertDialog? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler = ChoresDatabaseHandler(context = this)


        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreListItems!!, this)


        // setup list = recyclerview
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        //Load our chores
        choreList = dbHandler!!.readChores()
        choreList!!.reverse()



        for (c in choreList!!.iterator()) {

            val chore = Chore()
            chore.choreName = "Chore: ${c.choreName}"
            chore.assignedBy = "Assigned By: ${c.assignedBy}"
            chore.assignedTo = "Assigned to: ${c.assignedTo}"
            chore.id = c.id
            chore.showHumanDate(c.timeAssigned!!)


//            Log.d("====ID=====", c.id.toString())
//            Log.d("====Name=====", c.choreName)
//            Log.d("====Date=====", chore.showHumanDate(c.timeAssigned!!))
//            Log.d("====aTo=====", c.assignedTo)
//            Log.d("====aBy=====", c.assignedTo)
            choreListItems!!.add(chore)


        }
        adapter!!.notifyDataSetChanged()


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.add_menu_button) {
            Log.d("Item Clicked", "Item Clicked")

            createPopupDialog()
        }

        return super.onOptionsItemSelected(item)


    }


    fun createPopupDialog() {

        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.popEnterChore
        var assignedBy = view.popEnterAssignedBy
        var assigendTo = view.popEnterAssignedTo
        var saveButton = view.popSaveChore

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        saveButton.setOnClickListener {
            var name = choreName.text.toString().trim()
            var aBy = assignedBy.text.toString().trim()
            var aTo = assigendTo.text.toString().trim()

            if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(aBy)
                && !TextUtils.isEmpty(aTo)
            ) {
                var chore = Chore()

                chore.choreName = name
                chore.assignedTo = aTo
                chore.assignedBy = aBy

                dbHandler!!.createChore(chore)

                dialog!!.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()


            } else {

            }
        }


    }


}