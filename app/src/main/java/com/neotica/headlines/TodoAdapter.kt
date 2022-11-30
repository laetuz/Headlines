package com.neotica.headlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neotica.headlines.databinding.ItemTodoBinding

//Step 21

//Step 22: Inherit from RecyclerView.Adapter
//Step 23: Create an inner class for the viewHolder (TodoViewHolder)
class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    //Step 24: Put the viewHolder inside the inner class.
    //Step 25: Bind the viewHolder to item_todo.xml
    //Step 26: Inherit from RecyclerView.ViewHolder, apply binding.root
    //Step 40: Make binding a val
    inner class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    //Step 27: Create a new diffCallBack variable.\
    //Step 28: Inherit the object to DiffUtil.ItemCallBack, then put the /nTodo items inside the diamond braces.
    private val difCallback = object : DiffUtil.ItemCallback<Todo>(){
        //Step 29: Make comparisons whether items are the same or not.
        // Override function areItemsTheSame & AreContentsTheSame
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            //Step 30: Return old item equals to old item with the id
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            //Step 30.1: Return old item equals to old item without the id
            return oldItem == newItem
        }
    }

    //Step 31: Create new private val for the AsyncListDiffer (background threading)
    // Pass the adapter to this context and difCallback.
    private val differ = AsyncListDiffer(this,difCallback)
    //Step 32: Create a var for todos list
    var todos: List<Todo>
    //Step 33: Override the getter for the differ to currentList.
    get() = differ.currentList
    //Step 34: Create a setter with 'value' as its parameter.
    // Set the setter to submitList and return value.
    set(value) {differ.submitList(value)}

    //Step 35: Override the 3 function for the recyclerView.
    // getItemCount, onCreateViewHolder, onBindViewHolder
    //Step 36: return the size of the list
    override fun getItemCount() = todos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        //Step 37: return an instance of the ViewHolder(TodoViewHolder) to the item_todo. Bind and Inflate
        return TodoViewHolder(ItemTodoBinding.inflate(
            //Step 38: Pass the layoutInflater from parent.context to inflate from the parent.
            LayoutInflater.from(parent.context),
            //Step 39: Pass the parent, and don't attach to root layout.
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        //Step 41: apply binding to the holder
        holder.binding.apply {
            //Step 42: Get the current Toodo item index position.
            val todo = todos[position]
            //Step 43: Initialize the data from todo data class to the view.
            tvOne.text = todo.title
            tvDesc.text = todo.desc
            tvDate.text = todo.date

        }
    }
}