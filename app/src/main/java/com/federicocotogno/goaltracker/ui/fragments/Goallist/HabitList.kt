package com.federicocotogno.goaltracker.ui.fragments.Goallist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.federicocotogno.goaltracker.R
import com.federicocotogno.goaltracker.data.models.Goal
import com.federicocotogno.goaltracker.ui.fragments.Goallist.adapters.GoalListAdapter
//import com.federicocotogno.goaltracker.ui.fragments.habitlist.adapters.HabitListAdapter
import com.federicocotogno.goaltracker.ui.viewmodels.GoalViewModel
import kotlinx.android.synthetic.main.fragment_goal_list.*

class HabitList : Fragment(R.layout.fragment_goal_list) {

    private lateinit var goalList: List<Goal>
    private lateinit var goalViewModel: GoalViewModel
    private lateinit var adapter: GoalListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GoalListAdapter()
        rv_goals.adapter = adapter
        rv_goals.layoutManager = LinearLayoutManager(context)

        //Instantiate and create viewmodel observers
        viewModels()

        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_goalList_to_creategoalItem)
        }

        //Show the options menu in this fragment
        setHasOptionsMenu(true)

        swipeToRefresh.setOnRefreshListener {
            adapter.setData(goalList)
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun viewModels() {
        goalViewModel = ViewModelProvider(this).get(GoalViewModel::class.java)

        goalViewModel.getAllHabits.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            goalList = it

            if (it.isEmpty()) {
                rv_goals.visibility = View.GONE
                tv_emptyView.visibility = View.VISIBLE
            } else {
                rv_goals.visibility = View.VISIBLE
                tv_emptyView.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_delete -> goalViewModel.deleteAllGoals()
        }
        return super.onOptionsItemSelected(item)
    }

}