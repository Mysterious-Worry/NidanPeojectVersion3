package in.co.okservices.nidanhospitaapp3.data_adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.okservices.nidanhospitaapp3.R;
import in.co.okservices.nidanhospitaapp3.costom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp3.data_models.patient_model;

public class day_record_adapter extends RecyclerView.Adapter<day_record_adapter.myViewHolder> {

    ArrayList<day_record_adapter> dataHolder;
    Context context;
    MyDatabaseHelper myDB;

    public day_record_adapter(Context context, ArrayList<day_record_adapter> dataHolder) {
        this.context = context;
        this.dataHolder = dataHolder;
        myDB = new MyDatabaseHelper(context);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Write code
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        // Write code
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CheckBox item;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
