package com.neotica.headlines

import android.app.AlertDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.headlines.databinding.ActivityMainBinding
import com.neotica.headlines.databinding.DialogViewBinding
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: DialogViewBinding

    //Step 48: Create instance of todoAdapter
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this@MainActivity,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed(){
                bindingDialog = DialogViewBinding.inflate(layoutInflater)
                val view = bindingDialog.root
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setView(view)

                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                bindingDialog.btnBack.setOnClickListener { dialog.hide() }
                bindingDialog.btnExit.setOnClickListener { finish() }
            }
        })


        //Step 46: Initialize the binding here
        binding = ActivityMainBinding.inflate(layoutInflater)
        //Step 47: Change the parameter inside setContentView into binding.root
        setContentView(binding.root)
        //Step 53 call the setupRecyclerView function on onCreate
        setupRecyclerView()

        //Step 54: Setup api request on a background thread using lifecycleScope (coroutineScope)
        lifecycleScope1()

        binding.btnRefresh.setOnClickListener{
            lifecycleScope1()
        }

        binding.btnRefresh.setOnLongClickListener {
            Log.d(
                "Refresh-long-click",
                "User long clicked the refresh button, activity restarted."
            )
            finish()
            startActivity(intent)
            true }



    }
    fun lifecycleScope1(){
        lifecycleScope.launchWhenCreated {
            //Step 55: set the progress bar visibility to true
            binding.progressBar.isVisible = true
            //Step 56: Get the response in the try blocks
            val response = try {
                //Step 57: Use retrofit instance api, and get todo list (getTodos)
                RetrofitInstance.api.getTodos()
            }
            //Step 58: Catch to 2 types of exceptions, IOException and HttpException
            catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, internet connection trouble")
                //Step 62.1: Set the progressbar visibility to false.
                binding.progressBar.isVisible = false
                //Step 59: return to launchWhenCreated
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                //Step 62.2: Set the progressbar visibility to false.
                binding.progressBar.isVisible = false
                //Step 59.1: return to launchWhenCreated
                return@launchWhenCreated
            }
            //Step 60: Check if response is successful & body(of List<Todo>) is not null.
            if (response.isSuccessful && response.body() != null) {
                //Step 61: If checked, then we can use the todoAdapter.todos and assign response.body()!!
                // So now we can set the todo list to the adapter.
                todoAdapter.todos = response.body()!!
            } //Step 61: else response is not successful.
            else {
                Log.e(ContentValues.TAG, "Response is not successful.")
            }
            //Step 62: Set the progressbar visibility to false.
            binding.progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView() = binding.rvTodo.apply {
        //Step 50: Set todoAdapter to a new TodoAdapter
        todoAdapter = TodoAdapter()
        //Step 51: Set the adapter of the recyclerView to todoAdapter
        adapter = todoAdapter
        //Step 52: Set the layoutManager of the recyclerView to a LinearLayoutManager
        // and pass the context to this@MainActivity
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

   /* private fun refresh(){
        binding.btnRefresh.setOnClickListener(){respo}
    }*/


}