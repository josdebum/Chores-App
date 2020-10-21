package com.example.choresapp.Model

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month


class Chore() {
    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var timeAssigned: Long? = null
    var id: Int? = null

//    init {
//        this.choreName = choreName
//        this.assignedBy = assignedBy
//        this.assignedTo = assignedTo
//        this.timeAssigned = timeAssigned
//        this.id = id
//    }

    constructor(
        choreName: String, assignedBy: String,
        assignedTo: String, timeAssigned: Long,
        id: Int
    ) : this() {

        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assignedTo = assignedTo
        this.timeAssigned = timeAssigned
        this.id = id

    }

    fun showHumanDate(timeAssigned: Long): String {


        var dateFormat: java.text.DateFormat =  SimpleDateFormat("MM/dd/yyyy HH:mm")
        var formattedDate: String = dateFormat.format(Date(timeAssigned).time)

        return " ${formattedDate}"

    }

    override fun toString(): String {
        return "Chore(choreName=$choreName, assignedBy=$assignedBy, assignedTo=$assignedTo, timeAssigned=$timeAssigned, id=$id)"
    }

}