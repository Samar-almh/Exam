package com.samar.exam

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ToDo : Fragment(),TaskDaialog.Callbacks {

    private lateinit var addFab : FloatingActionButton
    private lateinit var taskRecyclerView: RecyclerView
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())

    private val taskListViewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.todo_fragment, container, false)
        taskRecyclerView = view.findViewById(R.id.task_recycler_view) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter

        addFab = view.findViewById(R.id.fab) as FloatingActionButton

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListViewModel.todoListLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    //Log.i(TAG, "Got tasks ${tasks.size}")
                    updateUI(tasks)
                }
            })

        addFab.setOnClickListener {
            TaskDaialog().apply {
                setTargetFragment(this@ToDo, 0)
                show(this@ToDo.requireFragmentManager(),"Input")
            }
        }

    }

    private fun updateUI(tasks: List<Task>) {
        adapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = adapter
    }


    private inner class TaskHolder(view: View)
        : RecyclerView.ViewHolder(view) {
         //lateinit var task: Task
         val titleTextView: TextView = itemView.findViewById(R.id.task_title)
         val detailsTextView: TextView = itemView.findViewById(R.id.task_details)
         val dateTextView: TextView = itemView.findViewById(R.id.task_date)
         val inProgress: ImageView = itemView.findViewById(R.id.Do_image_Button)
         val done : ImageView = itemView.findViewById(R.id.Do_image_Button2)
         val deletButton: ImageView = itemView.findViewById(R.id.delete)


    }

    private inner class TaskAdapter(var tasks: List<Task>)
        : RecyclerView.Adapter<TaskHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : TaskHolder {
            val view = layoutInflater.inflate(R.layout.to__do, parent, false)
            return TaskHolder(view)
        }

        override fun getItemCount() :Int{
            return tasks.size
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.apply {
                titleTextView.text = task.title
                detailsTextView.text=task.details
                dateTextView.text = task.date.toString()

                inProgress.setOnClickListener {
                    task.move = "In-Progress"
                    taskListViewModel.moveTask(task)
                }
                done.setOnClickListener {
                    task.move="Done"
                    taskListViewModel.moveTask(task)
                }

                deletButton.setOnClickListener {
                    taskListViewModel.deleteTask(task)
                }

            }
        }
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            ToDo().apply {
                arguments = Bundle().apply {

                }
            }
        }

    override fun addTask(task: Task) {
        taskListViewModel.addTask(task)
    }

}