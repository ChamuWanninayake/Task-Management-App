package com.federicocotogno.goaltracker.ui.fragments.updategoal

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.federicocotogno.goaltracker.R
import com.federicocotogno.goaltracker.data.models.Goal
import com.federicocotogno.goaltracker.logic.utils.Calculations
import com.federicocotogno.goaltracker.ui.viewmodels.GoalViewModel
import kotlinx.android.synthetic.main.fragment_update_goal_item.*
import java.util.*

class UpdateGoalItem : Fragment(R.layout.fragment_update_goal_item),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private val args by navArgs<UpdateGoalItemArgs>()
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

        //Retrieve data from our goal list
        et_goalTitle_update.setText(args.selectedgoal.goal_title)
        et_goalDescription_update.setText(args.selectedgoal.goal_description)

        //Pick a drawable
        drawableSelected()

        //Pick the date and time again
        pickDateAndTime()

        //Confirm changes and update the selected item
        btn_confirm_update.setOnClickListener {
            updategoal()
        }

        //Show the options menu in this fragment
        setHasOptionsMenu(true)
    }

    private fun updategoal() {
        //Get text from editTexts
        title = et_goalTitle_update.text.toString()
        description = et_goalDescription_update.text.toString()

        //Create a timestamp string for our recyclerview
        timeStamp = "$cleanDate $cleanTime"

        //Check that the form is complete before submitting data to the database
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val goal =
                Goal(args.selectedgoal.id, title, description, timeStamp, drawableSelected)

            //add the goal if all the fields are filled
            goalViewModel.updateGoal(goal)
            Toast.makeText(context, "Goal updated! successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_updategoalItem_to_goalList)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    // Create a selector for our icons which will appear in the recycler view
    private fun drawableSelected() {
        iv_teaSelected_update.setOnClickListener {
            iv_teaSelected_update.isSelected = !iv_teaSelected_update.isSelected
            drawableSelected = R.drawable.baseline_check_circle_24

            //de-select the other options when we pick an image
            iv_fastFoodSelected_update.isSelected = false
            iv_smokingSelected_update.isSelected = false
            iv_Assig_Selected_update.isSelected = false
        }
        iv_fastFoodSelected_update.setOnClickListener {

            iv_fastFoodSelected_update.isSelected = !iv_fastFoodSelected_update.isSelected
            drawableSelected = R.drawable.baseline_badge_24

            //de-select the other options when we pick an image
            iv_Assig_Selected_update.isSelected = false
            iv_smokingSelected_update.isSelected = false
            iv_teaSelected_update.isSelected = false
        }

        iv_smokingSelected_update.setOnClickListener {
            iv_smokingSelected_update.isSelected = !iv_smokingSelected_update.isSelected
            drawableSelected = R.drawable.baseline_attach_money_24

            //de-select the other options when we pick an image
            iv_teaSelected_update.isSelected = false
            iv_Assig_Selected_update.isSelected = false
            iv_fastFoodSelected_update.isSelected = false
        }

        iv_Assig_Selected_update.setOnClickListener {
            iv_Assig_Selected_update.isSelected = !iv_Assig_Selected_update.isSelected
            drawableSelected = R.drawable.baseline_assignment_24

            //de-select the other options when we pick an image
            iv_teaSelected_update.isSelected = false
            iv_smokingSelected_update.isSelected = false
            iv_fastFoodSelected_update.isSelected = false
        }


    }
    //------------------------------------------

    //Handle date and time picking
    @RequiresApi(Build.VERSION_CODES.N)
    //set on click listeners for our data and time pickers
    private fun pickDateAndTime() {
        btn_pickDate_update.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime_update.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }

    }

    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected_update.text = "Time: $cleanTime"
    }

    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected_update.text = "Date: $cleanDate"
    }
    //------------------------------------------

    //Create options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_delete -> {
                deletegoal(args.selectedgoal)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //------------------------------------------

    //Delete a single goal
    private fun deletegoal(goal: Goal) {
        goalViewModel.deleteGoal(goal)
        Toast.makeText(context, "Goal successfully deleted!", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_updategoalItem_to_goalList)
    }
    //------------------------------------------

}