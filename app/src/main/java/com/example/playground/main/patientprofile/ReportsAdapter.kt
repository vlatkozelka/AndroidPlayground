package com.example.playground.main.patientprofile

import android.support.v7.widget.RecyclerView
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.playground.R
import com.example.playground.main.Event
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.list_item_report.view.*
import org.notests.sharedsequence.Signal

/**
 * Created by Ali on 5/29/2019.
 */
class ReportsAdapter : RecyclerView.Adapter<ReportsAdapter.ReportViewHolder>() {

    var reportViewModels = mutableListOf<ReportViewModel>()
    private val clicks = PublishSubject.create<Event>()
    val clicksSignal = Signal(clicks)


    override fun onCreateViewHolder(container: ViewGroup, position: Int): ReportViewHolder {
        return ReportViewHolder(
            LayoutInflater.from(container.context).inflate(R.layout.list_item_report, container, false)
        )
    }

    override fun getItemCount(): Int {
        return reportViewModels.size
    }

    override fun onBindViewHolder(viewHolder: ReportViewHolder, position: Int) {
        viewHolder.bind(reportViewModels[position], position)
    }


    inner class ReportViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtType = view.findViewById<TextView>(R.id.txt_report_type)
        val txtSignature = view.findViewById<TextView>(R.id.txt_signature)
        val txtDate = view.findViewById<TextView>(R.id.txt_date)
        val btnSign = view.findViewById<TextView>(R.id.btn_sign)
        val btnViewReport = view.findViewById<TextView>(R.id.btn_view_report)

        fun bind(reportViewModel: ReportViewModel, position: Int) {
            txtType.text = reportViewModel.type
            reportViewModel.signature?.let { txtSignature.text = it }
            txtSignature.visibility = reportViewModel.signatureVisibility
            txtDate.text = reportViewModel.date
            btnSign.setOnClickListener { clicks.onNext(Event.ClickedSignReport(position)) }
            btnSign.visibility = reportViewModel.signBtnVisibility
            btnViewReport.setOnClickListener { clicks.onNext(Event.ClickedViewReport(position)) }
        }
    }


    fun update(reportViewModels: List<ReportViewModel>) {
        this.reportViewModels.clear()
        this.reportViewModels.addAll(reportViewModels)
        notifyDataSetChanged()
    }

}

