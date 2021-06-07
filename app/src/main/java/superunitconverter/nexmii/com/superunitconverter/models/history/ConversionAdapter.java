package superunitconverter.nexmii.com.superunitconverter.models.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.Conversion;
import superunitconverter.nexmii.com.superunitconverter.util.ResultColor;

/**
 * Conversion adapter
 */
public class ConversionAdapter extends ListAdapter<Conversion, ConversionAdapter.ConversionHolder> {

    public ConversionAdapter() {
        super(DIFF_CALLBACK);
    }

    /**
     * Field used to compare one list against another one for the purpose of refreshing only the updated item
     * in the new list, which can then be used with an animation to delete an item and have a nice swiping effect.
     */
    private static final DiffUtil.ItemCallback<Conversion> DIFF_CALLBACK = new DiffUtil.ItemCallback<Conversion>() {
        @Override
        public boolean areItemsTheSame(@NonNull Conversion oldItem, @NonNull Conversion newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Conversion oldItem, @NonNull Conversion newItem) {
            return oldItem.getCategory().equals(newItem.getCategory()) &&
                    oldItem.getOriginalAmount().equals(newItem.getOriginalAmount()) &&
                    oldItem.getOriginalUnit().equals(newItem.getOriginalUnit()) &&
                    oldItem.getConvertedAmount().equals(newItem.getConvertedAmount()) &&
                    oldItem.getTargetUnit().equals(newItem.getTargetUnit());
        }
    };

    /**
     * Adapter's onCreateViewHolder. Its job is to fire up the XML file which holds the views
     */
    @NonNull
    @Override
    public ConversionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_history_item, parent, false);

        return new ConversionHolder(itemView);
    }

    /**
     * Adapter's onBindViewHolder. Its job is to connect the XML views from the XML file
     * (connected in onCreateViewHolder) to the inner class variables of the same type
     * (the ViewHolder class).
     */
    @Override
    public void onBindViewHolder(@NonNull ConversionHolder holder, int position) {
        Conversion currentConversion = getItem(position);
        holder.itemConvertFromUnit.setText(currentConversion.getOriginalUnit());
        holder.itemConvertFromNumber.setText(currentConversion.getOriginalAmount());
        holder.itemConvertToUnit.setText(currentConversion.getTargetUnit());
        holder.itemConvertToNumber.setText(currentConversion.getConvertedAmount());
        setBackground(holder.itemConvertToData.getContext(), currentConversion.getCategory(), holder.itemConvertToData);
        placeDateIfCurrency(holder.itemAsOf.getContext(), currentConversion.getCategory(), holder, currentConversion);
    }

    /**
     * Method for deducing the background depending on the category.
     * Uses the ResultColor singleton class for that purpose
     */
    private void setBackground(Context context, String category, LinearLayout convertToData){
        ResultColor.getInstance().convertToBgColor(context, category, convertToData);
    }

    /**
     * Method for deducing whether or not to add the date to the Currency item in the Recycler view
     */
    private void placeDateIfCurrency(Context context, String category, ConversionHolder holder, Conversion conversion){
        if (category.equals(context.getResources().getString(R.string.category_currency))){
            String date = context.getResources().getString(R.string.as_of) + " " + conversion.getDate();
            holder.itemAsOf.setVisibility(View.VISIBLE);
            holder.itemAsOf.setText(date);
        }else{
            holder.itemAsOf.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Method for getting the conversions
     */
    public Conversion getConversionAt(int position){
        return getItem(position);
    }

    /**
     * Inner class which extends the RecyclerView.ViewHolder class.
     * This is used to hold the view variables here instead of doing it in the
     * adapter itself. This allows for better loading and smoother scrolling.
     */
    public static class ConversionHolder extends RecyclerView.ViewHolder{

        private final TextView itemConvertFromUnit;
        private final TextView itemConvertFromNumber;
        private final TextView itemConvertToUnit;
        private final TextView itemConvertToNumber;
        private final TextView itemAsOf;
        private final LinearLayout itemConvertToData;

        public ConversionHolder(View itemView){
            super(itemView);
            itemConvertFromUnit = itemView.findViewById(R.id.item_convert_from_unit);
            itemConvertFromNumber = itemView.findViewById(R.id.item_convert_from_number);
            itemConvertToUnit = itemView.findViewById(R.id.item_convert_to_unit);
            itemConvertToNumber = itemView.findViewById(R.id.item_convert_to_number);
            itemAsOf = itemView.findViewById(R.id.item_as_of);
            itemConvertToData = itemView.findViewById(R.id.item_convert_to_data);
        }
    }
}
