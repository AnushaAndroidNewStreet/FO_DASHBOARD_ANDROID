package com.example.fodashboard.fragment;

import static android.content.ContentValues.TAG;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.graphics.Color;


import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;


import android.widget.TextView;


import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fodashboard.R;
import com.example.fodashboard.adapter.CollectionTopPerformerListAdapter;
import com.example.fodashboard.adapter.SelectBankListAdapter;
import com.example.fodashboard.adapter.TopPerformerListAdapter;
import com.example.fodashboard.adapter.UserListAdapter;
import com.example.fodashboard.adapter.UserListpfAdapter;
import com.example.fodashboard.adapter.recyclerclick.RecyclerTouchListener;


import com.example.fodashboard.common.ApiClient;
import com.example.fodashboard.common.ApiInterface;
import com.example.fodashboard.common.AppConstants;
import com.example.fodashboard.common.ConnectivityReciever;
import com.example.fodashboard.common.CustomSharedPreferences;
import com.example.fodashboard.common.MyApplication;
import com.example.fodashboard.common.ProgressBarForDataLoad;
import com.example.fodashboard.common.ServerConnection;

import com.example.fodashboard.model.BankItem;
import com.example.fodashboard.model.TopPerformerItem;
import com.example.fodashboard.model.UserItem;

import com.example.fodashboard.pojo.CurrentMonthDisbursmentData;
import com.example.fodashboard.pojo.PerforamnceSummary;
import com.example.fodashboard.pojo.ProductFuldilmentGroups;

import com.example.fodashboard.pojo.VerificationAuditProspects;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.HttpURLConnection;


import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;


import lecho.lib.hellocharts.model.Axis;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    RecyclerView topPerformersRecyclerView, collectionTopPerformersRecyclerView;
    LineChartView lineChartView, collectionLineChartView;
    PieChartView pieChartView, collectionPieChartView;
    LinearLayout disburseLinear, collectionLinear, disburseDetailsLinear, collectionDetailsLinear, startLinear, stopLinear, auditLinear, monthTypeLinear, collectionMonthTypeLinear;
    TextView txtDisburse, txtDisbursePrice, txtDisburseCustomer, txtCollection, txtCollectionPrice, txtCollectionCustomer;
    View disburseBottomView, collectionBottomView;
    ImageView changeBankMenu, hdfcBankImage, otherBankImage;
    TextView bankNameTxt;
    LinearLayout verificationLinear, productFulfillLinear, documentLinear, approvalLinear, onBoardingLinear, loanUtilizationLinear;
    LinearLayout otrLinear, linearSma0, smaDropDownLinear, linearSma1, linearSma2, linearSma3, linear1_3, linear4_10, linear11_20, linear21_30;

    String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec"};
    int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 50, 18};
    int[] yAxisData1 = {30, 20, 20, 50, 40, 18, 30, 15, 15, 50, 30, 22};

    TopPerformerItem topPerformerItem;
    List<TopPerformerItem> topPerformerData = new ArrayList<>();
    TopPerformerListAdapter tAdapter;
    CollectionTopPerformerListAdapter cAdapter;

    List yAxisValues = new ArrayList();
    List yAxisValues1 = new ArrayList();
    List axisValues = new ArrayList();
    String frequency;

    List<SliceValue> pieData = new ArrayList<>();

    Dialog d;
    public static Dialog selectBankDialog;

    MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder;
    MaterialDatePicker materialDatePicker;

    public static String selectedId = "3";

    BankItem bankItem;
    List<BankItem> bankData = new ArrayList<>();

    SelectBankListAdapter sbAdapter;
    UserListAdapter userListAdapter;
    UserListpfAdapter userListpfAdapter;

    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<String> grp_temp_arr;
    ProgressDialog ringProgressDialog = null;
    TextView m_d_amount, m_d_count, m_d_prediction, m_d_target, tv_customer_count, tv_cust_percentage;
    TextView verification_title, verification_percentage, verification_count, audit_title, audit_percentage, audit_count, pf_title, pf_percentage, pf_count, de_title, de_percentage, de_count, approval_title, approval_percentage, approval_count, oc_title, oc_percentage, oc_count, luc_title, luc_percentage, luc_count;
    ApiInterface apiInterface;
    String name;
    TextView m_dcount,tv_target,prediction,growth_percentage,conversion_percentage,lead_count,disburse_count;

    ArrayList<PerforamnceSummary> summary = new ArrayList<PerforamnceSummary>();
    ArrayList<VerificationAuditProspects> prospects = new ArrayList<VerificationAuditProspects>();
    ArrayList<ProductFuldilmentGroups> groups = new ArrayList<ProductFuldilmentGroups>();
    TextView tv_frequency,tv_month,tv_month_chart,tv_month_disburs_chart;
    ProgressBarForDataLoad progressDialog;
    String myear,month,day;
    String from_date,to_date;
    String start_date = "01-01-2022";
    String end_date = "17-01-2022";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        topPerformersRecyclerView = rootView.findViewById(R.id.topPerformersRecyclerView);
        collectionTopPerformersRecyclerView = rootView.findViewById(R.id.collectionTopPerformersRecyclerView);
        lineChartView = rootView.findViewById(R.id.lineChartView);
        collectionLineChartView = rootView.findViewById(R.id.collectionLineChartView);
        pieChartView = rootView.findViewById(R.id.pieChartView);
        collectionPieChartView = rootView.findViewById(R.id.collectionPieChartView);
        disburseLinear = rootView.findViewById(R.id.disburseLinear);
        collectionLinear = rootView.findViewById(R.id.collectionLinear);
        disburseDetailsLinear = rootView.findViewById(R.id.disburseDetailsLinear);
        collectionDetailsLinear = rootView.findViewById(R.id.collectionDetailsLinear);
        txtDisburse = rootView.findViewById(R.id.txtDisburse);
        txtDisbursePrice = rootView.findViewById(R.id.txtDisbursePrice);
        txtDisburseCustomer = rootView.findViewById(R.id.txtDisburseCustomer);
        txtCollection = rootView.findViewById(R.id.txtCollection);
        txtCollectionPrice = rootView.findViewById(R.id.txtCollectionPrice);
        txtCollectionCustomer = rootView.findViewById(R.id.txtCollectionCustomer);
        disburseBottomView = rootView.findViewById(R.id.disburseBottomView);
        collectionBottomView = rootView.findViewById(R.id.collectionBottomView);
        startLinear = rootView.findViewById(R.id.startLinear);
        stopLinear = rootView.findViewById(R.id.stopLinear);

        auditLinear = rootView.findViewById(R.id.auditLinear);
        monthTypeLinear = rootView.findViewById(R.id.monthTypeLinear);
        collectionMonthTypeLinear = rootView.findViewById(R.id.collectionMonthTypeLinear);
        changeBankMenu = rootView.findViewById(R.id.changeBankMenu);
        hdfcBankImage = rootView.findViewById(R.id.hdfcBankImage);
        otherBankImage = rootView.findViewById(R.id.otherBankImage);
        bankNameTxt = rootView.findViewById(R.id.bankNameTxt);
        verificationLinear = rootView.findViewById(R.id.verificationLinear);
        productFulfillLinear = rootView.findViewById(R.id.productFulfillLinear);
        documentLinear = rootView.findViewById(R.id.documentLinear);
        approvalLinear = rootView.findViewById(R.id.approvalLinear);
        onBoardingLinear = rootView.findViewById(R.id.onBoardingLinear);
        loanUtilizationLinear = rootView.findViewById(R.id.loanUtilizationLinear);
        otrLinear = rootView.findViewById(R.id.otrLinear);
        linearSma0 = rootView.findViewById(R.id.linearSma0);
        smaDropDownLinear = rootView.findViewById(R.id.smaDropDownLinear);
        linearSma1 = rootView.findViewById(R.id.linearSma1);
        linearSma2 = rootView.findViewById(R.id.linearSma2);
        linearSma3 = rootView.findViewById(R.id.linearSma3);
        linear1_3 = rootView.findViewById(R.id.linear1_3);
        linear4_10 = rootView.findViewById(R.id.linear4_10);
        linear11_20 = rootView.findViewById(R.id.linear11_20);
        linear21_30 = rootView.findViewById(R.id.linear21_30);
        m_d_amount = rootView.findViewById(R.id.m_d_amount);
        m_d_count = rootView.findViewById(R.id.m_d_count);
        m_d_prediction = rootView.findViewById(R.id.m_d_prediction);
        m_d_target = rootView.findViewById(R.id.m_d_target);
        tv_customer_count = rootView.findViewById(R.id.tv_customer_count);
        tv_cust_percentage = rootView.findViewById(R.id.cust_percentage);

        verification_title = rootView.findViewById(R.id.verification_title);
        verification_percentage = rootView.findViewById(R.id.verification_percentage);
        verification_count = rootView.findViewById(R.id.verification_count);

        audit_title = rootView.findViewById(R.id.audit_title);
        audit_percentage = rootView.findViewById(R.id.audit_percentage);
        audit_count = rootView.findViewById(R.id.audit_count);

        pf_title = rootView.findViewById(R.id.pf_title);
        pf_percentage = rootView.findViewById(R.id.pf_percentage);
        pf_count = rootView.findViewById(R.id.pf_count);

        de_title = rootView.findViewById(R.id.de_title);
        de_percentage = rootView.findViewById(R.id.de_percentage);
        de_count = rootView.findViewById(R.id.de_count);

        approval_title = rootView.findViewById(R.id.approval_title);
        approval_percentage = rootView.findViewById(R.id.approval_percentage);
        approval_count = rootView.findViewById(R.id.approval_count);

        oc_title = rootView.findViewById(R.id.oc_title);
        oc_percentage = rootView.findViewById(R.id.oc_percentage);
        oc_count = rootView.findViewById(R.id.oc_count);

        luc_title = rootView.findViewById(R.id.luc_title);
        luc_percentage = rootView.findViewById(R.id.luc_percentage);
        luc_count = rootView.findViewById(R.id.luc_count);

        startLinear.setVisibility(View.GONE);
        m_dcount = rootView.findViewById(R.id.m_dcount);
        tv_target = rootView.findViewById(R.id.tv_target);
        prediction = rootView.findViewById(R.id.prediction);
        disburse_count = rootView.findViewById(R.id.disburse_count);
        lead_count = rootView.findViewById(R.id.lead_count);
        conversion_percentage = rootView.findViewById(R.id.tv_conversionpercentage);
        growth_percentage = rootView.findViewById(R.id.growth_percentage);
        tv_frequency = rootView.findViewById(R.id.frequency);
        tv_month = rootView.findViewById(R.id.tv_month);
        tv_month_chart = rootView.findViewById(R.id.tv_month_chart);
        tv_month_disburs_chart = rootView.findViewById(R.id.tv_month_disburs_chart);

        int month = Calendar.getInstance().get(Calendar.MONTH);
        String month_text = getMonth(month+1);
        progressDialog = new ProgressBarForDataLoad();
        tv_month.setText(month_text);
        tv_month_chart.setText(month_text);
        tv_month_disburs_chart.setText(month_text);

        CustomSharedPreferences.saveStringData(getActivity(), "JLG", CustomSharedPreferences.SP_KEY.PRODUCT_NAME);

        try {
            getData(CustomSharedPreferences.getStringData(getActivity(), CustomSharedPreferences.SP_KEY.PRODUCT_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tAdapter = new TopPerformerListAdapter(getActivity(), topPerformerData);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        topPerformersRecyclerView.setLayoutManager(mLayoutManager);
        topPerformersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topPerformersRecyclerView.addItemDecoration(new DividerItemDecoration(topPerformersRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        topPerformersRecyclerView.setAdapter(tAdapter);



       /* for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        for (int i = 0; i < yAxisData1.length; i++) {
            yAxisValues1.add(new PointValue(i, yAxisData1[i]));
        }*/



        pieData.add(new SliceValue(10, Color.parseColor("#ffe8ea")));
        pieData.add(new SliceValue(12, Color.parseColor("#ff949f")));
        pieData.add(new SliceValue(23, Color.parseColor("#ff949f")));
        pieData.add(new SliceValue(18, Color.parseColor("#ff4558")));
        pieData.add(new SliceValue(15, Color.parseColor("#e8152a")));
        pieData.add(new SliceValue(12, Color.parseColor("#b51021")));
        pieData.add(new SliceValue(10, Color.parseColor("#8c0d19")));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasCenterCircle(true).setCenterText1("");
        pieChartView.setPieChartData(pieChartData);
        collectionPieChartView.setPieChartData(pieChartData);


        materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();

        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder.setTitleText("SELECT A DATE");
        materialDateBuilder.setTheme(R.style.MaterialCalendarTheme);

        // now create the instance of the material date
        // picker
        materialDatePicker = materialDateBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                long startMilliSeconds = selection.first;
                long endMilliSeconds = selection.second;

                DateFormat simple = new SimpleDateFormat("dd-MM-yyyy");

                // Creating date from milliseconds
                // using Date() constructor
                Date result = new Date(startMilliSeconds);
                from_date = simple.format(result);
                Date result1 = new Date(endMilliSeconds);
                to_date = simple.format(result1);
                getConversionChartData(from_date,to_date,frequency);

            }
        });

//        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
////        materialDateBuilder.setCalendarConstraints(limitRange().build());
//        // now define the properties of the
//        // materialDateBuilder that is title text as SELECT A DATE
//        materialDateBuilder.setTitleText("SELECT A DATE");
//        materialDateBuilder.setTheme(R.style.MaterialCalendarTheme);
//
//        // now create the instance of the material date
//        // picker
//        materialDatePicker = materialDateBuilder.build();
//
//        // handle select date button which opens the
//        // material design date picker
//
//        // now handle the positive button click from the
//        // material design date picker
//
//        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
//            @Override
//            public void onPositiveButtonClick(Long selectedDate) {
//                // user has selected a date
//                // format the date and set the text of the input box to be the selected date
//                // right now this format is hard-coded, this will change
//                ;
//                // Get the offset from our timezone and UTC.
//
//            }
//        });

        bankItem = new BankItem();
        bankItem.setBankImage(R.drawable.sindicate_11);
        bankItem.setBackName("Syndiacate Bank");
        bankItem.setSortName("SYD");
        bankItem.setProductName("Product Name");
        bankItem.setId("1");
        bankData.add(bankItem);

        bankItem = new BankItem();
        bankItem.setBankImage(R.drawable.fedral_11);
        bankItem.setBackName("Federal Bank");
        bankItem.setSortName("FDL");
        bankItem.setProductName("JLG");
        bankItem.setId("2");
        bankData.add(bankItem);

        bankItem = new BankItem();
        bankItem.setBankImage(R.drawable.hdfc_11);
        bankItem.setBackName("HDFC Bank");
        bankItem.setSortName("HDFC");
        bankItem.setProductName("JLG");
        bankItem.setId("3");
        bankData.add(bankItem);

        disburseLinear.setOnClickListener(this);
        collectionLinear.setOnClickListener(this);
        startLinear.setOnClickListener(this);
        stopLinear.setOnClickListener(this);
        auditLinear.setOnClickListener(this);
        monthTypeLinear.setOnClickListener(this);
        collectionMonthTypeLinear.setOnClickListener(this);
        changeBankMenu.setOnClickListener(this);
        verificationLinear.setOnClickListener(this);
        productFulfillLinear.setOnClickListener(this);
        documentLinear.setOnClickListener(this);
        approvalLinear.setOnClickListener(this);
        onBoardingLinear.setOnClickListener(this);
        loanUtilizationLinear.setOnClickListener(this);
        otrLinear.setOnClickListener(this);
        linearSma0.setOnClickListener(this);
        smaDropDownLinear.setOnClickListener(this);
        linearSma1.setOnClickListener(this);
        linearSma2.setOnClickListener(this);
        linearSma3.setOnClickListener(this);
        linear1_3.setOnClickListener(this);
        linear4_10.setOnClickListener(this);
        linear11_20.setOnClickListener(this);
        linear21_30.setOnClickListener(this);

        return rootView;
    }

    private String getMonth(int month) {
        String month_text;
        switch (month){
            case 1:
                month_text = "January";
           return  month_text;
            case 2:
                month_text = "February";
                return  month_text;
            case 3:
                month_text = "March";
                return  month_text;
            case 4:
                month_text = "April";
                return  month_text;
            case 5:
                month_text = "May";
                return  month_text;
            case 6:
                month_text = "June";
                return  month_text;
            case 7:
                month_text = "July";
                return  month_text;
            case 8:
                month_text = "August";
                return  month_text;
            case 9:
                month_text = "September";
                return  month_text;
            case 10:
                month_text = "November";
                return  month_text;
            case 11:
                month_text = "December";
                return  month_text;



        }
        return "january";
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.disburseLinear:
                disburseBottomView.setVisibility(View.VISIBLE);
                collectionBottomView.setVisibility(View.INVISIBLE);
                disburseDetailsLinear.setVisibility(View.VISIBLE);
                collectionDetailsLinear.setVisibility(View.GONE);

                disburseLinear.setBackgroundColor(Color.parseColor("#ffffff"));
                collectionLinear.setBackgroundColor(Color.parseColor("#f4f4f4"));

                txtCollection.setTextColor(Color.parseColor("#525252"));
                txtCollectionCustomer.setTextColor(Color.parseColor("#5b5b5b"));
                txtCollectionPrice.setTextColor(Color.parseColor("#5b5b5b"));

                txtDisburse.setTextColor(Color.parseColor("#000000"));
                txtDisburseCustomer.setTextColor(Color.parseColor("#323232"));
                txtDisburseCustomer.setTextColor(Color.parseColor("#323232"));
                break;

            case R.id.collectionLinear:
                disburseBottomView.setVisibility(View.INVISIBLE);
                collectionBottomView.setVisibility(View.VISIBLE);
                disburseDetailsLinear.setVisibility(View.GONE);
                collectionDetailsLinear.setVisibility(View.VISIBLE);

                disburseLinear.setBackgroundColor(Color.parseColor("#f4f4f4"));
                collectionLinear.setBackgroundColor(Color.parseColor("#ffffff"));

                txtCollection.setTextColor(Color.parseColor("#000000"));
                txtCollectionCustomer.setTextColor(Color.parseColor("#323232"));
                txtCollectionPrice.setTextColor(Color.parseColor("#323232"));

                txtDisburse.setTextColor(Color.parseColor("#525252"));
                txtDisburseCustomer.setTextColor(Color.parseColor("#5b5b5b"));
                txtDisburseCustomer.setTextColor(Color.parseColor("#5b5b5b"));

                break;

            case R.id.startLinear:
                startLinear.setVisibility(View.GONE);
                stopLinear.setVisibility(View.VISIBLE);
                break;

            case R.id.stopLinear:
                stopLinear.setVisibility(View.GONE);
                startLinear.setVisibility(View.VISIBLE);
                break;

            case R.id.verificationLinear:
                name = "verification";
                getVerificationCustomers(verification_title.getText().toString(), "verification");
                break;

            case R.id.auditLinear:
                name = "audit";
                getVerificationCustomers(audit_title.getText().toString(), "audit");
                break;

            case R.id.productFulfillLinear:
                name = "pf";
                getProductFulfilmentDialog(pf_title.getText().toString(), "pf");
                break;

            case R.id.documentLinear:
                name = "de";
                getProductFulfilmentDialog(de_title.getText().toString(), "de");
                break;

            case R.id.approvalLinear:
                name = "approval";
                getProductFulfilmentDialog(approval_title.getText().toString(), "approval");
                break;

            case R.id.onBoardingLinear:
                name = "oc";
                getProductFulfilmentDialog(oc_title.getText().toString(), "oc");
                break;

            case R.id.loanUtilizationLinear:
                name = "luc";
                getProductFulfilmentDialog(luc_title.getText().toString(), "luc");
                break;


            case R.id.otrLinear:
                //  openDialog("OTR", R.drawable.ic_icon_loan_utilication, "#8C0D19", false);
                break;

            case R.id.linearSma0:
                if (smaDropDownLinear.getVisibility() == View.VISIBLE) {
                    smaDropDownLinear.setVisibility(View.GONE);
                } else {
                    smaDropDownLinear.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.linearSma1:
                //  openDialog("SMA 1", R.drawable.ic_icon_loan_utilication, "#8C0D19", false);
                break;

            case R.id.linearSma2:
                // openDialog("SMA 2", R.drawable.ic_icon_loan_utilication, "#8C0D19", false);
                break;

            case R.id.linearSma3:
                //  openDialog("SMA 3", R.drawable.ic_icon_loan_utilication, "#8C0D19", false);
                break;

            case R.id.linear1_3:
                //  openDialog("01 - 03", R.drawable.ic_icon_loan_utilication, "#8C0D19", false);
                break;

            case R.id.linear4_10:
                // openDialog("04 - 10", R.drawable.ic_icon_loan_utilication, "#8C0D19", false);
                break;

            case R.id.linear11_20:
                //  openDialog("11 - 20", R.drawable.ic_icon_loan_utilication, "#8C0D19", false);
                break;

            case R.id.linear21_30:
                //   openDialog("21 - 30", R.drawable.ic_icon_loan_utilication, "#8C0D19", false);
                break;

            case R.id.collectionMonthTypeLinear:
            case R.id.monthTypeLinear:
                openMonthTypeDialog();
                break;

            case R.id.changeBankMenu:
                openSelectBankDialog();
                break;
        }
    }

    private void getProductFulfilmentDialog(String title, String name) {
        final JSONArray[] array = {null};
        progressDialog.showProgress(getActivity(),"fetching" + " "+ title);

        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();


        JsonObject payload = new JsonObject();
        payload.addProperty("productType", "JLG");
        payload.addProperty("userId", "S0272");

        payload.addProperty("type", AppConstants.PENDING_STATUS_CUSTOMER);
        payload.addProperty("status", title);
        mainObj.add("Payload", payload);


        if(ConnectivityReciever.isConnected(getActivity())) {
            Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
            call1.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {

                        if (response.isSuccessful() && response.code() == 200) {
                            progressDialog.progressDialog.dismiss();

                            JsonObject gson = new JsonParser().parse(response.body().toString()).getAsJsonObject();

                            try {
                                JSONObject jo2 = new JSONObject(gson.toString());
                                JSONObject object = (JSONObject) jo2.get("Data");

                                String title = object.getString("title");
                                if (!object.get("groups").toString().equalsIgnoreCase("null")) {
                                    array[0] = (JSONArray) object.get("groups");
                                }
                                if (array[0] != null) {

                                    for (int i = 0; i < array[0].length(); i++) {
                                        ProductFuldilmentGroups productFuldilmentGroups = new ProductFuldilmentGroups();
                                        productFuldilmentGroups.setGroupId(array[0].getJSONObject(i).getString("groupId"));
                                        productFuldilmentGroups.setGroupImage(array[0].getJSONObject(i).getString("groupImage"));
                                        productFuldilmentGroups.setGroupName(array[0].getJSONObject(i).getString("groupName"));
                                        productFuldilmentGroups.setLoanAmount(Double.valueOf(array[0].getJSONObject(i).getString("loanAmount")));
                                        groups.add(productFuldilmentGroups);
                                    }

                                    if (name.equalsIgnoreCase("pf")) {
                                    openDialog(title, R.drawable.ic_icon_product_fullfilment, "#FF949F", true, pf_count.getText().toString());

                                } else if (name.equalsIgnoreCase("de")) {
                                    openDialog(de_title.getText().toString(), R.drawable.ic_icon_document_execution, "#FF4558", true, de_count.getText().toString());


                                } else if (name.equalsIgnoreCase("approval")) {
                                    openDialog(approval_title.getText().toString(), R.drawable.ic_icon_approval, "#E8152A", true, approval_count.getText().toString());

                                } else if (name.equalsIgnoreCase("oc")) {
                                    openDialog(oc_title.getText().toString(), R.drawable.ic_icon_on_boarding_call, "#B51021", true, oc_count.getText().toString());

                                } else {
                                    openDialog(luc_title.getText().toString(), R.drawable.ic_icon_loan_utilication, "#8C0D19", true, luc_count.getText().toString());
                                }
                            }else{
                                    ConnectivityReciever.errorDialogue(getActivity(),"Details not found");
                                }
                                  } catch(JSONException e){
                                    e.printStackTrace();
                                }
                        } else if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED){
                            progressDialog.progressDialog.dismiss();
                            ConnectivityReciever.errorDialogue(getActivity(),"Unauthorized");
                        }

                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.progressDialog.dismiss();
                    ConnectivityReciever.errorDialogue(getActivity(),t.getMessage());

                }
            });
        }else{
            progressDialog.progressDialog.dismiss();
            ConnectivityReciever.errorDialogue(getActivity(),"Internet is not available");
        }


    }


    private void getVerificationCustomers(String title, String name) {
        progressDialog.showProgress(getActivity(),"fetching" + " "+ title);

        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();


        JsonObject payload = new JsonObject();
        payload.addProperty("productType", "JLG");
        payload.addProperty("userId", "S0346");

        payload.addProperty("type", AppConstants.PENDING_STATUS_CUSTOMER);
        payload.addProperty("status", title);
        mainObj.add("Payload", payload);

        if(ConnectivityReciever.isConnected(getActivity())) {

            Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
            call1.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {

                        if (response.isSuccessful() && response.code() == 200) {
                            progressDialog.progressDialog.dismiss();

                            JsonObject gson = new JsonParser().parse(response.body().toString()).getAsJsonObject();

                            try {
                                JSONObject jo2 = new JSONObject(gson.toString());
                                JSONObject object = (JSONObject) jo2.get("Data");

                                String title = object.getString("title");
                                JSONArray array = (JSONArray) object.get("prospects");

                                for (int i = 0; i < array.length(); i++) {
                                    VerificationAuditProspects auditProspects = new VerificationAuditProspects();
                                    auditProspects.setName(array.getJSONObject(i).getString("name"));
                                    auditProspects.setImage(array.getJSONObject(i).getString("image"));
                                    auditProspects.setMobile(array.getJSONObject(i).getString("mobile"));
                                    prospects.add(auditProspects);
                                }


                                if (title.equalsIgnoreCase("verification"))
                                    openDialog(title, R.drawable.ic_icon_verification, "#FFE8EA", true, verification_count.getText().toString());
                                else
                                    openDialog(title, R.drawable.ic_icon_audit, "#FFB7BE", true, audit_count.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            progressDialog.progressDialog.dismiss();
                            ConnectivityReciever.errorDialogue(getActivity(), "Unauthorized");
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e(TAG, "Login API Failed ");
                    progressDialog.progressDialog.dismiss();
                    ConnectivityReciever.errorDialogue(getActivity(), t.getMessage());

                }


            });
        }else{
            progressDialog.progressDialog.dismiss();
            ConnectivityReciever.errorDialogue(getActivity(),"Internet connection is not available");

        }

    }


    public void openDialog(String name, int image, String colorCode, boolean isShowIcon, String count) {
        d = new BottomSheetDialog(getActivity(), R.style.SheetDialog);
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_audit, null);
        d.setContentView(sheetView);

        TextView txtProductName = d.findViewById(R.id.txtProductName);
        TextView txtLastPending = d.findViewById(R.id.txtLastPending);
        ImageView logoImg = d.findViewById(R.id.logoImg);
        LinearLayout logoLinear = d.findViewById(R.id.logoLinear);
        ImageView closeDialogImg = d.findViewById(R.id.closeDialogImg);
        RecyclerView userRecyclerView = d.findViewById(R.id.userRecyclerView);
        TextView pending_count = d.findViewById(R.id.pending_count);

        if (isShowIcon) {
            logoLinear.setVisibility(View.VISIBLE);
        } else {
            logoLinear.setVisibility(View.GONE);
        }

        logoLinear.setBackgroundColor(Color.parseColor(colorCode));

        txtProductName.setText(name);
        txtLastPending.setText("Leads pending at " + name);
        logoImg.setImageResource(image);
        pending_count.setText(count);

        if (name.equalsIgnoreCase("verification") || name.equalsIgnoreCase("audit")) {
            userListAdapter = new UserListAdapter(getActivity(), prospects, name);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            userRecyclerView.setLayoutManager(mLayoutManager);
            userRecyclerView.setItemAnimator(new DefaultItemAnimator());
            userRecyclerView.setAdapter(userListAdapter);
        } else {
            userListpfAdapter = new UserListpfAdapter(getActivity(), groups, name);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            userRecyclerView.setLayoutManager(mLayoutManager);
            userRecyclerView.setItemAnimator(new DefaultItemAnimator());
            userRecyclerView.setAdapter(userListpfAdapter);
        }

        closeDialogImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        d.show();
    }

    public void openMonthTypeDialog() {
        d = new BottomSheetDialog(getActivity());
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_month_type, null);
        d.setContentView(sheetView);

        LinearLayout thisMonthLinear = d.findViewById(R.id.thisMonthLinear);
        TextView txtThisMonth = d.findViewById(R.id.txtThisMonth);
        ImageView thisMonthImg = d.findViewById(R.id.thisMonthImg);
        LinearLayout lastMonthLinear = d.findViewById(R.id.lastMonthLinear);
        TextView txtLastMonth = d.findViewById(R.id.txtLastMonth);
        ImageView lastMonthImg = d.findViewById(R.id.lastMonthImg);
        LinearLayout thisQuarter = d.findViewById(R.id.thisQuarter);
        TextView txtThisQuarter = d.findViewById(R.id.txtThisQuarter);
        ImageView thisQuarterImg = d.findViewById(R.id.thisQuarterImg);
        LinearLayout lastQuarterLinear = d.findViewById(R.id.lastQuarterLinear);
        TextView txtLastQuarter = d.findViewById(R.id.txtLastQuarter);
        ImageView lastQuarterImg = d.findViewById(R.id.lastQuarterImg);
        LinearLayout customRangeLinear = d.findViewById(R.id.customRangeLinear);
        TextView txtCustomRange = d.findViewById(R.id.txtCustomRange);
        ImageView customRangeImg = d.findViewById(R.id.customRangeImg);
        TextView txtCancel = d.findViewById(R.id.txtCancel);

        thisMonthLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frequency = "TM";


                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();

                String dateToString = dateFormat.format(cal.getTime());

                cal.set(Calendar.DAY_OF_MONTH, 1);
                String first_date_of_month = dateFormat.format(cal.getTime());


               txtThisMonth.setTextColor(getResources().getColor(R.color.selected_color));
                txtLastMonth.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtThisQuarter.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtLastQuarter.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtCustomRange.setTextColor(getResources().getColor(R.color.non_selected_color));
                thisMonthImg.setVisibility(View.VISIBLE);
                lastMonthImg.setVisibility(View.GONE);
                thisQuarterImg.setVisibility(View.GONE);
                lastQuarterImg.setVisibility(View.GONE);
                customRangeImg.setVisibility(View.GONE);
                getConversionChartData(first_date_of_month,dateToString,frequency);

                 d.dismiss();
            }
        });

        lastMonthLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frequency = "LM";


                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar aCalendar = Calendar.getInstance();

                aCalendar.add(Calendar.MONTH, -1);

                aCalendar.set(Calendar.DATE, 1);

                String firstDateOfPreviousMonth = dateFormat.format(aCalendar.getTime());


                aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                String lastDateOfPreviousMonth = dateFormat.format(aCalendar.getTime());;

                txtThisMonth.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtLastMonth.setTextColor(getResources().getColor(R.color.selected_color));
                txtThisQuarter.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtLastQuarter.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtCustomRange.setTextColor(getResources().getColor(R.color.non_selected_color));
                thisMonthImg.setVisibility(View.GONE);
                lastMonthImg.setVisibility(View.VISIBLE);
                thisQuarterImg.setVisibility(View.GONE);
                lastQuarterImg.setVisibility(View.GONE);
                customRangeImg.setVisibility(View.GONE);
                getConversionChartData(firstDateOfPreviousMonth,lastDateOfPreviousMonth,frequency);


                 d.dismiss();
            }
        });

        thisQuarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frequency = "TQ";


                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar aCalendar = Calendar.getInstance();
                Calendar bCalendar = Calendar.getInstance();

                aCalendar.add(Calendar.MONTH, -3);

                aCalendar.set(Calendar.DATE, 1);
                String firstDateOfquarter = dateFormat.format(aCalendar.getTime());
                bCalendar.add(Calendar.MONTH, -1);
                bCalendar.set(Calendar.DATE,     bCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                String lastDateOfquarter = dateFormat.format(bCalendar.getTime());


                txtThisMonth.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtLastMonth.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtThisQuarter.setTextColor(getResources().getColor(R.color.selected_color));
                txtLastQuarter.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtCustomRange.setTextColor(getResources().getColor(R.color.non_selected_color));
                thisMonthImg.setVisibility(View.GONE);
                lastMonthImg.setVisibility(View.GONE);
                thisQuarterImg.setVisibility(View.VISIBLE);
                lastQuarterImg.setVisibility(View.GONE);
                customRangeImg.setVisibility(View.GONE);
                getConversionChartData(firstDateOfquarter,lastDateOfquarter,frequency);


                 d.dismiss();
            }
        });

        lastQuarterLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frequency = "LQ";

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                Calendar aCalendar = Calendar.getInstance();
                Calendar bCalendar = Calendar.getInstance();

                aCalendar.add(Calendar.MONTH, -6);

                aCalendar.set(Calendar.DATE, 1);
                String firstDateOfquarter = dateFormat.format(aCalendar.getTime());
                bCalendar.add(Calendar.MONTH, -3);
                bCalendar.set(Calendar.DATE,     bCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                String lastDateOfquarter = dateFormat.format(bCalendar.getTime());

                txtThisMonth.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtLastMonth.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtThisQuarter.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtLastQuarter.setTextColor(getResources().getColor(R.color.selected_color));
                txtCustomRange.setTextColor(getResources().getColor(R.color.non_selected_color));
                thisMonthImg.setVisibility(View.GONE);
                lastMonthImg.setVisibility(View.GONE);
                thisQuarterImg.setVisibility(View.GONE);
                lastQuarterImg.setVisibility(View.VISIBLE);
                customRangeImg.setVisibility(View.GONE);
                getConversionChartData(firstDateOfquarter,lastDateOfquarter,frequency);

                 d.dismiss();
            }
        });

        customRangeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frequency = "CD";



                txtThisMonth.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtLastMonth.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtThisQuarter.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtLastQuarter.setTextColor(getResources().getColor(R.color.non_selected_color));
                txtCustomRange.setTextColor(getResources().getColor(R.color.selected_color));
                thisMonthImg.setVisibility(View.GONE);
                lastMonthImg.setVisibility(View.GONE);
                thisQuarterImg.setVisibility(View.GONE);
                lastQuarterImg.setVisibility(View.GONE);
                customRangeImg.setVisibility(View.VISIBLE);

               final Calendar c = Calendar.getInstance();
               mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.MaterialCalendarTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                           public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                myear = String.valueOf(mYear);
                                 month = String.valueOf(monthOfYear + 1);
                                 day = String.valueOf(dayOfMonth);
                               if (monthOfYear + 1 < 10) {
                                   month = "0" + (monthOfYear + 1);
                                }
                                if (dayOfMonth < 10) {
                                    day = "0" + dayOfMonth;
                                } }

                      }, mYear, mMonth, mDay);


                materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");


               d.dismiss();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        d.show();
    }

    public void openSelectBankDialog() {
        selectBankDialog = new BottomSheetDialog(getActivity());
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_select_bank, null);
        selectBankDialog.setContentView(sheetView);

        ImageView closeDialogImg = selectBankDialog.findViewById(R.id.closeDialogImg);
        RecyclerView selectBankRecyclerView = selectBankDialog.findViewById(R.id.selectBankRecyclerView);

        sbAdapter = new SelectBankListAdapter(getActivity(), bankData);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        selectBankRecyclerView.setLayoutManager(mLayoutManager);
        selectBankRecyclerView.setItemAnimator(new DefaultItemAnimator());
        selectBankRecyclerView.setAdapter(sbAdapter);

        closeDialogImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBankDialog.dismiss();
            }
        });

        selectBankRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), selectBankRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                selectedId = bankData.get(position).getId();
                sbAdapter.notifyDataSetChanged();
                bankNameTxt.setText(bankData.get(position).getSortName() + " - JLG");
                try {
                    getData(bankData.get(position).getProductName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomSharedPreferences.saveStringData(getActivity(), bankData.get(position).getProductName(), CustomSharedPreferences.SP_KEY.PRODUCT_NAME);
                if (bankData.get(position).getId().equalsIgnoreCase("3")) {
                    hdfcBankImage.setVisibility(View.VISIBLE);
                    otherBankImage.setVisibility(View.GONE);
                } else {
                    hdfcBankImage.setVisibility(View.GONE);
                    otherBankImage.setVisibility(View.VISIBLE);
                    otherBankImage.setImageResource(bankData.get(position).getBankImage());
                }
                selectBankDialog.dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        selectBankDialog.show();
    }



    public void getData(String productname) throws JSONException {
        progressDialog.showProgress(getActivity(),"fetching current data");

        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        String dateToString = dateFormat.format(cal.getTime());


        JsonObject payload = new JsonObject();
        payload.addProperty("productType", productname);
        payload.addProperty("userId", "S0334");


        payload.addProperty("from", start_date);
        payload.addProperty("to", start_date);
        payload.addProperty("type", AppConstants.DISBURSMENT_TODAY);
        mainObj.add("Payload", payload);
        if(ConnectivityReciever.isConnected(getActivity())) {
            Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
            call1.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {

                        if (response.isSuccessful() && response.code() == 200) {
                            progressDialog.progressDialog.dismiss();
                            JsonObject jsonObject = response.body();

                            if(!jsonObject.get("Data").toString().equalsIgnoreCase("null")) {
                                JsonObject data = (JsonObject) jsonObject.get("Data");

                                if (data != null && !data.toString().equalsIgnoreCase("null")) {
                             /*   TodayDisbursmentData current_data = new TodayDisbursmentData();
                                current_data.setDisbursementAmt(data.get("disbursementAmt").getAsDouble());
                                current_data.setDisbursementCount(data.get("disbursementCount").getAsInt());

                                TodayDisbursmentData c_data = new TodayDisbursmentData();*/
                                    if (data.get("disbursementAmt") != null)
                                        txtDisbursePrice.setText("$" + ConnectivityReciever.format(data.get("disbursementAmt").getAsDouble()));
                                    txtDisburseCustomer.setText("$" + data.get("disbursementCount").getAsInt());
                                }
                            }


                            getMonthData();


                        } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            progressDialog.progressDialog.dismiss();
                            ConnectivityReciever.errorDialogue(getActivity(), "Unauthorized");

                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.progressDialog.dismiss();
                    ConnectivityReciever.errorDialogue(getActivity(), t.getMessage());
                    return;


                }


            });
        }else{
            progressDialog.progressDialog.dismiss();
            ConnectivityReciever.errorDialogue(getActivity(), "Internet is not available");
        }


    }


    private void getMonthData() {
        progressDialog.showProgress(getActivity(),"fetching month data");

        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();

        String dateToString = dateFormat.format(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, 1);
        String first_date_of_month = dateFormat.format(cal.getTime());

        JsonObject payload = new JsonObject();
        payload.addProperty("productType", "JLG");
        payload.addProperty("userId", "S0334");
     //   payload.addProperty("from", first_date_of_month);
     //   payload.addProperty("to", dateToString);
        payload.addProperty("from", start_date);
        payload.addProperty("to", end_date);
        payload.addProperty("type", AppConstants.DISBURSMENT_MONTH);
        mainObj.add("Payload", payload);
        if(ConnectivityReciever.isConnected(getActivity())) {
            Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
            call1.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {

                        if (response.isSuccessful() && response.code() == 200) {
                            JsonObject jsonObject = response.body();
                            progressDialog.progressDialog.dismiss();

                            JsonObject data = (JsonObject) jsonObject.get("Data");
                            if (data != null) {
                                CurrentMonthDisbursmentData current_data = new CurrentMonthDisbursmentData();
                                current_data.setDisbursementAmt(data.get("disbursementAmt").getAsDouble());
                                current_data.setDisbursementCount(data.get("disbursementCount").getAsInt());
                                current_data.setPrediction(data.get("prediction").toString());
                                current_data.setTarget(data.get("target").getAsInt());

                                CurrentMonthDisbursmentData m_data = new CurrentMonthDisbursmentData();
                                if(data.get("disbursementAmt")!=null)
                                m_d_amount.setText("$" + ConnectivityReciever.format((data.get("disbursementAmt").getAsDouble())));
                                m_d_count.setText(String.valueOf(data.get("disbursementCount").getAsInt()));
                                m_d_target.setText("$" + data.get("target").getAsInt());
                                m_d_prediction.setText(data.get("prediction").toString());
                            }
                            try {
                                getTopPerfomerData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            progressDialog.progressDialog.dismiss();
                            ConnectivityReciever.errorDialogue(getActivity(), "Unauthorized");

                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.progressDialog.dismiss();
                    ConnectivityReciever.errorDialogue(getActivity(), t.getMessage());

                }


            });
        }else{
            progressDialog.progressDialog.dismiss();
            ConnectivityReciever.errorDialogue(getActivity(),"Internet is not available");
        }


    }

    private void getTopPerfomerData() throws JSONException {
        progressDialog.showProgress(getActivity(),"fetching top performers");

        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();

        String dateToString = dateFormat.format(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, 1);
        String first_date_of_month = dateFormat.format(cal.getTime());

        JsonObject payload = new JsonObject();
        payload.addProperty("productType", "JLG");
        payload.addProperty("userId", "S0334");
      //  payload.addProperty("from", first_date_of_month);
       // payload.addProperty("to", dateToString);
        payload.addProperty("from", start_date);
        payload.addProperty("to", end_date);
        payload.addProperty("type", AppConstants.PERFOMENCE_REPORT);
        mainObj.add("Payload", payload);

        if(ConnectivityReciever.isConnected(getActivity())) {


            Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
            call1.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {

                        if (response.isSuccessful() && response.code() == 200) {
                            progressDialog.progressDialog.dismiss();

                            JsonObject gson = new JsonParser().parse(response.body().toString()).getAsJsonObject();

                            try {
                                JSONObject jo2 = new JSONObject(gson.toString());
                                JSONObject object = (JSONObject) jo2.get("Data");
                                if (object != null && object.length() != 0) {
                                    JSONArray array = (JSONArray) object.getJSONArray("performanceSummary");

                                    if (array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            PerforamnceSummary sum = new PerforamnceSummary();
                                            sum.setAmount(Double.valueOf(array.getJSONObject(i).get("amount").toString()));
                                            sum.setCount(Integer.parseInt(array.getJSONObject(i).get("count").toString()));
                                            sum.setFieldOfficerName(array.getJSONObject(i).get("fieldOfficerName").toString());
                                            sum.setLocaleName(array.getJSONObject(i).get("localeName").toString());
                                            summary.add(sum);
                                        }

                                        cAdapter = new CollectionTopPerformerListAdapter(getActivity(), summary);
                                        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                        topPerformersRecyclerView.setLayoutManager(mLayoutManager1);
                                        topPerformersRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                        //   collectionTopPerformersRecyclerView.addItemDecoration(new DividerItemDecoration(topPerformersRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                        topPerformersRecyclerView.setAdapter(cAdapter);
                                    }
                                }

                                getPendingStatus();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            progressDialog.progressDialog.dismiss();
                            ConnectivityReciever.errorDialogue(getActivity(), "Unauthorized");

                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.progressDialog.dismiss();
                    ConnectivityReciever.errorDialogue(getActivity(), t.getMessage());

                }


            });
        }else{
            progressDialog.progressDialog.dismiss();
            ConnectivityReciever.errorDialogue(getActivity(), "Internet is not available");
        }

    }

    private void getPendingStatus() throws JSONException {
        progressDialog.showProgress(getActivity(),"fetching pending status");
        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();


        JsonObject payload = new JsonObject();
        payload.addProperty("productType", "JLG");
        payload.addProperty("userId", "S0334");

        payload.addProperty("type", AppConstants.PENDING_STATUS);
        mainObj.add("Payload", payload);

        if(ConnectivityReciever.isConnected(getActivity())) {
            Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
            call1.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {

                        if (response.isSuccessful() && response.code() == 200) {
                            progressDialog.progressDialog.dismiss();

                            JsonObject gson = new JsonParser().parse(response.body().toString()).getAsJsonObject();

                            try {
                                JSONObject jo2 = new JSONObject(gson.toString());
                                JSONObject object = (JSONObject) jo2.get("Data");
                                JSONArray array = (JSONArray) object.get("summaryStatus");
                                tv_customer_count.setText(object.get("customerCount").toString());

                                JSONObject object1 = array.getJSONObject(0);

                                verification_title.setText(object1.get("title").toString());
                                verification_count.setText(object1.get("customerCount").toString());
                                verification_percentage.setText(object1.get("percent").toString());

                                JSONObject object2 = array.getJSONObject(1);

                                audit_title.setText(object2.get("title").toString());
                                audit_count.setText(object2.get("customerCount").toString());
                                audit_percentage.setText(object2.get("percent").toString());

                                JSONObject object3 = array.getJSONObject(2);

                                pf_title.setText(object3.get("title").toString());
                                pf_count.setText(object3.get("customerCount").toString());
                                pf_percentage.setText(object3.get("percent").toString());

                                JSONObject object4 = array.getJSONObject(3);

                                approval_title.setText(object4.get("title").toString());
                                approval_count.setText(object4.get("customerCount").toString());
                                approval_percentage.setText(object4.get("percent").toString());

                                JSONObject object5 = array.getJSONObject(4);

                                de_title.setText(object5.get("title").toString());
                                de_count.setText(object5.get("customerCount").toString());
                                de_percentage.setText(object5.get("percent").toString());

                                JSONObject object6 = array.getJSONObject(5);

                                oc_title.setText(object6.get("title").toString());
                                oc_count.setText(object6.get("customerCount").toString());
                                oc_percentage.setText(object6.get("percent").toString());

                                JSONObject object7 = array.getJSONObject(6);

                                luc_title.setText(object7.get("title").toString());
                                luc_percentage.setText(object7.get("customerCount").toString());
                                luc_count.setText(object7.get("percent").toString());

                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar cal = Calendar.getInstance();

                                String dateToString = dateFormat.format(cal.getTime());

                                cal.set(Calendar.DAY_OF_MONTH, 1);
                                String first_date_of_month = dateFormat.format(cal.getTime());
                                frequency = "TM";

                                getConversionChartData(first_date_of_month, dateToString, frequency);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED){
                            progressDialog.progressDialog.dismiss();
                            ConnectivityReciever.errorDialogue(getActivity(),"Unauthorized");
                        }
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.progressDialog.dismiss();
                    ConnectivityReciever.errorDialogue(getActivity(),t.getMessage());

                }
            });
        }else{
            progressDialog.progressDialog.dismiss();
            ConnectivityReciever.errorDialogue(getActivity(),"Internet is not available");
        }

    }

    private void getConversionChartData(String from_date, String todate, String frquency) {
        try {
            frequency = frquency;


            progressDialog.showProgress(getActivity(),"fetching conversion chart details");
            apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
            final JsonObject mainObj = new JsonObject();


            JsonObject payload = new JsonObject();
            payload.addProperty("productType", "JLG");
            payload.addProperty("userId", "S0334");

            payload.addProperty("type", AppConstants.CONVERSION_CHART);
            payload.addProperty("frequency", frequency);
          //  payload.addProperty("from", from_date);
              payload.addProperty("from","01-01-2022");
           // payload.addProperty("to", todate);
             payload.addProperty("to","17-01-2022");
            mainObj.add("Payload", payload);

            if (ConnectivityReciever.isConnected(getActivity())) {
                String frequency_text =  setFrequency(frequency);

                tv_frequency.setText(frequency_text);


                Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
                call1.enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.body() != null) {

                            if (response.isSuccessful() && response.code() == 200) {
                                progressDialog.progressDialog.dismiss();

                                JsonObject gson = new JsonParser().parse(response.body().toString()).getAsJsonObject();

                                try {
                                    JSONObject jo2 = new JSONObject(gson.toString());
                                    JSONObject object = (JSONObject) jo2.get("Data");
                                    //   verification_title.setText(object1.get("title").toString());

                                    String disburseAmount = object.get("disburseAmount").toString();
                                    m_dcount.setText(ConnectivityReciever.format(Double.valueOf(disburseAmount)));
                                    conversion_percentage.setText(object.get("conversionGrowthPercentage").toString());
                                    lead_count.setText(object.get("leadCount").toString());
                                    disburse_count.setText(object.get("disburseCount").toString());
                                    tv_target.setText(object.get("target").toString());
                                    prediction.setText(object.get("prediction").toString());
                                    String conversionVsPercentage = object.get("conversionVsPercentage").toString();

                                    if (!object.get("conversionRatio").toString().equals("null")) {
                                        lineChartView.setVisibility(View.VISIBLE);

                                        JSONArray array = (JSONArray) object.get("conversionRatio");
                                        //  tv_customer_count.setText(object.get("customerCount").toString());

                                        // JSONObject object1 = array.getJSONObject(0);


                                        yAxisValues.clear();
                                        yAxisValues1.clear();
                                        axisValues.clear();
                                        for (int i = 0; i < array.length(); i++) {
                                            //frequency
                                            axisValues.add(i, new AxisValue(i).setLabel(array.getJSONObject(i).get("frequency").toString()+" "));
                                           // axisValues.add(array.getJSONObject(i).get("frequency").toString());

                                            yAxisValues.add(new PointValue(i, Integer.parseInt(array.getJSONObject(i).get("leadCount").toString()))); //lead count
                                            // yAxisValues.add(array.getJSONObject(i).get("leadCount").toString());
                                            yAxisValues1.add(new PointValue(i, Integer.parseInt(array.getJSONObject(i).get("disburseCount").toString()))); //disburse count


                                        }


                                        setLineGraph();
                                    }else{
                                        lineChartView.setVisibility(View.GONE);
                                        ConnectivityReciever.errorDialogue(getActivity(),"No chart details found");

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }else if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED){

                                progressDialog.progressDialog.dismiss();
                                ConnectivityReciever.errorDialogue(getActivity(),"Unauthorized");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        progressDialog.progressDialog.dismiss();
                        ConnectivityReciever.errorDialogue(getActivity(),t.getMessage());

                    }


                });
            }else{
                progressDialog.progressDialog.dismiss();
                ConnectivityReciever.errorDialogue(getActivity(),"Internet is not available");

            }
            }catch(Exception e){
                e.printStackTrace();
            }




    }

    private String setFrequency(String frequency) {

        switch (frequency){
            case "TM":
                return "This Month";
            case "LM":
                return "Last Month";
            case "TQ":
                return "This Quarter";
            case "LQ":
                return "Laste Quarter";
            case "CD":
                return "Custom Range";

        }
        return  "This Month";
    }

    private void setLineGraph() {
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#ff4558"));
//        line.setFilled(true);
        line.setCubic(true);
        line.setHasPoints(true);
//        line.setShape(ValueShape.SQUARE);

        Line line1 = new Line(yAxisValues1).setColor(Color.parseColor("#49493f"));
        line1.setFilled(true);
        line1.setCubic(true);
        line1.setHasPoints(true);
        List lines = new ArrayList();
        lines.add(line);
        lines.add(line1);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(12);
        axis.setTextColor(Color.parseColor("#BEBEBE"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
//        yAxis.setName("Sales in millions");
        yAxis.setTextColor(Color.parseColor("#BEBEBE"));
        yAxis.setTextSize(12);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 60;
        viewport.right = axis.getMaxLabelChars();
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);

        collectionLineChartView.setLineChartData(data);
        Viewport viewport1 = new Viewport(collectionLineChartView.getMaximumViewport());
        viewport1.top = 60;
        collectionLineChartView.setMaximumViewport(viewport);
        collectionLineChartView.setCurrentViewport(viewport);
    }


}
