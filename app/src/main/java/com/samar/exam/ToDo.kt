package com.samar.exam

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "ToDo"


class ToDo : Fragment() {
    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun onTaskSelected(taskId: UUID)
    }
    private var callbacks: Callbacks? = null

    private lateinit var taskRecyclerView: RecyclerView
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())



    private val taskListViewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.to__do, container, false)
        taskRecyclerView =
            view.findViewById(R.id.task_recycler_view) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListViewModel.taskListLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    Log.i(TAG, "Got tasks ${tasks.size}")
                    updateUI(tasks)
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_task_list, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_task -> {
                val task = Task()
                taskListViewModel.addTask(task)
                callbacks?.onTaskSelected(task.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(tasks: List<Task>) {
        adapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = adapter
    }

    private inner class TaskHolder(view: View)
        : RecyclerView.ViewHolder(view),View.OnClickListener {
        private lateinit var task: Task
        private val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.task_date)
        private val doImageView: ImageView = itemView.findViewById(R.id.Do_image_Button)


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(task: Task) {
            this.task = task
            titleTextView.text = this.task.title
            dateTextView.text = this.task.date.toString()
        }
        override fun onClick(v: View) {
            callbacks?.onTaskSelected(task.id)
        }
    }

    private inner class TaskAdapter(var tasks: List<Task>)
        : RecyclerView.Adapter<TaskHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : TaskHolder {
            val view = layoutInflater.inflate(R.layout.list_item_task, parent, false)
            return TaskHolder(view)
        }
        override fun getItemCount() = tasks.size
        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ToDo().apply {
                arguments = Bundle().apply {

                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }

        fun newInstance(): ToDo {
            return ToDo()
        }
    }
}