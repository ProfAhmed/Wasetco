package com.example.ahmed.wasetco.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.models.filter_models.CityFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.FinishingTypeFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.GovernmentFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.PaymentTypeFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.PropertyInstallmentFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.PropertyNameFilterModel;
import com.example.ahmed.wasetco.data.models.filter_models.PurposeFilterModel;
import com.example.ahmed.wasetco.events.EventUrl;
import com.example.ahmed.wasetco.ui.activities.FilterActivity;
import com.example.ahmed.wasetco.viewmodels.FilterDataViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.etNameFlat)
    AutoCompleteTextView etKeyword;
    @BindView(R.id.etFromPrice)
    EditText etMinPrice;
    @BindView(R.id.etToPrice)
    EditText etMaxPrice;
    @BindView(R.id.spGovernment)
    Spinner spGovernment;
    @BindView(R.id.spCity)
    Spinner spCity;
    @BindView(R.id.spRealStateType)
    Spinner spProperty_name_id;
    @BindView(R.id.spFinishingType)
    Spinner spFinishing;
    @BindView(R.id.spLivingType)
    Spinner spPurpose;
    @BindView(R.id.spPaymentType)
    Spinner spPaymentType;
    @BindView(R.id.spPremier)
    Spinner spProperty_installment_id;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.rlCity)
    RelativeLayout rlCity;
    @BindView(R.id.rlFinish)
    RelativeLayout rlFinish;
    @BindView(R.id.rlPayment)
    RelativeLayout rlPayment;
    @BindView(R.id.rlInstallment)
    RelativeLayout rlInstallment;

    //declare model;
    private GovernmentFilterModel governmentFilterModel;
    private CityFilterModel cityFilterModel;
    private PropertyNameFilterModel propertyNameFilterModel;
    private FinishingTypeFilterModel finishingTypeFilterModel;
    private PurposeFilterModel purposeFilterModel;
    private PaymentTypeFilterModel paymentTypeFilterModel;
    private PropertyInstallmentFilterModel propertyInstallmentFilterModel;

    // declare lists for spinners
    private ArrayList<GovernmentFilterModel> governmentsList;
    private ArrayList<CityFilterModel> citiesList;
    private ArrayList<PropertyNameFilterModel> propertyNameList;
    private ArrayList<FinishingTypeFilterModel> finishingTypeList;
    private ArrayList<PurposeFilterModel> purposeList;
    private ArrayList<PaymentTypeFilterModel> paymentTypeList;
    private ArrayList<PropertyInstallmentFilterModel> propertyInstallmentList;
    private String[] countries;

    // declare Adapters for Spinners
    private ArrayAdapter<GovernmentFilterModel> governmentsAdapter;
    private ArrayAdapter<CityFilterModel> citiesAdapter;
    private ArrayAdapter<PropertyNameFilterModel> propertyNamesAdapter;
    private ArrayAdapter<FinishingTypeFilterModel> finishingTypeAdapter;
    private ArrayAdapter<PurposeFilterModel> purposeAdapter;
    private ArrayAdapter<PaymentTypeFilterModel> paymentTypeAdapter;
    private ArrayAdapter<PropertyInstallmentFilterModel> premierAdapter;
    private ArrayAdapter<String> keywordAdapter;

    //prevent spinner from listener when first launcher before user touch it
    private boolean isSpinnerTouched = false;

    //declare spinner inputs
    String keyword = "";
    String min_price = "";
    String max_price = "";
    String government_id = "";
    String city_id = "";
    String property_name_id = "";
    String property_finish_id = "";
    String purpose = "";
    String type = "";
    String property_installment_id = "";
    String url;

    //ViewModels
    FilterDataViewModel filterViewModel;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, view);
        FilterActivity.toolbar.setTitle(R.string.filter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filterViewModel = ViewModelProviders.of(this).get(FilterDataViewModel.class);
        initLists();
        initAdapter();
        initListeners();
    }

    private void initListeners() {

        btnSearch.setOnClickListener(this::onClick);
        spGovernment.setOnItemSelectedListener(this);
        spCity.setOnItemSelectedListener(this);
        spProperty_name_id.setOnItemSelectedListener(this);
        spFinishing.setOnItemSelectedListener(this);
        spPurpose.setOnItemSelectedListener(this);
        spPaymentType.setOnItemSelectedListener(this);
        spProperty_installment_id.setOnItemSelectedListener(this);

        spGovernment.setOnTouchListener(this);
        spCity.setOnTouchListener(this);
        spProperty_name_id.setOnTouchListener(this);
        spFinishing.setOnTouchListener(this);
        spPurpose.setOnTouchListener(this);
        spPaymentType.setOnTouchListener(this);
        spProperty_installment_id.setOnTouchListener(this);
    }

    private void initLists() {
        governmentsList = new ArrayList<>();
        citiesList = new ArrayList<>();
        propertyNameList = new ArrayList<>();
        finishingTypeList = new ArrayList<>();
        purposeList = new ArrayList<>();
        paymentTypeList = new ArrayList<>();
        propertyInstallmentList = new ArrayList<>();
        // Get the string array
        countries = getResources().getStringArray(R.array.countries_array);
    }

    private void initAdapter() {
        String governmentDefault = getResources().getString(R.string.government);
        String cityDefault = getResources().getString(R.string.city);
        String propertyNameDefault = getResources().getString(R.string.realEstateType);
        String finishingTypDefault = getResources().getString(R.string.finishingType);
        String purposeDefault = getResources().getString(R.string.livingType);
        String paymentTypeDefault = getResources().getString(R.string.paymentType);
        String propertyInstallmentDefault = getResources().getString(R.string.premier);

        governmentFilterModel = new GovernmentFilterModel(governmentDefault, "");
        cityFilterModel = new CityFilterModel(cityDefault, "");
        propertyNameFilterModel = new PropertyNameFilterModel(propertyNameDefault, "");
        finishingTypeFilterModel = new FinishingTypeFilterModel(finishingTypDefault, "");
        purposeFilterModel = new PurposeFilterModel(purposeDefault, "");
        paymentTypeFilterModel = new PaymentTypeFilterModel(paymentTypeDefault, "");
        propertyInstallmentFilterModel = new PropertyInstallmentFilterModel(propertyInstallmentDefault, "");

        governmentsList.add(governmentFilterModel);
        citiesList.add(cityFilterModel);
        propertyNameList.add(propertyNameFilterModel);
        finishingTypeList.add(finishingTypeFilterModel);
        purposeList.add(purposeFilterModel);
        paymentTypeList.add(paymentTypeFilterModel);
        propertyInstallmentList.add(propertyInstallmentFilterModel);

        getGovernments();
        getPropertyNames();
        getFinishes();
        fillLists();

        // Creating keywordAdapter for spinner
        governmentsAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, governmentsList);
        citiesAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, citiesList);
        propertyNamesAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, propertyNameList);
        finishingTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, finishingTypeList);
        purposeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, purposeList);
        paymentTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, paymentTypeList);
        premierAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, propertyInstallmentList);
        keywordAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, countries);

        // Drop down layout style - list view with radio button
        governmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        finishingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        premierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data keywordAdapter to spinner
        spGovernment.setAdapter(governmentsAdapter);
        spCity.setAdapter(citiesAdapter);
        spProperty_name_id.setAdapter(propertyNamesAdapter);
        spFinishing.setAdapter(finishingTypeAdapter);
        spPurpose.setAdapter(purposeAdapter);
        spPaymentType.setAdapter(paymentTypeAdapter);
        spProperty_installment_id.setAdapter(premierAdapter);
        // Create the keywordAdapter and set it to the AutoCompleteTextView
        etKeyword.setAdapter(keywordAdapter);
    }

    private void fillLists() {
        PurposeFilterModel purposeFilterModel1 = new PurposeFilterModel(getString(R.string.igar), "1");
        PurposeFilterModel purposeFilterModel2 = new PurposeFilterModel(getString(R.string.tamlik), "2");
        PurposeFilterModel purposeFilterModel3 = new PurposeFilterModel(getString(R.string.mosharkah), "3");
        purposeList.add(purposeFilterModel1);
        purposeList.add(purposeFilterModel2);
        purposeList.add(purposeFilterModel3);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (isSpinnerTouched) {

            if (parent.getItemAtPosition(position) instanceof GovernmentFilterModel) {
                rlCity.setVisibility(View.VISIBLE);
                spCity.setSelection(0);
                GovernmentFilterModel model = (GovernmentFilterModel) parent.getItemAtPosition(position);
                government_id = model.getId();
                getCities(government_id);
                //todo send city request
                Toast.makeText(getActivity(), government_id, Toast.LENGTH_SHORT).show();
            }
            if (parent.getItemAtPosition(position) instanceof CityFilterModel) {
                CityFilterModel model = (CityFilterModel) parent.getItemAtPosition(position);
                city_id = model.getId();
                Toast.makeText(getActivity(), city_id, Toast.LENGTH_SHORT).show();
            }
            if (parent.getItemAtPosition(position) instanceof PropertyNameFilterModel) {
                PropertyNameFilterModel model = (PropertyNameFilterModel) parent.getItemAtPosition(position);
                property_name_id = model.getId();
                rlFinish.setVisibility(View.VISIBLE);
                spFinishing.setSelection(0);
                Toast.makeText(getActivity(), property_name_id, Toast.LENGTH_SHORT).show();
            }
            if (parent.getItemAtPosition(position) instanceof FinishingTypeFilterModel) {
                FinishingTypeFilterModel model = (FinishingTypeFilterModel) parent.getItemAtPosition(position);
                property_finish_id = model.getId();
                Toast.makeText(getActivity(), property_finish_id, Toast.LENGTH_SHORT).show();
            }
            if (parent.getItemAtPosition(position) instanceof PurposeFilterModel) {
                PurposeFilterModel model = (PurposeFilterModel) parent.getItemAtPosition(position);
                String paymentTypeDefault = getResources().getString(R.string.paymentType);
                purpose = model.getName();
                if (purpose.equals(getString(R.string.igar))) {
                    rlPayment.setVisibility(View.VISIBLE);
                    spPaymentType.setSelection(0);
                    rlInstallment.setVisibility(View.GONE);
                    paymentTypeList.clear();
                    paymentTypeFilterModel = new PaymentTypeFilterModel(paymentTypeDefault, "");
                    PaymentTypeFilterModel model1 = new PaymentTypeFilterModel(getString(R.string.rent_old), "1");
                    PaymentTypeFilterModel model2 = new PaymentTypeFilterModel(getString(R.string.rent_new), "2");
                    PaymentTypeFilterModel model3 = new PaymentTypeFilterModel(getString(R.string.rent_day), "3");
                    paymentTypeList.add(paymentTypeFilterModel);
                    paymentTypeList.add(model1);
                    paymentTypeList.add(model2);
                    paymentTypeList.add(model3);
                } else if (purpose.equals(getString(R.string.tamlik))) {
                    rlPayment.setVisibility(View.VISIBLE);
                    spPaymentType.setSelection(0);
                    rlInstallment.setVisibility(View.GONE);
                    paymentTypeList.clear();
                    paymentTypeList.add(paymentTypeFilterModel);
                    paymentTypeFilterModel = new PaymentTypeFilterModel(paymentTypeDefault, "");
                    PaymentTypeFilterModel model1 = new PaymentTypeFilterModel(getString(R.string.taqset), "1");
                    PaymentTypeFilterModel model2 = new PaymentTypeFilterModel(getString(R.string.cash), "2");
                    paymentTypeList.add(model1);
                    paymentTypeList.add(model2);
                } else {
                    rlPayment.setVisibility(View.GONE);
                    rlInstallment.setVisibility(View.GONE);
                }
                Toast.makeText(getActivity(), purpose, Toast.LENGTH_SHORT).show();
            }
            if (parent.getItemAtPosition(position) instanceof PaymentTypeFilterModel) {
                PaymentTypeFilterModel model = (PaymentTypeFilterModel) parent.getItemAtPosition(position);
                type = model.getName();
                if (type.equals(getString(R.string.taqset))) {
                    rlInstallment.setVisibility(View.VISIBLE);
                    spProperty_installment_id.setSelection(0);
                    String propertyInstallmentDefault = getResources().getString(R.string.premier);
                    propertyInstallmentFilterModel = new PropertyInstallmentFilterModel(propertyInstallmentDefault, "");
                    PropertyInstallmentFilterModel model1 = new PropertyInstallmentFilterModel(getString(R.string.one_year), "1");
                    PropertyInstallmentFilterModel model2 = new PropertyInstallmentFilterModel(getString(R.string.tow_year), "2");
                    PropertyInstallmentFilterModel model3 = new PropertyInstallmentFilterModel(getString(R.string.three_year), "3");
                    PropertyInstallmentFilterModel model4 = new PropertyInstallmentFilterModel(getString(R.string.four_year), "4");
                    propertyInstallmentList.add(model1);
                    propertyInstallmentList.add(model2);
                    propertyInstallmentList.add(model3);
                    propertyInstallmentList.add(model4);
                } else {
                    rlInstallment.setVisibility(View.GONE);
                }
                Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
            }
            if (parent.getItemAtPosition(position) instanceof PropertyInstallmentFilterModel) {
                PropertyInstallmentFilterModel model = (PropertyInstallmentFilterModel) parent.getItemAtPosition(position);
                property_installment_id = model.getName();
                Toast.makeText(getActivity(), property_installment_id, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSearch) {
            keyword = etKeyword.getText().toString().trim();
            min_price = etMinPrice.getText().toString().trim();
            max_price = etMinPrice.getText().toString().trim();
            url = "http://demo.wasetco.com/api/searchproperties?"
                    + "max_price=" + max_price
                    + "&min_price=" + min_price
                    + "&property_name_id=" + property_name_id
                    + "&government_id=" + government_id
                    + "&city_id=" + city_id
                    + "&property_installment_id=" + property_installment_id
                    + "&property_finish_id=" + property_finish_id
                    + "&purpose=" + purpose
                    + "&type=" + type
                    + "&keyword=" + keyword;
            Log.v("URLR", url);
            EventBus.getDefault().postSticky(new EventUrl(url));
            Fragment fragment = new FilterResultFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    void getGovernments() {
        filterViewModel.getGovernmentsLiveData().observe(this, new Observer<ArrayList<GovernmentFilterModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<GovernmentFilterModel> governmentFilterModels) {
                governmentsList.addAll(governmentFilterModels);
                governmentsAdapter.notifyDataSetChanged();
            }
        });
    }

    void getCities(String id) {
        filterViewModel.getCitiesLiveData(id).observe(this, cityFilterModels -> {
            citiesList.clear();
            citiesList.add(cityFilterModel);
            citiesList.addAll(cityFilterModels);
            citiesAdapter.notifyDataSetChanged();
        });
    }

    void getPropertyNames() {
        filterViewModel.getPropertyNamesLiveData().observe(this, propertyNameFilterModels -> {
            propertyNameList.addAll(propertyNameFilterModels);
            propertyNamesAdapter.notifyDataSetChanged();
        });
    }

    void getFinishes() {
        filterViewModel.getFinishesLiveData().observe(this, finishingTypeFilterModels -> {
            finishingTypeList.addAll(finishingTypeFilterModels);
            finishingTypeAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        isSpinnerTouched = true;
        return false;
    }

}
