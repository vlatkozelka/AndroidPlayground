package com.example.playground.main.patients

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.playground.R
import io.reactivex.subjects.PublishSubject
import org.notests.sharedsequence.Signal

/**
 * Created by Ali on 5/29/2019.
 */
class PatientsAdapter : RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>() {

    private var patientViewModels = mutableListOf<PatientViewModel>()
    private val clicks = PublishSubject.create<Int>()
    val clicksSignal = Signal(clicks)


    override fun onCreateViewHolder(container: ViewGroup, position: Int): PatientViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.list_item_patient, container, false)
        return PatientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patientViewModels.size
    }

    override fun onBindViewHolder(viewHolder: PatientViewHolder, position: Int) {
        viewHolder.bind(patientViewModels[position], position)
    }


    inner class PatientViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imgPatient: ImageView = view.findViewById(R.id.img_patient)
        val txtPatientName: TextView = view.findViewById(R.id.txt_patient_name)

        fun bind(patientViewModel: PatientViewModel, position: Int) {
            view.setOnClickListener { clicks.onNext(position) }
            txtPatientName.text = patientViewModel.name
        }
    }

    fun update(patientViewModels: List<PatientViewModel>) {
        this.patientViewModels.clear()
        this.patientViewModels.addAll(patientViewModels)
        notifyDataSetChanged()
    }

}


