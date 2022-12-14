package in.co.okservices.nidanhospitaapp3.data_adapters;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.okservices.nidanhospitaapp3.MainActivity;
import in.co.okservices.nidanhospitaapp3.R;
import in.co.okservices.nidanhospitaapp3.costom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp3.data_models.patient_model;

public class patient_adapter extends RecyclerView.Adapter<patient_adapter.myViewHolder>{
    ArrayList<patient_model> dataHolder;
    Context context;
    MyDatabaseHelper myDB;
    TextView selected_patient, type_patient, sr_no_patient;
    boolean readyToSubmit = false;
    CheckBox normal_cbx, emergency_cbx, normal_paper_valid_cbx,
            paper_valid_emergency_cbx, discount_cbx, cancel_cbx;
    RecyclerView recyclerView;
    EditText discount_percent_txt;

    public patient_adapter(ArrayList<patient_model> dataHolder, Context context, RecyclerView recyclerView) {
        this.dataHolder = dataHolder;
        this.context = context;
        this.recyclerView = recyclerView;
        initMainClassViews();
        normal_cbx.setEnabled(false);
        emergency_cbx.setEnabled(false);
        normal_paper_valid_cbx.setEnabled(false);
        paper_valid_emergency_cbx.setEnabled(false);
        discount_cbx.setEnabled(false);
        cancel_cbx.setEnabled(false);
        discount_percent_txt.setEnabled(false);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        myDB = new MyDatabaseHelper(context);
        // get_checked
        holder.item.setText(dataHolder.get(position).getSr_no());
        String checked = dataHolder.get(position).getChecked();
        String id = dataHolder.get(position).getId();
        String type = dataHolder.get(position).getType();
        String sr_no = dataHolder.get(position).getSr_no();
        initMainClassViews();


        // setting up checks
        if(checked.equals("no")){
            holder.item.setChecked(false);
        } else if (checked.equals("yes")){
            holder.item.setChecked(true);
        }

        // Setting up color type
        try{
            holder.item.setTextColor(context.getResources().getColor(Integer.parseInt(type)));
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // removing or saving checks
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_patient = (TextView)((MainActivity)context).findViewById(R.id.selected_patient);
                selected_patient.setText(id);
                type_patient = (TextView)((MainActivity)context).findViewById(R.id.type_patient);
                type_patient.setText(type);
                sr_no_patient = (TextView)((MainActivity)context).findViewById(R.id.sr_no_patient);
                sr_no_patient.setText(sr_no);
                try{
                    if(readyToSubmit == false){
                        if(checked.equals("no")) {
                            boolean query = myDB.updateCheckedYes(id);
                            if (query) {
                                Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error at saving data!", Toast.LENGTH_SHORT).show();
                            }
                            selected_patient = (TextView) ((MainActivity)context).findViewById(R.id.selected_patient);
                            selected_patient.setText(id);
                            normal_cbx.setEnabled(true);
                            emergency_cbx.setEnabled(true);
                            normal_paper_valid_cbx.setEnabled(true);
                            paper_valid_emergency_cbx.setEnabled(true);
                            discount_cbx.setEnabled(true);
                            cancel_cbx.setEnabled(true);
                            discount_percent_txt.setEnabled(true);
                            readyToSubmit = true;
                            disableRecyclerView();
                        } else if(checked.equals("yes")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Do you really wanna delete this record?");
                            builder.setTitle("Alert !");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                                boolean query = myDB.updateCheckedNo(id);
                                if (query) {
                                    try{
                                        int typeOctal = holder.item.getCurrentTextColor();
                                        String hexColor = String.format("#%06X", (0xFFFFFF & typeOctal));

                                        int amount = 0;
                                        myDB.setNewType(String.valueOf(R.color.black), selected_patient.getText().toString());
                                        holder.item.setTextColor(context.getResources().getColor(R.color.black));
                                        Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(context, "Submit to reload data.", Toast.LENGTH_SHORT).show();
                                        normal_cbx.setEnabled(false);
                                        emergency_cbx.setEnabled(false);
                                        normal_paper_valid_cbx.setEnabled(false);
                                        paper_valid_emergency_cbx.setEnabled(false);
                                        discount_cbx.setEnabled(false);
                                        cancel_cbx.setEnabled(false);
                                        readyToSubmit = true;

                                        int currentPatientCount = myDB.senderCell("patient_count");
                                        int newPatientCount = 0;
                                        if(currentPatientCount > 0){
                                            newPatientCount = currentPatientCount - 1;
                                        }
                                        myDB.updateColumn("patient_count", newPatientCount);
                                        // have to minus the amountCount, typeCount
                                        try {
                                            if(hexColor.equals("#D10000")){
                                                hexColor = "normal_count";
                                                amount = 200;
                                            } else if(hexColor.equals("#D18400")){
                                                hexColor = "emergency_count";
                                                amount = 400;
                                            } else if(hexColor.equals("#49D100")){
                                                hexColor = "normal_paper_valid_count";
                                                amount = 0;
                                            } else if(hexColor.equals("#00D1A7")){
                                                hexColor = "paper_valid_emergency_count";
                                                amount = 200;
                                            } else if(hexColor.equals("#0023D1")){
                                                hexColor = "discount_count";
                                                amount = 0;
                                            } else if(hexColor.equals("#A400D1")){
                                                hexColor = "cancel_count";
                                                amount = 100;
                                            }  else if(hexColor.equals("#000000")){
                                                hexColor = "black";
                                            }

                                            int currentAmountCollected = myDB.senderCell("collected_money");
                                            int newAmountCollected = currentAmountCollected - amount;
                                            myDB.updateColumn("collected_money", newAmountCollected);

                                            int new_type_count = 0;
                                            int old_type_count = myDB.senderCell(hexColor);
                                            if(old_type_count <= 0){
                                                new_type_count = old_type_count;
                                            } else {
                                                new_type_count = old_type_count - 1;
                                            }
                                            myDB.updateColumn(hexColor, new_type_count);
                                        }catch (Exception x){
                                            Toast.makeText(context, x.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception ex) {
                                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "Error at saving data!", Toast.LENGTH_SHORT).show();
                                }
                                disableRecyclerView();
                            });
                            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

                                dialog.cancel();
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    } else {
                        Toast.makeText(context, "Please submit to add more records.", Toast.LENGTH_SHORT).show();
                        holder.item.setChecked(!holder.item.isChecked());
                    }
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // control checkbox onClickListener
        normal_cbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int amount = 200;
                    if(emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.emergencyC )+ "");
                        emergency_cbx.setChecked(false);
                    } else if(normal_paper_valid_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normal_paper_validC) + "");
                        normal_paper_valid_cbx.setChecked(false);
                    } else if(paper_valid_emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.paper_valid_emergencyC) + "");
                        paper_valid_emergency_cbx.setChecked(false);
                    } else if(discount_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.discountC) + "");
                        discount_cbx.setChecked(false);
                    } else if(cancel_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.cancelC) + "");
                        cancel_cbx.setChecked(false);
                    }
                    myDB.setNewType(String.valueOf(R.color.normal_cbx), selected_patient.getText().toString());
                    holder.item.setTextColor(context.getResources().getColor(R.color.normal_cbx));
                    Toast.makeText(context, "Refresh to reload data.", Toast.LENGTH_SHORT).show();
                    myDB.insertPaymentHistory(sr_no_patient.getText().toString(), String.valueOf(R.color.normal_cbx), amount);
                    updateDayDetails(amount, "normal_count");
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        emergency_cbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(normal_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normalC) + "");
                        normal_cbx.setChecked(false);
                    } else if(normal_paper_valid_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normal_paper_validC) + "");
                        normal_paper_valid_cbx.setChecked(false);
                    } else if(paper_valid_emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.paper_valid_emergencyC) + "");
                        paper_valid_emergency_cbx.setChecked(false);
                    } else if(discount_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.discountC) + "");
                        discount_cbx.setChecked(false);
                    } else if(cancel_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.cancelC) + "");
                        cancel_cbx.setChecked(false);
                    }
                    myDB.setNewType(String.valueOf(R.color.emergency_cbx), selected_patient.getText().toString());
                    holder.item.setTextColor(context.getResources().getColor(R.color.emergency_cbx));
                    Toast.makeText(context, "Refresh to reload data.", Toast.LENGTH_SHORT).show();
                    int amount = 400;
                    myDB.insertPaymentHistory(sr_no_patient.getText().toString(), String.valueOf(R.color.emergency_cbx), amount);
                    updateDayDetails(400, "emergency_count");
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        normal_paper_valid_cbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(normal_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normalC) + "");
                        normal_cbx.setChecked(false);
                    } else if(emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.emergencyC) + "");
                        emergency_cbx.setChecked(false);
                    } else if(paper_valid_emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.paper_valid_emergencyC + ""));
                        paper_valid_emergency_cbx.setChecked(false);
                    } else if(discount_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.discountC) + "");
                        discount_cbx.setChecked(false);
                    } else if(cancel_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.cancelC) + "");
                        cancel_cbx.setChecked(false);
                    }
                    myDB.setNewType(String.valueOf(R.color.normal_paper_valid_cbx), selected_patient.getText().toString());
                    holder.item.setTextColor(context.getResources().getColor(R.color.normal_paper_valid_cbx));
                    Toast.makeText(context, "Refresh to reload data.", Toast.LENGTH_SHORT).show();
                    int amount = 0;
                    myDB.insertPaymentHistory(sr_no_patient.getText().toString(), String.valueOf(R.color.normal_paper_valid_cbx), amount);
                    updateDayDetails(0, "normal_paper_valid_count");
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        paper_valid_emergency_cbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(normal_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normalC) + "");
                        normal_cbx.setChecked(false);
                    } else if(emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.emergencyC) + "");
                        emergency_cbx.setChecked(false);
                    } else if(normal_paper_valid_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normal_paper_validC) + "");
                        normal_paper_valid_cbx.setChecked(false);
                    } else if(discount_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.discountC) + "");
                        discount_cbx.setChecked(false);
                    } else if(cancel_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.cancelC) + "");
                        cancel_cbx.setChecked(false);
                    }
                    myDB.setNewType(String.valueOf(R.color.paper_valid_emergency_cbx), selected_patient.getText().toString());
                    holder.item.setTextColor(context.getResources().getColor(R.color.paper_valid_emergency_cbx));
                    Toast.makeText(context, "Refresh to reload data.", Toast.LENGTH_SHORT).show();
                    int amount = 200;
                    myDB.insertPaymentHistory(sr_no_patient.getText().toString(), String.valueOf(R.color.paper_valid_emergency_cbx), amount);
                    updateDayDetails(200, "paper_valid_emergency_count");
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        discount_cbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(normal_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normalC) + "");
                        normal_cbx.setChecked(false);
                    } else if(emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.emergencyC) + "");
                        emergency_cbx.setChecked(false);
                    } else if(normal_paper_valid_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normal_paper_validC) + "");
                        normal_paper_valid_cbx.setChecked(false);
                    } else if(paper_valid_emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.paper_valid_emergencyC) + "");
                        paper_valid_emergency_cbx.setChecked(false);
                    } else if(cancel_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.cancelC) + "");
                        cancel_cbx.setChecked(false);
                    }
                    myDB.setNewType(String.valueOf(R.color.discount_cbx), selected_patient.getText().toString());
                    holder.item.setTextColor(context.getResources().getColor(R.color.discount_cbx));
                    Toast.makeText(context, "Refresh to reload data.", Toast.LENGTH_SHORT).show();
                    int amount = Integer.parseInt(discount_percent_txt.getText().toString()) * 2;
                    myDB.insertPaymentHistory(sr_no_patient.getText().toString(), String.valueOf(R.color.emergency_cbx), amount);
                    updateDayDetails(amount*2, "discount_count");
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel_cbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(normal_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normalC) + "");
                        normal_cbx.setChecked(false);
                    } else if(emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.emergencyC) + "");
                        emergency_cbx.setChecked(false);
                    } else if(normal_paper_valid_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.normal_paper_validC) + "");
                        normal_paper_valid_cbx.setChecked(false);
                    } else if(paper_valid_emergency_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.paper_valid_emergencyC) + "");
                        paper_valid_emergency_cbx.setChecked(false);
                    }else if(discount_cbx.isChecked()){
                        removeDayDetails(String.valueOf(R.string.cancelC) + "" );
                        discount_cbx.setChecked(false);
                    }
                    myDB.setNewType(String.valueOf(R.color.cancel_cbx), selected_patient.getText().toString());
                    holder.item.setTextColor(context.getResources().getColor(R.color.cancel_cbx));
                    Toast.makeText(context, "Refresh to reload data.", Toast.LENGTH_SHORT).show();
                    int amount = 0;
                    myDB.insertPaymentHistory(sr_no_patient.getText().toString(), String.valueOf(R.color.cancel_cbx), amount);
                    updateDayDetails(0, "cancel_count");
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CheckBox item;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            item = (CheckBox)itemView.findViewById(R.id.cbx_patient);
            itemView.setTag((RecyclerView)((MainActivity)context).findViewById(R.id.recycler_view));
        }
    }

    private void initMainClassViews(){
        normal_cbx = (CheckBox)((MainActivity)context).findViewById(R.id.normal_cbx);
        emergency_cbx = (CheckBox)((MainActivity)context).findViewById(R.id.emergency_cbx);
        normal_paper_valid_cbx = (CheckBox)((MainActivity)context).findViewById(R.id.normal_paper_valid_cbx);
        paper_valid_emergency_cbx = (CheckBox)((MainActivity)context).findViewById(R.id.paper_valid_emergency_cbx);
        discount_cbx = (CheckBox)((MainActivity)context).findViewById(R.id.discount_cbx);
        cancel_cbx = (CheckBox)((MainActivity)context).findViewById(R.id.cancel_cbx);
        recyclerView = (RecyclerView)((MainActivity)context).findViewById(R.id.recycler_view);
        discount_percent_txt = ((MainActivity)context).findViewById(R.id.discount_percent_txt);
    }
    private void updateDayDetails(int amount, String type_count){
        try {
            int currentPatientCount = myDB.senderCell("patient_count");
            int currentAmountCollected = myDB.senderCell("collected_money");
            int old_type_count = myDB.senderCell(type_count);

            int newPatientCount = currentPatientCount + 1;
            int newAmountCollected = currentAmountCollected + amount;
            int new_type_count = old_type_count + 1;

            myDB.updateColumn("patient_count", newPatientCount);
            myDB.updateColumn("collected_money", newAmountCollected);
            myDB.updateColumn(type_count, new_type_count);
        } catch(Exception ex) {
            Log.e("updateDayDetails", ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void removeDayDetails(String type){
        short amount = 0;
        switch (type) {
            case R.string.normalC + "":
                amount = 200;
                break;
            case R.string.discountC + "":
                amount = 100;
                break;
            case R.string.normal_paper_validC + "":
                amount = 0;
                break;
            case R.string.paper_valid_emergencyC + "":
                amount = 200;
                break;
            case R.string.cancelC + "":
                amount = 0;
                break;
            case R.string.emergencyC + "":
                amount = 400;
                break;
        }
        try {
            int currentPatientCount = myDB.senderCell("patient_count");
            int currentAmountCollected = myDB.senderCell("collected_money");
            int old_type_count = myDB.senderCell(type);

            int newPatientCount = currentPatientCount - 1;
            int newAmountCollected = currentAmountCollected - (int)amount;
            int new_type_count = old_type_count - 1;

            myDB.updateColumn("patient_count", newPatientCount);
            myDB.updateColumn("collected_money", newAmountCollected);
            myDB.updateColumn(type, new_type_count);
        } catch(Exception ex) {
            Log.e("updateDayDetails", ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void disableRecyclerView() {
        recyclerView.setEnabled(false);
    }
}
