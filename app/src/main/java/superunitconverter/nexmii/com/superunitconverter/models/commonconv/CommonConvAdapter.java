package superunitconverter.nexmii.com.superunitconverter.models.commonconv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.util.ResultColor;

/**
 * Common conversion adapter
 */
public class CommonConvAdapter extends ListAdapter<CommonConv, CommonConvAdapter.CommonConvHolder> {

    public CommonConvAdapter() {
        super(CC_DIFF_CALLBACK);
    }

    /**
     * Field used to compare one list against another one for the purpose of refreshing only the updated item in
     * the new list, which can then be used with an animation to delete an item and have a nice swiping effect.
     */
    private static final DiffUtil.ItemCallback<CommonConv> CC_DIFF_CALLBACK = new DiffUtil.ItemCallback<CommonConv>() {
        @Override
        public boolean areItemsTheSame(@NonNull CommonConv oldItem, @NonNull CommonConv newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CommonConv oldItem, @NonNull CommonConv newItem) {
            return false;
        }
    };

    /**
     * Adapter's onCreateViewHolder. Its job is to fire up the XML file which holds the views
     */
    @NonNull
    @Override
    public CommonConvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cc_item_template, parent, false);
        return new CommonConvHolder(view);
    }

    /**
     * Adapter's onBindViewHolder. Its job is to connect the XML views from the XML file
     * (connected in onCreateViewHolder) to the inner class variables of the same type
     * (the ViewHolder class).
     */
    @Override
    public void onBindViewHolder(@NonNull CommonConvHolder holder, int position) {
        CommonConv currentCommonConv = getItem(position);
        holder.mCCItemCategory.setText(currentCommonConv.getCategory());
        holder.mCCItemOriginalAmount.setText(currentCommonConv.getOriginalNumber());
        holder.mCCItemOriginalUnit.setText(currentCommonConv.getOriginalUnit());
        holder.mCCItemTargetAmount.setText(currentCommonConv.getTargetNumber());
        holder.mCCItemTargetUnit.setText(transformedNewUnit(currentCommonConv.getCategory()
                , currentCommonConv.getTargetNumber(), currentCommonConv.getTargetUnit()));
        setElementsColor(currentCommonConv.getCategory(), holder.mCCItemCategory, holder.mCCItemConvertArrow);
    }

    /**
     * Method for setting the color for both category title and crossed arrows ImageView
     */
    private void setElementsColor(String category, TextView categoryWidget, ImageView arrowsWidget){
        categoryWidget.setTextColor(ResultColor.getInstance().generalColorSelector(categoryWidget.getContext(),category));
        arrowsWidget.setImageResource(ResultColor.getInstance().convertImageViewFromColor(category));
    }

    /**
     * Method which collects the target unit, and makes it plural (by adding an "s") to it if
     * certain conditions are true
     */
    private String transformedNewUnit(String category, String newNumber, String newUnit){//Error
        if (newNumber.contains(","))
            newNumber = newNumber.replace(",",".");
        if ((Double.parseDouble(newNumber) > 1 || Double.parseDouble(newNumber) < 1) && (!category.equals(UnitConstants.TEMPERATURE))
            && (!newUnit.equals(UnitConstants.FEET)))
            newUnit = newUnit + "s";
        return newUnit;
    }

    /**
     * Inner class which extends the RecyclerView.ViewHolder class.
     * This is used to hold the view variables here instead of doing it in the adapter itself
     * which allows for better loading and smoother scrolling.
     */
    public static class CommonConvHolder extends RecyclerView.ViewHolder{

        private final TextView mCCItemCategory;
        private final TextView mCCItemOriginalAmount;
        private final TextView mCCItemOriginalUnit;
        private final TextView mCCItemTargetAmount;
        private final TextView mCCItemTargetUnit;
        private final ImageView mCCItemConvertArrow;

        public CommonConvHolder(View itemView){
            super(itemView);
            mCCItemCategory = itemView.findViewById(R.id.cc_item_category);
            mCCItemOriginalAmount = itemView.findViewById(R.id.cc_item_original_amount);
            mCCItemOriginalUnit = itemView.findViewById(R.id.cc_item_original_unit);
            mCCItemTargetAmount = itemView.findViewById(R.id.cc_item_target_amount);
            mCCItemTargetUnit = itemView.findViewById(R.id.cc_item_target_unit);
            mCCItemConvertArrow = itemView.findViewById(R.id.cc_item_convert_arrow);
        }

    }

}
