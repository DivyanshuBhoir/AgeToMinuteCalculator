package div.dablank.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button2)
        val displayDateTextView: TextView = findViewById(R.id.editTextDate)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        button.setOnClickListener {
            showDatePickerDialog(displayDateTextView, resultTextView)
        }
    }

    private fun showDatePickerDialog(displayDateTextView: TextView, resultTextView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                displayDateTextView.text = selectedDate

                // Calculate the minutes lived
                val minutesLived = calculateMinutesLived(selectedYear, selectedMonth, selectedDay)
                resultTextView.text = "Minutes Lived: $minutesLived"

                Toast.makeText(this, "Date selected: $selectedDate", Toast.LENGTH_SHORT).show()
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun calculateMinutesLived(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis

        val birthDate = Calendar.getInstance().apply {
            set(year, month, day)
        }
        val birthTime = birthDate.timeInMillis

        val differenceInMillis = currentTime - birthTime
        return TimeUnit.MILLISECONDS.toMinutes(differenceInMillis)
    }
}
