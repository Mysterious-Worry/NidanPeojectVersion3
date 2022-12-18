package in.co.okservices.nidanhospitaapp3.data_adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.okservices.nidanhospitaapp3.EditPreviousDayRecord;
import in.co.okservices.nidanhospitaapp3.R;
import in.co.okservices.nidanhospitaapp3.costom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp3.data_models.day_record_madel;
import in.co.okservices.nidanhospitaapp3.data_models.patient_model;

public class day_record_adapter extends RecyclerView.Adapter<day_record_adapter.myViewHolder>{

    ArrayList<day_record_madel> dataHolder;
    Context context;

    public day_record_adapter(ArrayList<day_record_madel> dataHolder, Context context) {
        this.dataHolder = dataHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_details_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        int pos = position;
        holder.date.setText(dataHolder.get(position).getDate());
        holder.patient_count.setText(dataHolder.get(position).getPatient_count());
        holder.collected_money.setText(dataHolder.get(position).getCollected_money());
        holder.normal_count.setText("Normal = " + dataHolder.get(position).getNormal_count());
        holder.emergency_count.setText("Emergency = " + dataHolder.get(position).getEmergency_count());
        holder.normal_paper_valid_count.setText("Normal Paper\nValid = " + dataHolder.get(position).getNormal_paper_valid_count());
        holder.paper_valid_emergency_count.setText("Emergency Paper\nValid = " + dataHolder.get(position).getPaper_valid_emergency_count());
        holder.discount_count.setText("Discount = " + dataHolder.get(position).getDiscount_count());
        holder.cancel_count.setText("Cancle = " + dataHolder.get(position).getCancel_count());

        holder.see_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditPreviousDayRecord.class);
                intent.putExtra("date", dataHolder.get(pos).getDate());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView date, patient_count, collected_money,
                normal_count, emergency_count, normal_paper_valid_count,
                paper_valid_emergency_count, discount_count, cancel_count;
        Button see_details_btn;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date);
            patient_count = (TextView)itemView.findViewById(R.id.patient_count);
            collected_money = (TextView)itemView.findViewById(R.id.collected_money);
            normal_count = (TextView)itemView.findViewById(R.id.normal_count);
            emergency_count = (TextView)itemView.findViewById(R.id.emergency_count);
            normal_paper_valid_count = (TextView)itemView.findViewById(R.id.normal_paper_valid_count);
            paper_valid_emergency_count = (TextView)itemView.findViewById(R.id.paper_valid_emergency_count);
            discount_count = (TextView)itemView.findViewById(R.id.discount_count);
            cancel_count = (TextView)itemView.findViewById(R.id.cancel_count);
            see_details_btn = (Button)itemView.findViewById(R.id.see_details_btn);
        }
    }
}
