package com.friendly_machines.frbpdoctor.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.friendly_machines.frbpdoctor.R
import com.friendly_machines.frbpdoctor.WatchCommunicationClientShorthand
import com.friendly_machines.frbpdoctor.watchprotocol.command.WatchChangeAlarmAction
import com.friendly_machines.frbpdoctor.watchprotocol.notification.WatchResponse
import com.friendly_machines.frbpdoctor.watchprotocol.notification.big.AlarmDataBlock
import com.friendly_machines.frbpdoctor.watchprotocol.notification.big.AlarmTitle

class AlarmFragment : Fragment() {
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addAlarmTimeButton = view.findViewById<Button>(R.id.addAlarmButton)
        addAlarmTimeButton.setOnClickListener {
            val editAlarmDialog = EditAlarmDialog(WatchChangeAlarmAction.Add)
            editAlarmDialog.addListener(object : EditAlarmDialog.OnAlarmSetListener {
                override fun onAlarmSet(enabled: Boolean, title: AlarmTitle, hour: Byte, min: Byte, repeatOnDaysOfWeek: BooleanArray) {
                    WatchCommunicationClientShorthand.bindExecOneCommandUnbind(requireContext(), WatchResponse.SetAlarm(0)) { binder ->
                        val id = 1 // FIXME
                        binder.changeAlarm(WatchChangeAlarmAction.Add, id, enabled, hour, min, title, repeatOnDaysOfWeek)
                    }
                    // TODO refresh alarm list maybe
                }
            })
            editAlarmDialog.show(childFragmentManager, "edit_alarm_dialog")
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //adapter.notifyDataSetChanged()
        this.recyclerView = recyclerView
    }

    fun setData(data: Array<AlarmDataBlock>) {
        val adapter = AlarmAdapter(data.sortedBy { it.id })
        recyclerView!!.adapter = adapter
        //adapter.notifyDataSetChanged()
    }
}