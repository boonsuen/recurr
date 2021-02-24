package com.boonsuen.recurr.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.boonsuen.recurr.R
import com.boonsuen.recurr.data.models.BillingPeriod
import com.boonsuen.recurr.data.models.SubscriptionData

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<SubscriptionData>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.name_txt).text = dataList[position].name
        holder.itemView.findViewById<TextView>(R.id.amount_txt).text = dataList[position].amount.toString()
        holder.itemView.findViewById<ConstraintLayout>(R.id.row_background).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        when (dataList[position].billingPeriod) {
            BillingPeriod.MONTHLY -> holder.itemView.findViewById<CardView>(R.id.billing_period_indicator).setCardBackgroundColor(ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
            ))
            BillingPeriod.WEEKLY -> holder.itemView.findViewById<CardView>(R.id.billing_period_indicator).setCardBackgroundColor(ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.yellow
            ))
            BillingPeriod.YEARLY -> holder.itemView.findViewById<CardView>(R.id.billing_period_indicator).setCardBackgroundColor(ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
            ))
        }
    }

    fun setData(subscriptionData: List<SubscriptionData>) {
        this.dataList = subscriptionData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}