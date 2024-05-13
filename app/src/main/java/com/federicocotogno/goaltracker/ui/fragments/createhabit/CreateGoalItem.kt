package com.federicocotogno.goaltracker.ui.fragments.creategoal

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.federicocotogno.goaltracker.R
import com.federicocotogno.goaltracker.data.models.Goal
import com.federicocotogno.goaltracker.logic.utils.Calculations
import com.federicocotogno.goaltracker.ui.viewmodels.GoalViewModel
import kotlinx.android.synthetic.main.fragment_create_goal_item.*
import java.util.*

class CreateGoalItem : Fragment(R.layout.fragment_create_goal_item),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private lateinit var goalViewModel: GoalViewModel

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        goalViewModel = ViewModelProvider(this).get(GoalViewModel::class.java)

        //Add goal to database
        btn_confirm.setOnClickListener {
            addgoalToDB()
        }
        //Pick a date and time
        pickDateAndTime()

        //Selected and image to put into our list
        drawableSelected()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    //set on click listeners for our data and time pickers
    private fun pickDateAndTime() {
        btn_pickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }
    }

    private fun addgoalToDB() {

        //Get text from editTexts
        title = et_goalTitle.text.toString()
        description = et_goalDescription.text.toString()

        //Create a timestamp string for our recyclerview
        timeStamp = "$cleanDate $cleanTime"

        //Check that the form is complete before submitting data to the database
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val goal = Goal(0, title, description, timeStamp, drawableSelected)

            //add the goal if all the fields are filled
            goalViewModel.addGoal(goal)
            Toast.makeText(context, "Goal created successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_creategoalItem_to_goalList)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    // Create a selector for our icons which will appear in the recycler view
    private fun drawableSelected() {
        iv_teaSelected.setOnClickListener {
            iv_teaSelected.isSelected = !iv_teaSelected.isSelected
            drawableSelected = R.drawable.baseline_check_circle_24

            //de-select the other options when we pick an image
            iv_fastFoodSelected.isSelected = false
            iv_smokingSelected.isSelected = false
            iv_Assig_Selected.isSelected = false
        }

        iv_fastFoodSelected.setOnClickListener {

            iv_fastFoodSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.baseline_badge_24

            //de-select the other options when we pick an image
            iv_smokingSelected.isSelected = false
            iv_Assig_Selected.isSelected = false
            iv_teaSelected.isSelected = false
        }

        iv_smokingSelected.setOnClickListener {
            iv_smokingSelected.isSelected = !iv_smokingSelected.isSelected
            drawableSelected = R.drawable.baseline_attach_money_24

            //de-select the other options when we pick an image
            iv_fastFoodSelected.isSelected = false
            iv_teaSelected.isSelected = false
            iv_Assig_Selected.isSelected = false
        }

        iv_Assig_Selected.setOnClickListener {
            iv_Assig_Selected.isSelected = !iv_Assig_Selected.isSelected
            drawableSelected = R.drawable.baseline_assignment_24

            //de-select the other options when we pick an image
            iv_fastFoodSelected.isSelected = false
            iv_smokingSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }


    }

    //get the time set
    override fun onTimeSet(TimePicker: TimePicker?, p1: Int, p2: Int) {
        Log.d("Fragment", "Time: $p1:$p2")

        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected.text = "Time: $cleanTime"
    }

    //get the date set
    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {

        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected.text = "Date: $cleanDate"
    }

    //get the current time
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    //get the current date
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

}