package in.co.okservices.nidanhospitaapp3.data_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import in.co.okservices.nidanhospitaapp3.MainActivity;
import in.co.okservices.nidanhospitaapp3.R;
import in.co.okservices.nidanhospitaapp3.costom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp3.data_models.*;

public class edit_previous_record_adapter extends RecyclerView.Adapter<edit_previous_record_adapter.myViewHolder> {

    ArrayList<patient_model> dataHolder;
    Context context;
    MyDatabaseHelper myDatabaseHelper;
    String[] types = new String[]{"Normal", "Emergency", "Normal Paper Valid", "Paper Valid Emergency",
            "Discount", "Cancel", "Not Checked"};

    public edit_previous_record_adapter(ArrayList<patient_model> dataHolder, Context context) {
        this.dataHolder = dataHolder;
        this.context = context;
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    public edit_previous_record_adapter(Context context) {
        this.context = context;
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    public edit_previous_record_adapter(ArrayList<patient_model> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_detail_row, parent, false);
        return new edit_previous_record_adapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        // Setting up texts
        holder.id_txt.setText(dataHolder.get(position).getId());
        holder.sr_no_txt.setText(dataHolder.get(position).getSr_no());
        String checked = dataHolder.get(position).getChecked();
        String id = dataHolder.get(position).getId();
        if(Objects.equals(checked, "yes")){
            holder.seen_check_box.setChecked(true);
        } else {
            holder.seen_check_box.setChecked(false);
        }

        int typeDecimal = Integer.parseInt(dataHolder.get(position).getType());
        String hexColor = String.format("#%06X", (0xFFFFFF & typeDecimal));
        holder.type_txt.setText(getTypeByHex(hexColor));

        holder.seen_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(checked, "no")) {
                    boolean query = myDatabaseHelper.updateCheckedYes(holder.id_txt.getText().toString());
                    if (query) {
                        Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error at saving data!", Toast.LENGTH_SHORT).show();
                    }
                } else if (Objects.equals(checked, "yes")){
                    boolean query = myDatabaseHelper.updateCheckedNo(holder.id_txt.getText().toString());
                    if (query) {
                        Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error at saving data!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView sr_no_txt, id_txt;
        TextView type_txt;
        CheckBox seen_check_box;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            sr_no_txt = (TextView)itemView.findViewById(R.id.sr_no_txt);
            type_txt = (TextView)itemView.findViewById(R.id.type_txt);
            id_txt = (TextView)itemView.findViewById(R.id.id_txt);
            seen_check_box = (CheckBox)itemView.findViewById(R.id.seen_check_box);
        }
    }

    /* private methods */
    private String getTypeByHex(String hex){
        if(hex.equals("#060248")){
            hex = "Normal";
        } else if(hex.equals("#06005B")){
            hex = "Emergency";
        } else if(hex.equals("#060249")){
            hex = "Normal Paper Valid";
        } else if(hex.equals("#06024C")){
            hex = "Paper Valid Emergency";
        } else if(hex.equals("#06005A")){
            hex = "Discount";
        } else if(hex.equals("#06002B")){
            hex = "Cancel";
        }  else if(hex.equals("#060022")){
            hex = "Not Checked";
        }
        return hex;
    }
}
