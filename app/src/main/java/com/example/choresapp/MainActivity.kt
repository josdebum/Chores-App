package com.example.choresapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.choresapp.Data.ChoresDatabaseHandler
import com.example.choresapp.Model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dbHandler: ChoresDatabaseHandler? = null
    var progressDialog: ProgressDialog? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        dbHandler = ChoresDatabaseHandler(this)


        checkDB()


        saveChore.setOnClickListener {
            progressDialog!!.setMessage("Saving...")
            progressDialog!!.show()


            if (!TextUtils.isEmpty(enterChoreId.text.toString())
                && !TextUtils.isEmpty(assignToId.text.toString())
                &&  !TextUtils.isEmpty(assignedById.text.toString())) {
                //save to database
                var chore = Chore()
                chore.choreName = enterChoreId.text.toString()
                chore.assignedTo = assignToId.text.toString()
                chore.assignedBy = assignedById.text.toString()

                saveToDB(chore)
                progressDialog!!.cancel()

                startActivity(Intent(this, ChoreListActivity::class.java))


            } else {
                Toast.makeText(this, "Please enter a chore", Toast.LENGTH_LONG).show()
            }
        }

    }


    fun checkDB() {
        if (dbHandler!!.getChoresCount() != 0) {
            startActivity(Intent(this, ChoreListActivity::class.java))

        }
    }
    fun saveToDB(chore: Chore) {
        dbHandler!!.createChore(chore)
    }

}