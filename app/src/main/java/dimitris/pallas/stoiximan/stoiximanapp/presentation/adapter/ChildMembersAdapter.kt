package dimitris.pallas.stoiximan.stoiximanapp.presentation.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dimitris.pallas.stoiximan.stoiximanapp.R
import dimitris.pallas.stoiximan.stoiximanapp.SportsModel
import java.util.*

open class ChildMembersAdapter(memberData: List<SportsModel.Events>) :
    RecyclerView.Adapter<ChildMembersAdapter.ViewHolder>() {
    private var mNestedList: List<SportsModel.Events> = ArrayList()
    private var viewHolderList: MutableList<ViewHolder> = mutableListOf()
    private val mainHandler = Handler(Looper.getMainLooper())
    private var booleanChecker: Boolean = false
    private val runnable = Runnable {
        val currentTime: Long = System.currentTimeMillis()
        for (viewHolder: ViewHolder in viewHolderList) {
            viewHolder.updateTimeRemaining(currentTime)
        }
    }

    init {
        this.mNestedList = memberData
        this.viewHolderList = ArrayList()
        startUpdateTimer()

    }

    private fun startUpdateTimer() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                mainHandler.post(runnable)
            }
        }, 1000, 1000)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_row_child, parent, false
        )
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mNestedList[position]
        viewHolderList.add(holder)
        holder.setData(item)
        holder.outlineStar.setOnClickListener {
            booleanChecker = if (!booleanChecker) {
                holder.outlineStar.setImageResource(R.drawable.ic_baseline_star_24)
                holder.swapItem(holder.layoutPosition, 0)
                true
            } else {
                holder.outlineStar.setImageResource(R.drawable.ic_baseline_star_outline_24)
                false
            }
        }
    }

    override fun getItemCount(): Int = mNestedList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val time: TextView = view.findViewById(R.id.epoch)
        private val team: TextView = view.findViewById(R.id.teams)
        val outlineStar: ImageButton = view.findViewById(R.id.outline_start)
        private lateinit var event: SportsModel.Events


        fun setData(item: SportsModel.Events) {
            event = item
            team.text = item.sh
            updateTimeRemaining(System.currentTimeMillis())
        }

        fun updateTimeRemaining(currentTime: Long) {
            val apiTime = event.tt
            val timeDiff: Long = (apiTime * 1000L) - currentTime
            if (timeDiff > 0) {
                val seconds = (timeDiff / 1000 % 60).toInt()
                val minutes = (timeDiff / (60 * 1000) % 60).toInt()
                val hours = (timeDiff / (60 * 60 * 1000) % 24).toInt()
                val days = (timeDiff / (24 * 60 * 60 * 1000)).toInt()
                time.text = "$days Days $hours Hours $minutes Minutes $seconds Seconds"
            } else {
                time.text = "This event has already finished"
            }
        }

        fun swapItem(fromPosition: Int, toPosition: Int) {
            Collections.swap(mNestedList, fromPosition, 0)
            if (outlineStar.isPressed) {
                notifyItemMoved(fromPosition, 0)
            }
        }
    }
}
