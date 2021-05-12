package hu.bme.aut.android.showlist

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.showlist.databinding.FragmentShowCreateBinding
import hu.bme.aut.android.showlist.model.Show
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class ShowCreateFragment : DialogFragment(), DateSelected, AdapterView.OnItemSelectedListener
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
        binding.dropdownShowType.onItemSelectedListener = this
        dialog?.setTitle("Create show")
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.okButtonCreate.setOnClickListener {
            val types = resources.getStringArray(R.array.show_type)
            val format = SimpleDateFormat("yyyy-MM-dd")
            if (binding.editTextDueDate.text.toString() != "")
            {
                val date = format.parse(binding.editTextDueDate.text.toString())
                listener.onShowCreated(
                    Show(
                        id = Random.nextInt(),
                        title = binding.editTextTitle.text.toString(),
                        type = types[binding.dropdownShowType.selectedItemPosition],
                        isWatched = false,
                        description = binding.editTextDescription.text.toString(),
                        dueDate = date,
                        episode = binding.editTextEpisode.text.toString()
                    )
                )
                dismiss()
            }
            else
            {
                Toast.makeText(view.context, R.string.wrong_date, Toast.LENGTH_LONG).show()
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

        val viewFormater = SimpleDateFormat("yyyy-MM-dd")
        val viewFormattedDate = viewFormater.format(calendar.getTime())
        binding.editTextDueDate.text = viewFormattedDate
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        val types = resources.getStringArray(R.array.show_type)
        binding.editTextEpisode.isEnabled = types[position] == "Series"
    }

    override fun onNothingSelected(parent: AdapterView<*>?)
    {

    }
}

interface DateSelected
{
    fun recieveDate(year: Int, month: Int, day: Int)
}