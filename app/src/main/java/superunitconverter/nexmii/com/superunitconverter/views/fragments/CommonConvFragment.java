package superunitconverter.nexmii.com.superunitconverter.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.controllers.CommonConvController;
import superunitconverter.nexmii.com.superunitconverter.models.commonconv.CommonConvAdapter;

/**
 * Common Conversion Fragment. View class.
 * Should handle only view logic. Displaying results in the views
 */
public class CommonConvFragment extends Fragment {

    //properties:
    private RecyclerView rvCCContent;
    private CommonConvAdapter commonConvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common_conv, container, false);

        initViews(view);
        initCommonConvsList();
        retrieveCommonConvs();

        return view;

    }

    /**
     * Method for views initialization
     */
    private void initViews(View v){
        rvCCContent = v.findViewById(R.id.rv_cc_content);
    }

    /**
     * Method for the recycler view and recycler view adapter initialization
     */
    private void initCommonConvsList(){
        rvCCContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCCContent.setHasFixedSize(true);
        commonConvAdapter = new CommonConvAdapter();
        rvCCContent.setAdapter(commonConvAdapter);
    }

    /**
     * Method for populating the common conversions
     */
    private void retrieveCommonConvs() {
        if (getActivity() != null){
            CommonConvController commonConvController = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                    .getInstance(getActivity().getApplication())).get(CommonConvController.class);
            commonConvController.getAllCommonConversions().observe(getActivity(), commonConvList
                    -> commonConvAdapter.submitList(commonConvList));
        }
    }
}