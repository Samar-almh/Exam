package com.samar.exam

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "Done"


class Done : Fragment() {


    private lateinit var doneRecyclerView: RecyclerView
    private var adapter:Done.DoneAdapter? = DoneAdapter(emptyList())

    private val doneListViewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }

   // interface Callbacks {
     //   fun onTaskSelected(taskId: UUID)
    //}
    //private var callbacks: Callbacks? = null

    //override fun onAttach(context: Context){
      //  super.onAttach(context)
       // callbacks=context as Callbacks?
    //}




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          //  param1 = it.getString(ARG_PARAM1)
          //  param2 = it.getString(ARG_PARAM2)
        }
    }

   // private var param1: String? = null
   // private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.done_fragment, container, false)
        doneRecyclerView = view.findViewById(R.id.done_recyclerview) as RecyclerView
        doneRecyclerView.layoutManager = LinearLayoutManager(context)
        doneRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doneListViewModel.doneListLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    Log.i(TAG, "Got tasks ${tasks.size}")
                    updateUI(tasks)
                }
            })
    }



   // override fun onDetach() {
  //      super.onDetach()
     //   callbacks = null
    //}


    private fun updateUI(tasks: List<Task>) {
        adapter = DoneAdapter(tasks)
        doneRecyclerView.adapter = adapter
    }

    private inner class DoneHolder(view: View)
        : RecyclerView.ViewHolder(view) {
       // private lateinit var task: Task
         val titleTextView: TextView = itemView.findViewById(R.id.task_title)
         val detailsTextView: TextView = itemView.findViewById(R.id.task_details)
         val dateTextView: TextView = itemView.findViewById(R.id.task_date)
         val inProgress: ImageView = itemView.findViewById(R.id.Do_image_Button)
         val toDo : ImageView = itemView.findViewById(R.id.Do_image_Button2)
         val deletButton: ImageView = itemView.findViewById(R.id.delete)


       // init {
         //   itemView.setOnClickListener(this)
       // }

       // fun bind(task: Task) {
           // this.task = task
            //titleTextView.text = this.task.title
           // detailsTextView.text=this.task.details
           // dateTextView.text = this.task.date.toString()
       // }
       // override fun onClick(v: View) {
        //    callbacks?.onTaskSelected(task.id)
       // }
    }

    private inner class DoneAdapter(var tasks: List<Task>)
        : RecyclerView.Adapter<DoneHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : DoneHolder {
            val view = layoutInflater.inflate(R.layout.done, parent, false)
            return DoneHolder(view)
        }

        override fun getItemCount() :Int{
            return tasks.size
        }

        override fun onBindViewHolder(holder: DoneHolder, position: Int) {
            val task = tasks[position]
            holder.apply {
                titleTextView.text = task.title
                detailsTextView.text=task.details
                dateTextView.text =task.date.toString()

                inProgress.setOnClickListener {
                    task.move = "In-Progress"
                    doneListViewModel.moveTask(task)
                }
                toDo.setOnClickListener {
                    task.move="To-Do"
                    doneListViewModel.moveTask(task)
                }

                deletButton.setOnClickListener {
                    doneListViewModel.deleteTask(task)
                }
            }
        }
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            Done().apply {
                arguments = Bundle().apply {

                  //  putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)

                }
            }

       // fun newInstance(): Done {
         //   return Done()
        //}
    }

}