package superunitconverter.nexmii.com.superunitconverter.views.fragments;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.models.history.ConversionAdapter;
import superunitconverter.nexmii.com.superunitconverter.controllers.HistoryController;

/**
 * History Fragment. View class.
 * Should handle only view logic. Displaying results in the views
 */
public class HistoryFragment extends Fragment {

    //Properties
    private RecyclerView mRvHistoryContent;
    private ConversionAdapter conversionAdapter;
    private HistoryController historyController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        fieldsInit(view);
        initConversionsList();
        retrieveHistory();
        swipeLeftDeleteConversion();

        return view;
    }

    private void fieldsInit(View v){
        mRvHistoryContent = v.findViewById(R.id.rvHistoryContent);
    }

    private void initConversionsList(){
        mRvHistoryContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvHistoryContent.setHasFixedSize(true);
        conversionAdapter = new ConversionAdapter();
        mRvHistoryContent.setAdapter(conversionAdapter);
    }

    //Call HistoryController methods
    private void retrieveHistory(){
        if (getActivity() != null) {
            historyController = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                    .getInstance(getActivity().getApplication())).get(HistoryController.class);
            historyController.getAllConversions().observe(getActivity(), conversionsList
                    -> conversionAdapter.submitList(conversionsList));
        }
    }

    /**
     * A method that calls for action to delete a single conversion
     */
    private void swipeLeftDeleteConversion(){
        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0 , ItemTouchHelper.LEFT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                historyController.delete(conversionAdapter.getConversionAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mRvHistoryContent);
    }

    /**
     * When onResume() is called here, RecyclerView's native method will auto scroll up to the
     * latest entry
     */
    @Override
    public void onResume() {
        super.onResume();
        mRvHistoryContent.smoothScrollToPosition(0);
    }
}