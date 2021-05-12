package hu.bme.aut.android.showlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.showlist.databinding.FragmentShowCreateBinding
import hu.bme.aut.android.showlist.model.Show
import kotlinx.coroutines.processNextEventInCurrentThread
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

class ShowCreateFragment : DialogFragment(), DateSelected
{
    private lateinit var binding: FragmentShowCreateBinding
    private lateinit var listener: ShowCreatedListener

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        listener = activity as ShowCreatedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentShowCreateBinding.inflate(inflater, container, false)
        dialog?.setTitle("Create show")
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextDueDate.text = "  -  "

        binding.okButtonCreate.setOnClickListener {
            val format = SimpleDateFormat.getDateInstance()
            val date = format.parse(binding.editTextDueDate.text.toString())
            if (date != null)
            {
                listener.onShowCreated(
                    Show(
                        id = Random.nextInt(),
                        title = binding.editTextTitle.text.toString(),
                        type = binding.editTextType.text.toString(),
                        isWatched = false,
                        description = binding.editTextDescription.toString(),
                        dueDate = date,
                        episode = binding.editTextEpisode.text.toString()
                    )
                )
                dismiss()
            }
            else
            {
                Toast.makeText(view.context, R.string.wrongDate, Toast.LENGTH_LONG).show()
            }
        }
        binding.cancelButtonCreate.setOnClickListener {
            dismiss()
        }
        binding.editTextDueDate.setOnClickListener {
            showDatePicker()
        }
    }

    interface ShowCreatedListener
    {
        fun onShowCreated(show: Show)
    }

    private fun showDatePicker()
    {
        val datePicker = DatePickerFragment(this)
        datePicker.show(fragmentManager!!, "date picker")
    }

    override fun recieveDate(year: Int, month: Int, day: Int)
    {
        val calendar = GregorianCalendar()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val viewFormater = SimpleDateFormat("YYYY-MM-dd")
        var viewFormattedDate: String = viewFormater.format(calendar.getTime())
        binding.editTextDueDate.text = viewFormattedDate
    }
}

interface DateSelected
{
    fun recieveDate(year: Int, month: Int, day: Int)
}