package com.federicocotogno.goaltracker.ui.fragments.Goallist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.federicocotogno.goaltracker.data.models.Goal
import com.federicocotogno.goaltracker.logic.utils.Calculations
import com.federicocotogno.goaltracker.ui.fragments.Goallist.HabitListDirections
import com.federicocotogno.goaltracker.R
import kotlinx.android.synthetic.main.recycler_goal_item.view.*

class GoalListAdapter : RecyclerView.Adapter<GoalListAdapter.MyViewHolder>() {

    var goalList = emptyList<Goal>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.cv_cardView.setOnClickListener {
                val position = adapterPosition
                Log.d("GoalListAdapter", "Item clicked at: $position")

                val action =
                    HabitListDirections.actionGoalListToUpdategoalItem(goalList[position])
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_goal_item, parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentGoal = goalList[position]
        holder.itemView.iv_goal_icon.setImageResource(currentGoal.imageId)
        holder.itemView.tv_item_description.text = currentGoal.goal_description
        holder.itemView.tv_timeElapsed.text =
            Calculations.calculateTimeBetweenDates(currentGoal.goal_startTime)
        holder.itemView.tv_item_createdTimeStamp.text = "Since: ${currentGoal.goal_startTime}"
        holder.itemView.tv_item_title.text = "${currentGoal.goal_title}"
    }

    override fun getItemCount(): Int {
        return goalList.size
    }

    fun setData(goal: List<Goal>) {
        this.goalList = goal
        notifyDataSetChanged()
    }
}
