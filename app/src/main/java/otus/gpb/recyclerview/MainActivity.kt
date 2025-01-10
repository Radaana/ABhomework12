package otus.gpb.recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val adapter: ChatAdapter by lazy { ChatAdapter() }
    private var list: List<ChatItem> = emptyList()
    private val names: List<String> = listOf("Sam", "Paul", "Jean", "Jo", "Jacob", "Winter", "Summer", "Olly")
    private val surnames: List<String> = listOf("Brown", "Potter", "Sol", "Dew", "Moss", "Triple", "Kenzie", "Martin")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.addItemDecoration(
            CustomDecorator()
        )

        ItemTouchHelper(ItemTouchCallback()).attachToRecyclerView(recyclerView)

        recyclerView.adapter = adapter
        list = generateList(15)
        adapter.submitList(list)

        findViewById<FloatingActionButton>(R.id.loadMore).setOnClickListener { addChats() }
    }

    private fun addChats() {
        val currentList = list.toMutableList()
        currentList.addAll(generateList(5))
        adapter.submitList(currentList)
    }

    private fun generateList(number: Int) = run {
        val list = mutableListOf<ChatItem>()
        repeat(number) {
            val nameIdx = Random.nextInt(0, names.size - 1)
            val surnameIdx = Random.nextInt(0, surnames.size - 1)
            val personItem = ChatItem(
                id = it,
                name = names[nameIdx],
                surname = surnames[surnameIdx],
                message = "Some message from ${names[nameIdx]}",
                date = "12:05",
                background = ColorGenerator.generateColor()
            )
            list.add(personItem)
        }

        list.toList()
    }
}