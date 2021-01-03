package com.samar.exam

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "InProgress"

class InProgress : Fragment() {

    private lateinit var inprogressRecyclerView: RecyclerView
    private var adapter:InProgress.InprogressAdapter? = InprogressAdapter(emptyList())

    private val inProgressListViewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }


    interface Callbacks {
        fun onTaskSelected(taskId: UUID)
    }
    private var callbacks: Callbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           // param1 = it.getString(ARG_PARAM1)
           // param2 = it.getString(ARG_PARAM2)
        }
    }

  //  private var param1: String? = null
   // private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.inprogress_fragment, container, false)
        inprogressRecyclerView = view.findViewById(R.id.inprogress_recyclerview) as RecyclerView
        inprogressRecyclerView.layoutManager = LinearLayoutManager(context)
        inprogressRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inProgressListViewModel.inprogressListLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    Log.i(TAG, "Got tasks ${tasks.size}")
                    updateUI(tasks)
                }
            })
    }



  //  override fun onDetach() {
    //    super.onDetach()
      //  callbacks = null
    //}


    private fun updateUI(tasks: List<Task>) {
        adapter = InprogressAdapter(tasks)
        inprogressRecyclerView.adapter = adapter
    }

    private inner class InprogressHolder(view: View)
        : RecyclerView.ViewHolder(view){
       // private lateinit var task: Task
         val titleTextView: TextView = itemView.findViewById(R.id.task_title)
         val detailsTextView: TextView = itemView.findViewById(R.id.task_details)
         val dateTextView: TextView = itemView.findViewById(R.id.task_date)
         val todo: ImageView = itemView.findViewById(R.id.Do_image_Button)
         val done : ImageView = itemView.findViewById(R.id.Do_image_Button2)
         val deletButton: ImageView = itemView.findViewById(R.id.delete)


        //init {
        //    itemView.setOnClickListener(this)
       // }

      //  fun bind(task: Task) {
            //this.task = task
           // titleTextView.text = this.task.title
          //  detailsTextView.text=this.task.details
          //  dateTextView.text = this.task.date.toString()
       // }
       // override fun onClick(v: View) {
         //   callbacks?.onTaskSelected(task.id)
      //  }
    }

    private inner class InprogressAdapter(var tasks: List<Task>)
        : RecyclerView.Adapter<InprogressHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : InprogressHolder {
            val view = layoutInflater.inflate(R.layout.in_progress, parent, false)
            return InprogressHolder(view)
        }
        override fun getItemCount() :Int{
            return  tasks.size
        }

        override fun onBindViewHolder(holder: InprogressHolder, position: Int) {
            val task = tasks[position]
            holder.apply {
                titleTextView.text = task.title
                detailsTextView.text=task.details
                dateTextView.text =task.date.toString()

                todo.setOnClickListener {
                    task.move = "To-Do"
                    inProgressListViewModel.moveTask(task)
                }
                done.setOnClickListener {
                    task.move="Done"
                    inProgressListViewModel.moveTask(task)
                }
                deletButton.setOnClickListener {
                    inProgressListViewModel.deleteTask(task)
                }
            }
        }
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            InProgress().apply {
                arguments = Bundle().apply {

                   // putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)

                }
            }
    }
}