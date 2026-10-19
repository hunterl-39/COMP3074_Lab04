package com.example.comp3074lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comp3074lab4.data.AppDatabase
import com.example.comp3074lab4.ui.CustomerScreen
import com.example.comp3074lab4.ui.CustomerViewModel
import com.example.comp3074lab4.ui.CustomerViewModelFactory
import com.example.comp3074lab4.ui.theme.Comp3074Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)

        setContent {
            Comp3074Lab4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: CustomerViewModel = viewModel(
                        factory = CustomerViewModelFactory(
                            database.customerDao()
                        )
                    )

                    val customers by viewModel.customers
                        .collectAsStateWithLifecycle(initialValue = emptyList())

                    val searchQuery by viewModel.searchQuery
                        .collectAsStateWithLifecycle()

                    CustomerScreen(
                        customers = customers,
                        searchQuery = searchQuery,
                        onSearchQueryChange = viewModel::setSearchQuery,
                        onClearSearch = viewModel::clearSearch,
                        onAddCustomer = viewModel::addCustomer,
                        onDeleteCustomer = viewModel::deleteCustomer,
                        onUpdateCustomer = viewModel::updateCustomer,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
