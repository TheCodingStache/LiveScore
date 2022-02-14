package dimitris.pallas.stoiximan.stoiximanapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dimitris.pallas.stoiximan.stoiximanapp.R
import dimitris.pallas.stoiximan.stoiximanapp.SportsModel
import kotlinx.android.synthetic.main.item_row_parent.view.*


open class SportsRecyclerViewAdapter() :
    RecyclerView.Adapter<SportsRecyclerViewAdapter.DataViewHolder>() {

    private var sportList: List<SportsModel> = ArrayList()

    private lateinit var childMembersAdapter: ChildMembersAdapter

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: RecyclerView = itemView.findViewById(R.id.child_recycler_view)
        val expand: ConstraintLayout = itemView.findViewById(R.id.expandLayout)
        fun bind(result: SportsModel) {
            itemView.sport.text = result.d.replace("SOCCER", "FOOTBALL")
            childMembersAdapter = ChildMembersAdapter(result.e)
            itemView.child_recycler_view.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.child_recycler_view.adapter = childMembersAdapter
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_row_parent, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(sportList[position])
        val currentItem = sportList[position]
        val isVisible: Boolean = currentItem.visibility
        holder.layout.visibility = if (isVisible) View.GONE else View.VISIBLE
        holder.expand.setOnClickListener {
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }
        holder.itemView.child_recycler_view.layoutManager?.scrollToPosition(0)


    }

    override fun getItemCount(): Int = sportList.size


    fun addData(list: List<SportsModel>) {
        sportList = list
        notifyDataSetChanged()
    }

}