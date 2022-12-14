package in.co.okservices.nidanhospitaapp3.data_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.okservices.nidanhospitaapp3.R;
import in.co.okservices.nidanhospitaapp3.data_models.day_record_madel;

public class edit_previous_record_adapter extends RecyclerView.Adapter<edit_previous_record_adapter.myViewHolder> {

    ArrayList<patient_adapter> dataHolder;
    Context context;

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_detail_row, parent, false);
        return new edit_previous_record_adapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
